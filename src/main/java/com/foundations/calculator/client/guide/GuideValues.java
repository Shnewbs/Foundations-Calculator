package com.foundations.calculator.client.guide;
import com.foundations.guide.api.*;
import com.foundations.guide.api.GuideData.*;
import com.foundations.calculator.network.*;
import com.foundations.calculator.core.*;
import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.network.PacketDistributor;
/** Session-scoped bounded snapshots; transport/authority belongs to Calculator, not the master reader. */
public final class GuideValues {
 private static final GuideSnapshotCache cache=new GuideSnapshotCache();
 private static boolean registered;private static long config=-1,research=-1;
 public static void register(){if(!registered){GuideApi.registerProvider("foundations_calculator",GuideValues::read);GuideRecipeProvider.register();registered=true;}}
 private static void session(){var mc=Minecraft.getInstance();cache.session(mc.getConnection());long c=CalculatorConfig.revision(),r=mc.level==null?0:ResearchData.revision(mc.level);if(config!=c||research!=r){cache.refresh();config=c;research=r;}}
 public static void clear(){GuideRecipeProvider.clear();cache.clear();config=-1;research=-1;}
 public static long revision(){return cache.revision();}
 public static void refresh(){GuideRecipeProvider.clear();cache.refresh();}
 private static LiveResult read(LiveRequest request){
  session();var mc=Minecraft.getInstance();if(mc.getConnection()==null||mc.level==null)return LiveResult.unavailable("Not connected to a world");
  return cache.request(request.key(),System.nanoTime(),q->PacketDistributor.sendToServer(new GuideQueryPayload(q.token(),q.key())));
 }
 public static void receive(GuideSnapshotPayload p){session();cache.receive(p.token(),p.key(),new LiveResult(p.available()?State.READY:State.UNAVAILABLE,"Server snapshot · use Refresh to update",p.lines()));}
 private GuideValues(){}
}
