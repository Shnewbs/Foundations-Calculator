package com.foundations.calculator.core;

import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.atomic.*;
import com.foundations.calculator.compat.LongEnergyBridge;
import net.neoforged.neoforge.energy.IEnergyStorage;

/** Runs unchanged against the real FE interface in Gradle, or a test-only interface offline. */
public final class PowerCoreAssertions {
    private static int checks;
    private static void check(boolean condition,String message) { checks++; if(!condition)throw new AssertionError(message); }
    private static void invalid(Runnable action) {
        boolean rejected=false;try{action.run();}catch(IllegalArgumentException|ArithmeticException expected){rejected=true;}
        check(rejected,"Invalid setting must be rejected");
    }
    private static boolean le(long a,long b,long c,long d) {
        return BigInteger.valueOf(a).multiply(BigInteger.valueOf(b)).compareTo(BigInteger.valueOf(c).multiply(BigInteger.valueOf(d)))<=0;
    }
    public static class NativeStore implements LongEnergyBridge.Storage {
        public long amount, capacity=Long.MAX_VALUE, limit=Long.MAX_VALUE; public boolean receive=true, extract=true;
        public long receive(long n,boolean simulate) { long accepted=receive?Math.max(0,Math.min(Math.min(n,limit),capacity-amount)):0; if(!simulate)amount+=accepted;return accepted; }
        public long extract(long n,boolean simulate) { long accepted=extract?Math.max(0,Math.min(Math.min(n,limit),amount)):0;if(!simulate)amount-=accepted;return accepted; }
        public long stored(){return amount;}public long capacity(){return capacity;}
        public boolean canReceive(){return receive;}public boolean canExtract(){return extract;}
    }
    public static int run() {
        checks=0;
        var j=EnergyRatio.nativePerFE(2.5);
        check(j.nativeUnits()==5&&j.feUnits()==2,"2.5 J/FE must become 5 J:2 FE");
        var inverse=EnergyRatio.fePerNative(2.5);
        check(inverse.nativeUnits()==2&&inverse.feUnits()==5,"FE/native must invert the exact decimal, not a floating reciprocal");
        check(EnergyRatio.fePerNative(0.0001).nativeUnits()==10000,"Small decimal rate");
        check(EnergyRatio.nativePerFE(1000000).nativeUnits()==1000000,"Exponent notation");
        for(double bad:new double[]{0,-1,Double.NaN,Double.POSITIVE_INFINITY,Double.NEGATIVE_INFINITY,1e-12})invalid(()->EnergyRatio.nativePerFE(bad));
        invalid(()->EnergyConversion.of(j,-1,0));invalid(()->EnergyConversion.of(j,0,100));
        invalid(()->new EnergyRatio(0,1));invalid(()->new EnergyRatio(1,0));

        AtomicInteger dirty=new AtomicInteger();var fe=new StoredEnergy(101,dirty::incrementAndGet);
        var bridge=LongEnergyBridge.fromFE(fe,()->j,()->true);
        check(bridge.receive(Long.MAX_VALUE,true)==250&&fe.getEnergyStored()==0&&dirty.get()==0,"Simulation is read-only and bounded");
        check(bridge.receive(Long.MAX_VALUE,false)==250&&fe.getEnergyStored()==100,"R5 receive quanta regression");
        check(bridge.receive(4,false)==0&&fe.getEnergyStored()==100,"Subquantum input stays with sender");
        check(bridge.extract(249,false)==245&&fe.getEnergyStored()==2,"R5 partial extraction");
        check(bridge.extract(Long.MAX_VALUE,false)==5&&fe.getEnergyStored()==0,"R5 final extraction");
        for(long bad:new long[]{0,-1,Long.MIN_VALUE})check(bridge.receive(bad,false)==0&&bridge.extract(bad,false)==0,"Nonpositive long request");

        var eu=EnergyRatio.fePerNative(4);var loss=EnergyConversion.of(eu,10,20);
        var buffer=new StoredEnergy(100000,()->{});
        var nativePort=LongEnergyBridge.fromFE(buffer,()->loss,()->true,()->true);
        check(nativePort.receive(100,false)==100&&buffer.getEnergyStored()==360,"Input loss must credit 360 FE for 100 EU");
        check(nativePort.extract(Long.MAX_VALUE,false)==72&&buffer.getEnergyStored()==0,"Both losses leave 72 EU after a 100 EU roundtrip");
        var battery=new NativeStore();var wrapped=LongEnergyBridge.toFE(battery,()->loss,()->true,()->true);
        check(wrapped.receiveEnergy(100,true)==100&&battery.amount==0,"External item simulation");
        check(wrapped.receiveEnergy(100,false)==100&&battery.amount==20,"Charging uses OUTPUT policy");
        check(wrapped.extractEnergy(100,false)==72&&battery.amount==0,"Discharging uses INPUT policy");

        AtomicBoolean input=new AtomicBoolean(true),output=new AtomicBoolean(true);
        var live=LongEnergyBridge.fromFE(buffer,()->EnergyConversion.of(eu,0,0),input::get,output::get);
        var external=LongEnergyBridge.toFE(battery,()->EnergyConversion.of(eu,0,0),input::get,output::get);
        input.set(false);
        check(!live.canReceive()&&live.receive(10,false)==0&&live.canExtract(),"Input toggle on cached exposed port");
        check(!external.canExtract()&&external.canReceive(),"Wrapped port swaps policy direction correctly");
        output.set(false);
        check(!external.canReceive()&&external.receiveEnergy(100,false)==0&&!live.canExtract(),"Output toggle is immediate");
        input.set(true);output.set(true);
        check(live.receive(1,false)==1&&buffer.getEnergyStored()==4,"Toggle recovery does not need a new port");

        check(loss.inputPacketFE(32,4)==115,"GT packet input rounds down 115.2 FE to 115");
        check(loss.outputPacketFE(32,4)==160,"GT packet output includes 20 percent loss");
        check(EnergyConversion.of(eu,0,10).outputPacketFE(32,4)==143,"GT output 142.222 FE costs 143");
        check(loss.inputPacketFE(Long.MAX_VALUE,4)==0&&loss.outputPacketFE(Long.MAX_VALUE,4)==0,"GT voltage overflow");
        check(EnergyConversion.of(eu,99,99).inputPacketFE(1,1)==0,"Sub-FE incoming packets refused");
        check(EnergyConversion.of(eu,0,99).outputPacketFE(Integer.MAX_VALUE,1)==0,"Oversized loss-adjusted output refused");
        check(loss.inputPacketFE(-1,4)==0&&loss.outputPacketFE(1,0)==0,"Invalid packet inputs");

        var budget=new TransferBudget();check(budget.available(10,4)==4,"Initial amp allowance");budget.consume(10,3);
        check(budget.remaining(11,4)==4&&budget.available(10,4)==1,"Diagnostics must not reset live budget");
        budget.consume(10,Long.MAX_VALUE);check(budget.available(10,4)==0,"Budget saturates safely");
        check(budget.available(11,4)==4,"New tick gets independent budget");

        AtomicReference<EnergyConversion> changing=new AtomicReference<>(EnergyConversion.of(eu,0,0));
        var dynamic=LongEnergyBridge.fromFE(buffer,changing::get,()->true,()->true);buffer.load(0);
        check(dynamic.receive(2,false)==2&&buffer.getEnergyStored()==8,"Initial live ratio");
        changing.set(EnergyConversion.of(EnergyRatio.fePerNative(8),0,0));
        check(dynamic.receive(2,false)==2&&buffer.getEnergyStored()==24,"Cached capability reads updated ratio");
        changing.set(null);check(!dynamic.canReceive()&&dynamic.receive(1,false)==0&&dynamic.extract(1,false)==0,"Invalid plan fails closed");
        changing.set(EnergyConversion.of(eu,0,0));check(dynamic.canReceive(),"Invalid ratio can recover");

        var huge=new StoredEnergy(Integer.MAX_VALUE,()->{});var hugeRatio=new EnergyRatio(Long.MAX_VALUE,1);
        var hugeBridge=LongEnergyBridge.fromFE(huge,()->hugeRatio,()->true);
        check(hugeBridge.receive(Long.MAX_VALUE,false)==Long.MAX_VALUE&&huge.getEnergyStored()==1,"Long.MAX_VALUE native quantum");
        check(hugeBridge.extract(Long.MAX_VALUE,false)==Long.MAX_VALUE&&huge.getEnergyStored()==0,"Huge quantum roundtrip");

        // A partial commit after a generous simulation may waste a rounding remainder, but cannot create energy.
        NativeStore partialNative=new NativeStore(){public long receive(long n,boolean sim){return super.receive(sim?n:Math.min(1,n),sim);}};
        var partialWrapper=LongEnergyBridge.toFE(partialNative,()->j,()->true);
        check(partialWrapper.receiveEnergy(2,false)==2&&partialNative.amount==1,"Partial native commit still costs positive FE");
        IEnergyStorage partialFE=new IEnergyStorage(){int stored;
            public int receiveEnergy(int n,boolean sim){if(sim)return n;int a=Math.min(1,n);stored+=a;return a;}
            public int extractEnergy(int n,boolean sim){return 0;}public int getEnergyStored(){return stored;}
            public int getMaxEnergyStored(){return 100;}public boolean canReceive(){return true;}public boolean canExtract(){return false;}
        };
        var partialExpose=LongEnergyBridge.fromFE(partialFE,()->eu,()->true);
        check(partialExpose.receive(1,false)==1&&partialFE.getEnergyStored()==1,"Partial FE commit still consumes positive native energy");

        Random random=new Random(0xF06D);
        EnergyRatio[] ratios={new EnergyRatio(1,4),j,inverse,new EnergyRatio(125,32),new EnergyRatio(1,1),new EnergyRatio(10000,1),new EnergyRatio(1,1000000)};
        for(var base:ratios)for(int inLoss:new int[]{0,1,10,33,99})for(int outLoss:new int[]{0,1,10,33,99})for(int iteration=0;iteration<50;iteration++){
            var plan=EnergyConversion.of(base,inLoss,outLoss);var store=new StoredEnergy(1+random.nextInt(2000000),()->{});
            var port=LongEnergyBridge.fromFE(store,()->plan,()->true,()->true);
            long offered=iteration%7==0?Long.MAX_VALUE:random.nextInt(2000000);
            long simulated=port.receive(offered,true);check(store.getEnergyStored()==0,"Property: input simulation must not mutate");
            long spent=port.receive(offered,false);int credited=store.getEnergyStored();
            check(spent==simulated&&spent>=0&&spent<=offered,"Property: native receive honors simulation and bound");
            check(le(credited,base.nativeUnits(),spent,base.feUnits()),"Property: incoming conversion never creates energy");
            long expected=port.extract(Long.MAX_VALUE,true);check(store.getEnergyStored()==credited,"Property: output simulation must not mutate");
            long extracted=port.extract(Long.MAX_VALUE,false);int feSpent=credited-store.getEnergyStored();
            check(extracted==expected&&extracted>=0&&extracted<=spent,"Property: native roundtrip cannot increase energy");
            check(le(extracted,base.feUnits(),feSpent,base.nativeUnits()),"Property: outgoing conversion never creates energy");
            NativeStore ext=new NativeStore();var view=LongEnergyBridge.toFE(ext,()->plan,()->true,()->true);
            int offeredFE=iteration%7==0?Integer.MAX_VALUE:random.nextInt(2000000);
            int predicted=view.receiveEnergy(offeredFE,true);check(ext.amount==0,"Property: external receive simulation is read-only");
            int consumed=view.receiveEnergy(offeredFE,false);long nativeStored=ext.amount;
            check(consumed==predicted&&consumed>=0&&consumed<=offeredFE,"Property: wrapped receive honors FE bound");
            check(le(nativeStored,base.feUnits(),consumed,base.nativeUnits()),"Property: external charge cannot create energy");
            int returned=view.extractEnergy(Integer.MAX_VALUE,false);long nativeSpent=nativeStored-ext.amount;
            check(returned>=0&&returned<=consumed,"Property: wrapped roundtrip cannot increase FE");
            check(le(returned,base.nativeUnits(),nativeSpent,base.feUnits()),"Property: external discharge cannot create energy");
        }
        return checks;
    }
    public static void main(String[] args){System.out.println("POWER_CORE_PASS checks="+run());}
    private PowerCoreAssertions(){}
}
