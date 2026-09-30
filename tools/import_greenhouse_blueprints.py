"""Preserve the original greenhouse geometry; convert directions and block-state placement."""
from pathlib import Path
import sys
r=Path(sys.argv[1]);root=Path(__file__).resolve().parents[1]
def method(text,signature):
 start=text.index(signature);brace=text.index('{',start);depth=1;i=brace+1
 while depth:
  depth+=(text[i]=='{')-(text[i]=='}');i+=1
 return text[start:i]
parts=[]
for tier,name in [(1,'Basic'),(2,'Advanced')]:
 s=next(r.rglob('TileEntity'+name+'Greenhouse.java')).read_text();m=method(s,'public ArrayList<BlockPlace> getStructure()')
 m=m.replace('getStructure()',name.lower()+'()').replace('SonarHelper.getHorizontal(forward)','forward.getClockWise()').replace('getFrontOffsetX()','getStepX()').replace('getFrontOffsetZ()','getStepZ()')
 parts.append(m)
s=next(r.rglob('TileEntityGreenhouse.java')).read_text();orient=method(s,'public int type(String string)').replace('this.getBlockMetadata()','forward.getOpposite().get3DDataValue()')
text='''package com.foundations.calculator.content;
import java.util.*;
import net.minecraft.core.*;
/** Geometry translated from Calculator 1.12.2 (MIT, Ollie Lansdell). */
public final class GreenhouseBlueprint {
 public enum BlockType {LOG,STAIRS,GLASS,PLANKS}
 public record BlockPlace(BlockType type,int x,int y,int z,int meta){public BlockPos pos(){return new BlockPos(x,y,z);}}
 private final BlockPos pos;private final Direction forward;private final int tier;
 public GreenhouseBlueprint(BlockPos pos,Direction forward,int tier){this.pos=pos;this.forward=forward;this.tier=tier;}
 public List<BlockPlace> blocks(){Map<BlockPos,BlockPlace> unique=new LinkedHashMap<>();for(BlockPlace p:tier==1?basic():advanced())unique.put(p.pos(),p);return List.copyOf(unique.values());}
 private int intValues(int value,BlockType ignored){return (tier==1?5:8)-value;}
'''+orient+'\n'+'\n'.join(parts)+'\n}\n'
(root/'src/main/java/com/foundations/calculator/content/GreenhouseBlueprint.java').write_text(text)
