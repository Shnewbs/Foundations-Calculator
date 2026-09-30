"""Extract upstream registrations and recipe declarations; never validates game code with stubs.
Run from project root with sibling upstream-calculator and upstream-core checkouts.
The small Java capture adapter evaluates the ORIGINAL recipe declarations (including loops).
"""
from pathlib import Path
import re,json,shutil,subprocess
ROOT=Path(__file__).resolve().parents[1]
CALC=ROOT.parent/'upstream-calculator'; CORE=ROOT.parent/'upstream-core'
NS='foundations_calculator'
def clean(s):
 s=re.sub(r'/\*.*?\*/','',s,flags=re.S);return re.sub(r'//[^\n]*','',s)
def snake(s): return re.sub(r'([a-z0-9])([A-Z])',r'\1_\2',re.sub(r'([A-Z]+)([A-Z][a-z])',r'\1_\2',s)).lower()
def body(s,method='addRecipes'):
 s=clean(s); start=re.search(r'void '+method+r'\(\)\s*\{',s).end();depth=1;i=start
 while depth:
  depth+=(s[i]=='{')-(s[i]=='}');i+=1
 return s[start:i-1]
entries=[];fields={}
for repo,cls,prefix in [(CALC,'CalculatorItems','Calculator'),(CALC,'CalculatorBlocks','Calculator'),(CORE,'SonarBlocks','SonarCore')]:
 p=next(repo.rglob(cls+'.java'));s=clean(p.read_text())
 for m in re.finditer(r'(\w+)\s*=\s*SonarRegister\.add(Item|Block)\([^;]*?"(\w+)"\s*,\s*(.+?);',s):
  field,kind,old,expr=m.groups();id=snake(old)
  if 'InvisibleBlock' in expr or field in ['scarecrowBlock']:continue
  entry={'field':prefix+'.'+field,'id':id,'legacy':old.lower(),'kind':kind.lower(),'constructor':expr}
  entries.append(entry);fields[entry['field']]=id
# Explicit metatype / special registrations.
fields['Calculator.calculatorScreen']='calculator_screen_block'
entries.append(dict(field='Calculator.calculatorScreen',id='calculator_screen_block',legacy='calculatorscreenblock',kind='block',constructor='screen'))
materials='amethyst tanzanite enriched_gold reinforced_iron weakened_diamond flawless_diamond fire_diamond electric_diamond end_diamond redstone_ingot'.split()
for i,m in enumerate(materials):
 id=m+'_block';fields['Calculator.material_block:'+str(i)]=id;entries.append(dict(field='Calculator.material_block:'+str(i),id=id,legacy=m,kind='block',constructor='material'))
colors='normal black blue brown cyan green light_blue light_grey lime magenta orange pink plain purple red yellow'.split()
for base,old in [('stable_stone','stablestone'),('stable_stone_rimmed','stablestonerimmed'),('stable_stone_black_rimmed','stablestoneblackrimmed')]:
 for i,c in enumerate(colors):
  id=base+'_'+c;entries.append(dict(field=f'SonarCore.{base}[{i}]',id=id,legacy=old+'_'+c.replace('_',''),kind='block',constructor='stable'))
fields['SonarCore.stableStone[0]']='stable_stone_normal'
# Flatten 1.12 metadata into explicit registry entries.
for base in ['circuit_board','circuit_dirty','circuit_damaged']:
 original=next(e for e in entries if e['id']==base);entries.remove(original)
 for i in range(14):entries.append({**original,'id':base+'_'+str(i),'meta':i,'legacy':original['legacy']+'_'+str(i)})
 for f,id in list(fields.items()):
  if id==base:
   for i in range(14):fields[f+':'+str(i)]=base+'_'+str(i)
   fields[f]=base+'_0'
