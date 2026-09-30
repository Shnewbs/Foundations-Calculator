from pathlib import Path
import json, shutil
P=Path(__file__).resolve().parents[1]
SRC=P/'src/main/resources/assets/foundations_calculator/foundations_guides/field_guide'
DATA=P/'src/main/resources/data/foundations_calculator/patchouli_books/field_guide'
ASSET=P/'src/main/resources/assets/foundations_calculator/patchouli_books/field_guide/en_us'
book=json.loads((SRC/'book.json').read_text())
if DATA.exists(): shutil.rmtree(DATA)
book_asset_root=ASSET.parent
if book_asset_root.exists(): shutil.rmtree(book_asset_root)
DATA.mkdir(parents=True); (ASSET/'categories').mkdir(parents=True); (ASSET/'entries').mkdir(parents=True)
patch_book={
 'name':'Calculator Field Guide',
 'landing_text':'Welcome to Foundations Calculator. Start with Getting Started: First 10 Minutes, then use the categories below as your machine and systems reference.',
 'version':'8.1',
 'subtitle':'Foundations Guide API + Patchouli mirror',
 'use_resource_pack':True,
 'dont_generate_book':True,
 'custom_book_item':'foundations_calculator:info_calculator',
 'show_toasts':False,
 'pause_game':False,
 'text_overflow_mode':'resize'
}
(DATA/'book.json').write_text(json.dumps(patch_book,indent=2)+'\n')
icons={'power':'foundations_calculator:power_cube','machines':'foundations_calculator:processing_chamber','research':'foundations_calculator:research_chamber','farming':'foundations_calculator:basic_greenhouse','circuits':'foundations_calculator:circuit_board_0','calculators':'foundations_calculator:calculator'}
for rel in book['chapters']:
 c=json.loads((SRC/rel).read_text()); cid=c['id'].split(':',1)[1]
 data={'name':c['title'],'description':f"{c['title']} from the Foundations Calculator Field Guide.",'icon':icons.get(cid,'foundations_calculator:info_calculator'),'sortnum':c.get('order',0)}
 (ASSET/'categories'/(cid+'.json')).write_text(json.dumps(data,indent=2)+'\n')

def esc(s): return str(s).replace('$','$$')
def lines(b):
 t=b.get('type'); text=b.get('text','')
 if t=='heading': return [f"$(l){esc(text)}$()"]
 if t=='paragraph': return [esc(text)]
 if t=='callout': return [f"$(o){esc(text)}$()"]
 if t in ('steps','list'): return [f"{i}. "+' | '.join(esc(x) for x in row) for i,row in enumerate(b.get('rows',[]),1)]
 if t=='table': return ([f"$(l){esc(text)}$()"] if text else [])+[' — '.join(esc(x) for x in row) for row in b.get('rows',[])]
 if t=='link': return [f"$(l:{b.get('target','')}){esc(text or b.get('target',''))}$()"]
 if t=='item': return [esc(text or 'Item')+': '+b.get('target','')]
 if t=='recipe': return [esc(text or 'Recipe')+': live recipe details are available in the native or master Foundations reader.']
 if t=='live': return [esc(text or 'Live values')+': live server values are available in the native or master Foundations reader.']
 if t in ('image','layers'): return [esc(text or 'Diagram')+': see the native or master Foundations reader for the interactive or layered view.']
 return [esc(text)] if text else []

for rel in book['entries']:
 e=json.loads((SRC/rel).read_text()); path=e['id'].split(':',1)[1]; cat=e['chapter'].split(':',1)[1]
 paragraphs=[]
 for b in e['blocks']: paragraphs.extend(lines(b))
 pages=[]; cur=''
 for para in paragraphs:
  add=para if not cur else '$p'+para
  if len(cur)+len(add)>1500 and cur:
   pages.append({'type':'patchouli:text','text':cur}); cur=para
  else: cur+=add
 if cur or not pages: pages.append({'type':'patchouli:text','text':cur or 'See the native Foundations reader for this entry.'})
 pe={'name':e['title'],'category':'foundations_calculator:'+cat,'icon':(e.get('items') or ['foundations_calculator:info_calculator'])[0],'sortnum':e.get('order',0),'pages':pages}
 if path in ('getting_started/welcome','getting_started/quickstart'): pe['priority']=True
 if e.get('items'): pe['extra_recipe_mappings']={item:0 for item in e['items'][:32]}
 out=ASSET/'entries'/(path+'.json'); out.parent.mkdir(parents=True,exist_ok=True); out.write_text(json.dumps(pe,indent=2)+'\n')
interop={
 'guide':'foundations_calculator:field_guide','api_version':1,'landing':'foundations_calculator:getting_started/welcome',
 'readers':{
  'standalone':{'item':'foundations_calculator:info_calculator'},
  'master':{'contract':'com.foundations:foundations-guide-api:1.0.0','entry_target_format':'guide#entry'},
  'patchouli':{'book':'foundations_calculator:field_guide','mode':'resource_mirror','generated_item':False,'custom_book_item':'foundations_calculator:info_calculator'}
 }
}
(SRC/'interop.json').write_text(json.dumps(interop,indent=2)+'\n')
print(json.dumps({'patchouli_book':'foundations_calculator:field_guide','entries':len(book['entries']),'duplicate_item':False},indent=2))
