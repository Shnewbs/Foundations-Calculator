from pathlib import Path
import subprocess,json,collections
P=Path(__file__).resolve().parents[1]
T=P/'tools/guide-geometry-fixtures'; (T/'net/minecraft/core').mkdir(parents=True,exist_ok=True)
(T/'net/minecraft/core/BlockPos.java').write_text('''package net.minecraft.core;
public record BlockPos(int x,int y,int z){public static final BlockPos ZERO=new BlockPos(0,0,0);public int getX(){return x;}public int getY(){return y;}public int getZ(){return z;}}
''')
(T/'net/minecraft/core/Direction.java').write_text('''package net.minecraft.core;
public enum Direction {DOWN(0,-1,0),UP(0,1,0),NORTH(0,0,-1),SOUTH(0,0,1),WEST(-1,0,0),EAST(1,0,0);
private final int x,y,z;Direction(int x,int y,int z){this.x=x;this.y=y;this.z=z;}
public int getStepX(){return x;}public int getStepY(){return y;}public int getStepZ(){return z;}public int get3DDataValue(){return ordinal();}
public Direction getOpposite(){return switch(this){case DOWN->UP;case UP->DOWN;case NORTH->SOUTH;case SOUTH->NORTH;case WEST->EAST;case EAST->WEST;};}
public Direction getClockWise(){return switch(this){case NORTH->EAST;case EAST->SOUTH;case SOUTH->WEST;case WEST->NORTH;default->throw new IllegalStateException();};}}
''')
(T/'ExportBlueprint.java').write_text('''import com.foundations.calculator.content.GreenhouseBlueprint;import net.minecraft.core.*;
public class ExportBlueprint {public static void main(String[] args){for(int tier=1;tier<=2;tier++)for(Direction forward:new Direction[]{Direction.NORTH,Direction.EAST,Direction.SOUTH,Direction.WEST})for(var p:new GreenhouseBlueprint(BlockPos.ZERO,forward,tier).blocks()){
int side=p.x()*forward.getClockWise().getStepX()+p.z()*forward.getClockWise().getStepZ();int inside=p.x()*forward.getStepX()+p.z()*forward.getStepZ();
System.out.println(tier+"\\t"+forward+"\\t"+side+"\\t"+p.y()+"\\t"+inside+"\\t"+p.type()+"\\t"+p.meta());}}}
''')
out=P/'build/guide-geometry';out.mkdir(parents=True,exist_ok=True)
subprocess.run(['javac','--release','21','-d',str(out),*[str(f) for f in T.rglob('*.java')],str(P/'src/main/java/com/foundations/calculator/content/GreenhouseBlueprint.java')],check=True)
r=subprocess.run(['java','-cp',str(out),'ExportBlueprint'],check=True,capture_output=True,text=True)
(P/'docs/guide').mkdir(parents=True,exist_ok=True)
(P/'docs/guide/blueprint-export.tsv').write_text(r.stdout)
by=collections.defaultdict(dict)
for line in r.stdout.splitlines():
 tier,face,x,y,z,kind,meta=line.split('\t');by[(int(tier),face)][(int(x),int(y),int(z))]=kind
for tier in [1,2]:
 base=by[(tier,'NORTH')]
 for face in ['EAST','SOUTH','WEST']:assert by[(tier,face)]==base
 print('tier',tier,'unique structure blocks',len(base),'counts',dict(collections.Counter(base.values())))
print('All four rotated blueprint maps agree in controller-relative coordinates.')
