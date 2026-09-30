package com.foundations.calculator.core;

import java.util.*;

/** Dependency-free checks against actual production policies. No Minecraft performance claims. */
public final class R7CoreAssertions {
    private static int checks;
    private static void check(boolean value,String message) { checks++;if(!value)throw new AssertionError(message); }
    public static int run() {
        checks=0;
        for(boolean enabled:new boolean[]{false,true})for(boolean supported:new boolean[]{false,true})
        for(int mode=-5;mode<=8;mode++)for(boolean output:new boolean[]{false,true}) {
            boolean expected=enabled&&supported&&(mode==0||mode==(output?2:1));
            check(AutomationRules.permits(enabled,supported,mode,output)==expected,"automation matrix");
        }
        long[] positions={0,1,-1,Long.MAX_VALUE,Long.MIN_VALUE,123456789,-887891231};
        for(long position:positions)for(int interval:new int[]{1,2,3,10,20,60,200,1200}) {
            int hits=0;for(long t=-2L*interval;t<2L*interval;t++)if(StaggeredWork.due(t,position,interval,true))hits++;
            check(hits==4,"exact cadence");int phase=StaggeredWork.phase(position,interval);
            check(phase>=0&&phase<interval&&phase==StaggeredWork.phase(position,interval),"stable phase");
            check(StaggeredWork.due(phase,position,interval,true),"phase fires");
            check(StaggeredWork.due(Long.MIN_VALUE,position,interval,true)==
                (Math.floorMod(Long.MIN_VALUE,(long)interval)==phase),"no time overflow");
            for(long t=-interval;t<=interval;t++)check(StaggeredWork.due(t,position,interval,false)==(Math.floorMod(t,interval)==0),"unstaggered cadence");
        }
        Set<Integer> buckets=new HashSet<>();for(int x=0;x<128;x++)buckets.add(StaggeredWork.phase(((long)x<<38)|64,20));
        check(buckets.size()>=16,"neighboring positions spread checks");
        try{StaggeredWork.due(1,1,0,true);throw new AssertionError("zero cadence accepted");}catch(IllegalArgumentException expected){checks++;}
        var stamp=new ChangeStamp<Map<String,Integer>>();var state=new HashMap<String,Integer>();
        check(stamp.differs(state),"initial publish");stamp.capture(()->Map.copyOf(state));
        for(int i=0;i<1000;i++)check(!stamp.differs(state),"unchanged state does not publish");
        state.put("mode",3);check(stamp.differs(state),"in-place mutation detected");stamp.capture(()->Map.copyOf(state));
        check(!stamp.differs(state),"new snapshot captured");state.remove("mode");check(stamp.differs(state),"field removal detected");
        stamp.clear();check(stamp.differs(state),"load/unload invalidates snapshot");return checks;
    }
    public static void main(String[] args){System.out.println("R7_CORE_PASS checks="+run()+" (automation, cadence and change-snapshot policies; not a game benchmark)");}
    private R7CoreAssertions(){}
}