(ROOT/'tools/content_catalog.json').write_text(json.dumps(entries,indent=2))
(ROOT/'tools/legacy_fields.json').write_text(json.dumps(fields,indent=2))
# Capture all original recipe declarations without requiring obsolete Forge.
files=sorted((CALC/'src/main/java/sonar/calculator/mod/common/recipes').glob('*Recipes.java'))
bodies={p.stem:body(p.read_text()) for p in files}
refs='\n'.join(bodies.values())
classes=[]
for cls in ['Items','Blocks','Calculator','SonarCore','ResearchRecipeType']:
 names=sorted(set(re.findall(r'\b'+cls+r'\.(\w+)',refs)))
 decl=[]
 for n in names:
  if n=='stableStone':decl.append('static Item[] stableStone={new Item("SonarCore.stableStone[0]")};')
  elif cls=='ResearchRecipeType':decl.append(f'static ResearchRecipeType {n}=new ResearchRecipeType("{n}");')
  else:decl.append(f'static Item {n}=new Item("{cls}.{n}");')
 classes.append(f'static class {cls} '+'{'+('final String name; ResearchRecipeType(String name){this.name=name;}' if cls=='ResearchRecipeType' else '')+''.join(decl)+'}')
java='''import java.util.*;
public class RecipeCapture {
 static String current;
 record Item(String id) {}
 static class ItemStack { Item item;int count,meta;boolean stable,analysed;
 ItemStack(Item item){this(item,1,0);} ItemStack(Item item,int count){this(item,count,0);}
 ItemStack(Item item,int count,int meta){this.item=item;this.count=count;this.meta=meta;}
 }
 record ExtractionChamberOutput(ItemStack stack){} record PrecisionChamberOutput(ItemStack stack){}
 static class OreDictionary {static final int WILDCARD_VALUE=32767;}
 static String q(String s){return "\\\""+s+"\\\"";}
 static String json(Object o){
 if(o instanceof Item x)return "{\\\"item\\\":"+q(x.id())+",\\\"count\\\":1,\\\"meta\\\":0}";
 if(o instanceof ItemStack x)return "{\\\"item\\\":"+q(x.item.id())+",\\\"count\\\":"+x.count+",\\\"meta\\\":"+x.meta+",\\\"stable\\\":"+x.stable+",\\\"analysed\\\":"+x.analysed+"}";
 if(o instanceof String s)return "{\\\"ore\\\":"+q(s)+"}";
 if(o instanceof ExtractionChamberOutput x)return "{\\\"random_circuit\\\":"+json(x.stack())+",\\\"chance\\\":0.1111111111111111}";
 if(o instanceof PrecisionChamberOutput x)return "{\\\"random_circuit\\\":"+json(x.stack())+",\\\"chance\\\":1.0}";
 if(o instanceof ResearchRecipeType x)return "{"+q("research")+":"+q(x.name)+"}";
 if(o instanceof Number)return o.toString();return "null";
 }
 static void addRecipe(Object... objs){System.out.println("{\\\"type\\\":"+q(current)+",\\\"args\\\":["+String.join(",",Arrays.stream(objs).map(RecipeCapture::json).toList())+"]}");}
 static void addCircuit(Item input,Item output,int meta){addRecipe(new ItemStack(input,1,meta),new ItemStack(output,1,meta));}
 static ItemStack c(int meta,long amount,boolean stable){ItemStack s=new ItemStack(Calculator.circuitBoard,(int)amount,meta);s.stable=stable;s.analysed=true;return s;}
'''
java+='\n'.join(classes)
java+='enum Variants {'+','.join(m.upper()+f'({i})' for i,m in enumerate(materials))+'; final int m; Variants(int m){this.m=m;}int getMeta(){return m;}}'
for name,b in bodies.items():java+='\nstatic void '+name+'(){current="'+name+'";'+b+'}\n'
java+='public static void main(String[] args){'+''.join(n+'();' for n in bodies)+'}\n}'
capture=ROOT/'build/recipe-capture';capture.mkdir(parents=True,exist_ok=True);(capture/'RecipeCapture.java').write_text(java)
subprocess.run(['javac',str(capture/'RecipeCapture.java')],check=True)
out=subprocess.check_output(['java','-cp',str(capture),'RecipeCapture'],text=True)
recipes=[json.loads(l) for l in out.splitlines()]
(ROOT/'tools/upstream_recipes.json').write_text(json.dumps(recipes,indent=2))
print('Catalog:',len(entries),'items/blocks; extracted',len(recipes),'original recipe declarations')
