package com.foundations.calculator.core;
import com.foundations.calculator.compat.LongEnergyBridge;
import com.foundations.calculator.core.PowerPolicy.Scope;
public final class PowerPolicyAssertions {
    private static int checks;
    private static void check(boolean test,String text){checks++;if(!test)throw new AssertionError(text);}
    public static void main(String[] args){
        var values=CalculatorConfig.TEST_VALUES;var policy=PowerPolicy.MEKANISM;
        var plan=policy.conversion(2.5,true);
        check(plan==policy.conversion(2.5,true),"Steady-state queries reuse immutable plan");
        values.put("power.mekanism.inputLossPercent",10);
        var changed=policy.conversion(2.5,true);check(changed!=plan&&changed.inputLossPercent()==10,"Loss reload rebuilds plan");
        check(policy.conversion(Double.NaN,true)==null,"Invalid plan closes port");
        check(policy.conversion(2.5,true)!=null,"Valid recovery");
        var store=new StoredEnergy(10000,()->{});
        var port=LongEnergyBridge.fromFE(store,()->policy.conversion(2.5,true),()->policy.input(Scope.BLOCK),()->policy.output(Scope.BLOCK));
        check(port.receive(25,false)==25&&store.getEnergyStored()==9,"Live policy applied to bridge");
        values.put("power.mekanism.blockPorts",false);
        check(port.receive(25,false)==0&&!port.canExtract()&&policy.input(Scope.ITEM),"Scope gates remain independent");
        values.put("power.mekanism.blockPorts",true);values.put("power.mekanism.input",false);
        check(!port.canReceive()&&port.canExtract(),"Direction gates independent");
        values.put("compat.mekanism",false);check(!port.canExtract(),"Legacy master still honored");
        values.clear();check(port.canReceive()&&port.canExtract(),"Cached capability recovers without recreation");
        check(!PowerPolicy.AE2.enabled(Scope.BLOCK),"AE2 does not advertise fake block support");
        check(PowerPolicy.GREGTECH.input(Scope.ITEM),"Default EU item input enabled");
        values.put("compat.gtceu.euToFE",false);
        check(!PowerPolicy.GREGTECH.input(Scope.ITEM)&&!PowerPolicy.GREGTECH.input(Scope.BLOCK),"Legacy EU input still honored");
        check(PowerPolicy.GREGTECH.output(Scope.ITEM),"Legacy EU input does not disable output");
        values.clear();var raw=new StoredEnergy(100,()->{});var guarded=PowerPolicy.guardFE(raw,Scope.BLOCK);
        values.put("power.fe.blockPorts",false);
        check(guarded.receiveEnergy(1,false)==0&&!guarded.canExtract(),"FE capability gate is live");
        check(raw.receiveEnergy(1,false)==1,"FE gate does not disable storage used by native bridges");
        values.clear();check(guarded.extractEnergy(1,false)==1,"FE capability gate recovers");
        System.out.println("POWER_POLICY_PASS checks="+checks);
    }
}
