from pathlib import Path
import json,re,subprocess
R=Path(__file__).resolve().parents[1];cal=R.parent/'upstream-calculator';core=R.parent/'upstream-core'
def body(s,name):
 s=re.sub(r'/\*.*?\*/','',s,flags=re.S);s=re.sub(r'//[^\n]*','',s);start=re.search(r'void '+name+r'\(\)\s*\{',s).end();d=1;i=start
 while d:d+=(s[i]=='{')-(s[i]=='}');i+=1
 return s[start:i-1]
a=body((cal/'src/main/java/sonar/calculator/mod/CalculatorCrafting.java').read_text(),'addRecipes').replace('MaterialBlock.Variants','MaterialVariants').replace('Variants','MaterialVariants').replace('MaterialMaterialVariants','MaterialVariants')
b=body((core/'src/main/java/sonar/core/SonarCrafting.java').read_text(),'registerCraftingRecipes').replace('Variants','StableVariants')
fields=json.loads((R/'tools/legacy_fields.json').read_text())
refs=a+b
java='''import java.util.*;
public class CraftingCapture {
 record Item(String id){}
 record ItemStack(Item item,int count,int meta){ItemStack(Item i){this(i,1,0);}ItemStack(Item i,int c){this(i,c,0);}}
 record ResourceLocation(String path){}
 static class CalculatorConstants {static String MODID="calculator",NAME="Calculator";}
 static class SonarConstants {static String MODID="sonarcore";}
 static class Loader {static boolean isModLoaded(String s){return true;}}
 static class Ingredient {static Object fromStacks(ItemStack s){return s;}}
 static String q(String s){return "\\\""+s+"\\\"";}
 static String json(Object o){if(o instanceof Item x)return "{\\\"item\\\":"+q(x.id())+",\\\"count\\\":1,\\\"meta\\\":0}";
 if(o instanceof ItemStack x)return "{\\\"item\\\":"+q(x.item().id())+",\\\"count\\\":"+x.count()+",\\\"meta\\\":"+x.meta()+"}";
 return q(o.toString());}
 static void emit(String type,ItemStack out,Object[] in){System.out.println("{\\\"type\\\":"+q(type)+",\\\"output\\\":"+json(out)+",\\\"args\\\":["+String.join(",",Arrays.stream(in).map(CraftingCapture::json).toList())+"]}");}
 static void addShapedOre(String mod,ItemStack out,Object...in){emit("shaped",out,in);}
 static void addShapelessOre(String mod,ItemStack out,Object...in){emit("shapeless",out,in);}
 static void addShaped(String mod,ResourceLocation group,ItemStack out,Object...in){emit("shaped",out,in);}
 static void addShapeless(String mod,ResourceLocation group,ItemStack out,Object...in){emit("shapeless",out,in);}
 static Object fromBlock(Item b){return b;}
 static class SonarCrafting extends CraftingCapture {}
'''
for cls in ['Blocks','Items','SonarCore']:
 names=sorted(set(re.findall(r'\b'+cls+r'\.(\w+)',refs))|{f.split('.')[1] for f in fields if f.startswith(cls+'.') and ':' not in f and '[' not in f})
 if cls=='SonarCore':names=list(set(names)|{'stableStone','stablestonerimmedBlock','stablestonerimmedblackBlock'})
 java+='static class '+cls+' {'
 for n in names:
  if n in ['stableStone','stablestonerimmedBlock','stablestonerimmedblackBlock']:java+='static Item[] '+n+'={'+','.join('new Item("SonarCore.'+n+'['+str(i)+']")' for i in range(16))+'};'
  else:java+=f'static Item {n}=new Item("{cls}.{n}");'
 java+='}\n'
for f in fields:
 if f.startswith('Calculator.') and ':' not in f:java+=f'static Item {f.split(".")[1]}=new Item("{f}");\n'
java+='static Item material_block=new Item("Calculator.material_block");\n'
for n in ['stableStone','stablestonerimmedBlock','stablestonerimmedblackBlock']:java+='static Item[] '+n+'=SonarCore.'+n+';'
materials='AMETHYST TANZANITE ENRICHED_GOLD REINFORCED_IRON WEAKENED_DIAMOND FLAWLESS_DIAMOND FIRE_DIAMOND ELECTRIC_DIAMOND END_DIAMOND REDSTONE_INGOT'.split();base=['large_amethyst','large_tanzanite','enrichedgold_ingot','reinforcediron_ingot','weakeneddiamond','flawlessdiamond','firediamond','electricDiamond','endDiamond','redstone_ingot']
java+='enum MaterialVariants {'+','.join(f'{m}({i},new Item("Calculator.{base[i]}"))' for i,m in enumerate(materials))+';int m;Item i;MaterialVariants(int m,Item i){this.m=m;this.i=i;}int getMeta(){return m;}Item getBaseItem(){return i;}}'
dyes=[7,0,4,3,6,2,12,8,10,13,14,9,15,5,1,11]
java+='enum StableVariants {'+','.join('C'+str(i)+'('+str(d)+')' for i,d in enumerate(dyes))+';int d;StableVariants(int d){this.d=d;}int getDyeMeta(){return d;}}'
java+='static void calculator(){'+a+'}static void core(){'+b+'}public static void main(String[] args){calculator();core();}}'
p=R/'build/recipe-capture';(p/'CraftingCapture.java').write_text(java)
subprocess.run(['javac',str(p/'CraftingCapture.java')],check=True)
out=subprocess.check_output(['java','-cp',str(p),'CraftingCapture'],text=True)
rs=[json.loads(l) for l in out.splitlines()];(R/'tools/upstream_crafting.json').write_text(json.dumps(rs,indent=2));print('Captured',len(rs),'crafting recipes')
