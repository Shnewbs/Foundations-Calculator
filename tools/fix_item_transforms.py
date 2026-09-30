"""Supply explicit item transforms for every block; legacy meshes lack block/block inheritance."""
from pathlib import Path
import json
p=Path(__file__).resolve().parents[1];assets=p/'src/main/resources/assets/foundations_calculator'
def transform(rotation,translation,scale):return dict(rotation=rotation,translation=translation,scale=[scale]*3)
display={
 'gui':transform([30,225,0],[0,0,0],.625),
 'ground':transform([0,0,0],[0,3,0],.25),
 'fixed':transform([0,0,0],[0,0,0],.5),
 'head':transform([0,0,0],[0,0,0],1),
 'thirdperson_righthand':transform([75,45,0],[0,2.5,0],.375),
 'thirdperson_lefthand':transform([75,45,0],[0,2.5,0],.375),
 'firstperson_righthand':transform([0,45,0],[0,0,0],.4),
 'firstperson_lefthand':transform([0,225,0],[0,0,0],.4),
}
for entry in json.loads((p/'tools/content_catalog.json').read_text()):
 if entry['kind']!='block':continue
 f=assets/'models/item'/(entry['id']+'.json')
 if not f.exists():continue
 data=json.loads(f.read_text());data['display']=display
 f.write_text(json.dumps(data,indent=2)+'\n')
print('Block item transforms updated')
