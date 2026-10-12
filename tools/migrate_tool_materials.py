"""Apply Minecraft 26.x tool-property registrations after generating the legacy catalog."""
from pathlib import Path
import re,json
root=Path(__file__).resolve().parents[1]
p=root/'src/main/java/com/foundations/calculator/content/Content.java'
s=p.read_text()
pattern=r'\.attributes\((PickaxeItem|SwordItem|AxeItem|ShovelItem|HoeItem)\.createAttributes\(CalculatorTier\.of\("([^\"]+)"\),([^,]+),([^)]*)\)\)'
lines=[]
for line in s.splitlines():
    m=re.search(pattern,line)
    if m:
        cls,mat,damage,speed=m.groups()
        constructor=re.search(r'new (PickaxeItem|SwordItem|FireSwordItem|AxeItem|ShovelItem|HoeItem)\(CalculatorTier\.of\("([^\"]+)"\),',line)
        if not constructor:raise ValueError('Unrecognized tool registration: '+line)
        actual=constructor.group(1)
        if cls in ['PickaxeItem','SwordItem']:
            method='pickaxe' if cls=='PickaxeItem' else 'sword'
            line=line[:m.start()]+f'.{method}(CalculatorTier.of("{mat}"),{damage},{speed})'+line[m.end():]
            line=re.sub(r'new '+actual+r'\(CalculatorTier\.of\("[^\"]+"\),','new '+('FireSwordItem' if actual=='FireSwordItem' else 'Item')+'(',line)
        else:
            line=line[:m.start()]+line[m.end():]
            line=line.replace(constructor.group(0),f'new {actual}(CalculatorTier.of("{mat}"),{damage},{speed},')
    lines.append(line)
p.write_text('\n'.join(lines)+'\n')
repairs={'reinforced':'reinforced_stone_block','redstone':'redstone_ingot','enriched_gold':'enriched_gold_ingot','reinforced_iron':'reinforced_iron_ingot','weakened_diamond':'weakened_diamond','flawless_diamond':'flawless_diamond','fire_diamond':'fire_diamond','electric':'electric_diamond','end_forged':'end_diamond'}
for mat,item in repairs.items():
    p=root/f'src/main/resources/data/foundations_calculator/tags/item/repair/{mat}.json'
    p.parent.mkdir(parents=True,exist_ok=True)
    p.write_text(json.dumps({'replace':False,'values':['foundations_calculator:'+item]},indent=2)+'\n')
