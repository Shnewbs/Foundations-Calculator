import com.foundations.calculator.content.GreenhouseBlueprint;import net.minecraft.core.*;
public class ExportBlueprint {public static void main(String[] args){for(int tier=1;tier<=2;tier++)for(Direction forward:new Direction[]{Direction.NORTH,Direction.EAST,Direction.SOUTH,Direction.WEST})for(var p:new GreenhouseBlueprint(BlockPos.ZERO,forward,tier).blocks()){
int side=p.x()*forward.getClockWise().getStepX()+p.z()*forward.getClockWise().getStepZ();int inside=p.x()*forward.getStepX()+p.z()*forward.getStepZ();
System.out.println(tier+"\t"+forward+"\t"+side+"\t"+p.y()+"\t"+inside+"\t"+p.type()+"\t"+p.meta());}}}
