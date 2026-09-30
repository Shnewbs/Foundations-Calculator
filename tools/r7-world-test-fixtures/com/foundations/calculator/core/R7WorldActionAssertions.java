package com.foundations.calculator.core;
import java.util.*;import com.foundations.calculator.api.MachineWorldActionEvent;import com.foundations.calculator.content.MachineBlockEntity;import net.minecraft.core.BlockPos;import net.minecraft.server.level.ServerLevel;import net.minecraft.world.item.ItemStack;import net.minecraft.world.level.block.state.BlockState;import net.neoforged.neoforge.common.NeoForge;import net.neoforged.neoforge.common.util.FakePlayerFactory;import net.neoforged.neoforge.event.level.BlockEvent;import net.neoforged.bus.api.*;
public final class R7WorldActionAssertions {
static int checks;static final BlockPos P=new BlockPos(2,64,2);static final BlockState OLD=new BlockState("dirt",false,1),NEW=new BlockState("stone",false,1);
static void check(boolean value,String why){checks++;if(!value)throw new AssertionError(why);}
static MachineBlockEntity fresh(){NeoForge.EVENT_BUS.reset();CalculatorConfig.flags.clear();var m=new MachineBlockEntity(new ServerLevel());m.level.states.put(P,OLD);return m;}
static boolean change(MachineBlockEntity m,MachineWorldActions.Action a){return MachineWorldActions.replace(m,P,OLD,NEW,new ItemStack("resource"),a);}
public static void main(String[] ignored){
 for(var action:MachineWorldActions.Action.values()){
  var m=fresh();var level=m.level;
  NeoForge.EVENT_BUS.listeners.add(e->{check(level.writes==0&&level.getBlockState(P).equals(OLD),"preflight not temporary mutation");if(e instanceof MachineWorldActionEvent a)check(a.getOwner().equals(m.owner),"owner event identity");if(e instanceof BlockEvent.EntityPlaceEvent place)check(place.getState().equals(NEW)&&place.getPlacedBlock().equals(NEW)&&place.snapshot.state().equals(OLD),"proposed event and old snapshot");});
  check(change(m,action)&&level.writes==1&&level.getBlockState(P).equals(NEW),"allowed action single commit");var actor=FakePlayerFactory.last;
  check(actor.getUUID().equals(m.owner)&&actor.getX()==4&&actor.getY()==5&&actor.getZ()==6&&actor.getMainHandItem().name.equals("original"),"actor identity and restoration");
  for(int cancellation=0;cancellation<3;cancellation++){
   var denied=fresh();final int c=cancellation;NeoForge.EVENT_BUS.listeners.add(e->{if(c==0&&e instanceof MachineWorldActionEvent||c==1&&e instanceof BlockEvent.BreakEvent||c==2&&e instanceof BlockEvent.EntityPlaceEvent)((ICancellableEvent)e).setCanceled(true);});
   check(!change(denied,action)&&denied.level.writes==0&&denied.level.getBlockState(P).equals(OLD),"each denial before write");check(denied.program.contains("WorldActionBlocked"),"denial diagnostic");
  }
 }
 var m=fresh();m.owner=null;check(!change(m,MachineWorldActions.Action.BUILD)&&m.level.writes==0,"owner required");CalculatorConfig.flags.put("protection.requireOwner",false);check(change(m,MachineWorldActions.Action.BUILD),"explicit ownerless compatibility");
 m=fresh();m.level.interact=false;check(!change(m,MachineWorldActions.Action.BUILD)&&m.level.writes==0,"world permission denied");
 m=fresh();m.level.unloaded.add(P);check(!change(m,MachineWorldActions.Action.BUILD)&&m.level.unloadedReads==0,"unloaded target never read");
 m=fresh();m.level.unloaded.add(m.getBlockPos());check(!change(m,MachineWorldActions.Action.BUILD)&&m.level.unloadedReads==0,"unloaded controller never read");
 m=fresh();m.level.border=false;check(!change(m,MachineWorldActions.Action.BUILD)&&m.level.writes==0,"world border");
 m=fresh();m.level.states.put(P,new BlockState("bedrock",false,-1));check(!MachineWorldActions.replace(m,P,m.level.getBlockState(P),NEW,ItemStack.EMPTY,MachineWorldActions.Action.DEMOLISH),"unbreakable");
 m=fresh();m.level.states.put(P,new BlockState("chest",true,1));check(!MachineWorldActions.replace(m,P,m.level.getBlockState(P),NEW,ItemStack.EMPTY,MachineWorldActions.Action.DEMOLISH),"block entity target");
 m=fresh();check(!MachineWorldActions.replace(m,P,OLD,new BlockState("chest",true,1),ItemStack.EMPTY,MachineWorldActions.Action.BUILD)&&NeoForge.EVENT_BUS.events.isEmpty(),"block entity replacement");
 m=fresh();check(!MachineWorldActions.replace(m,P,OLD,OLD,ItemStack.EMPTY,MachineWorldActions.Action.BUILD)&&m.level.writes==0,"no-op");
 var mutable=fresh();NeoForge.EVENT_BUS.listeners.add(e->{if(e instanceof MachineWorldActionEvent)mutable.level.states.put(P,BlockState.AIR);});check(!change(mutable,MachineWorldActions.Action.BUILD)&&mutable.level.writes==0&&mutable.level.getBlockState(P).isAir(),"listener mutation is not overwritten");
 var removed=fresh();NeoForge.EVENT_BUS.listeners.add(e->{if(e instanceof MachineWorldActionEvent)removed.removed=true;});check(!change(removed,MachineWorldActions.Action.BUILD)&&removed.level.writes==0,"removed controller cannot commit");
 var ownerChanged=fresh();NeoForge.EVENT_BUS.listeners.add(e->{if(e instanceof MachineWorldActionEvent)ownerChanged.owner=UUID.randomUUID();});check(!change(ownerChanged,MachineWorldActions.Action.BUILD)&&ownerChanged.level.writes==0,"owner changed during callbacks");
 var insufficient=fresh();boolean[] ready={true};NeoForge.EVENT_BUS.listeners.add(e->ready[0]=false);check(!MachineWorldActions.replace(insufficient,P,OLD,NEW,ItemStack.EMPTY,MachineWorldActions.Action.BUILD,()->ready[0])&&insufficient.level.writes==0,"resources rechecked after events");
 var throwing=fresh();NeoForge.EVENT_BUS.listeners.add(e->{throw new IllegalStateException("test listener");});try{change(throwing,MachineWorldActions.Action.BUILD);throw new AssertionError("exception suppressed");}catch(IllegalStateException expected){check(throwing.level.writes==0&&FakePlayerFactory.last.getMainHandItem().name.equals("original")&&FakePlayerFactory.last.getX()==4,"actor restored on exception");}
 m=fresh();m.level.writeSuccess=false;check(!change(m,MachineWorldActions.Action.BUILD)&&m.level.getBlockState(P).equals(OLD),"write failure no successful transaction");
 m=fresh();check(!MachineWorldActions.legacyAllowed(m,P,MachineWorldActions.Action.GROW),"opaque callbacks off by default");CalculatorConfig.flags.put("protection.allowLegacyPlantCallbacks",true);check(MachineWorldActions.legacyAllowed(m,P,MachineWorldActions.Action.GROW),"legacy is explicit opt-in");
 m=fresh();MachineWorldActions.reject(m,P,MachineWorldActions.Action.BUILD,"test");int changes=m.changes;MachineWorldActions.reject(m,P,MachineWorldActions.Action.BUILD,"test");check(m.changes==changes,"unchanged diagnostic no repeated dirty");check(change(m,MachineWorldActions.Action.BUILD)&&!m.program.contains("WorldActionBlocked"),"successful retry clears same target");
 System.out.println("R7_WORLD_POLICY_PASS checks="+checks+" (real guard with minimal test-only world/event fixtures; not NeoForge or claim-mod certification)");
}
}
