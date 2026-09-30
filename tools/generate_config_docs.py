"""Generate the full server setting reference from the runtime's exact catalog."""
from pathlib import Path
import json,re,collections
root=Path(__file__).resolve().parents[1];rows=json.loads((root/'tools/configuration_catalog.json').read_text());groups=collections.OrderedDict()
for row in rows:groups.setdefault(row['key'].split('.')[0],[]).append(row)
lines=['# Complete server configuration reference','',f'Generated from the same catalog loaded by the mod: **{len(rows):,} settings**. See [CONFIGURATION.md](../CONFIGURATION.md) for precedence, file locations, reload behavior and examples. Values shown here are shipped defaults; recipe-specific `-1` means inherit.','']
for group,values in groups.items():
 lines+=['## '+group,'','| Key | Default | Valid range | Meaning |','|---|---|---|---|']
 for r in values:
  default=json.dumps(r['default']);bounds=f"{r['min']} … {r['max']}" if 'min'in r else 'true / false';desc=r['description']
  if desc==r['key'].replace('.',' / '):
   field=r['key'].rsplit('.',1)[-1];desc={
    'enabled':'Enable this content/recipe.', 'capacity':'Maximum stored FE; existing excess is preserved.',
    'transferRate':'Maximum FE per tick per block connection; global positive transfer override takes precedence.',
    'chargeRate':'Maximum FE per tick used for the machine’s battery transfer.',
    'energyMultiplier':'Multiplier on applicable processing costs, after the global multiplier.',
    'timeMultiplier':'Multiplier on applicable processing duration, after the global multiplier.',
    'itemAutomation':'Allow item capabilities to insert/extract through configured faces.',
    'energyInput':'Allow external energy insertion through eligible faces.',
    'energyOutput':'Allow external energy extraction through eligible faces.',
    'retainProgressWithoutPower':'Keep partial processing progress during a power shortage.',
    'energyOverride':'-1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE.',
    'ticksOverride':'-1 or 0: recipe/default duration; positive: replacement ticks where processing is timed.',
    'chanceMultiplier':'Multiply each process result chance, clamped to 0–1.',
   }.get(field,' '.join(re.sub(r'([a-z0-9])([A-Z])',r'\1 \2',field).replace('_',' ').split()).capitalize()+'. See the category rules in CONFIGURATION.md.')
  lines.append(f"| `{r['key']}` | `{default}` | {bounds} | {desc} |")
 lines.append('')
(root/'docs/CONFIG_REFERENCE.md').write_text('\n'.join(lines)+'\n')
print('Documented',len(rows),'server keys')
