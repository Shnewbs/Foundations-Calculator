package com.foundations.calculator;
import com.foundations.calculator.compat.*;
import net.minecraft.gametest.framework.GameTestHelper;
public final class EnergyIntegrationAssertions {
    public static void mekanism(GameTestHelper h){
        long[] stored={0};var handler=new mekanism.api.energy.IStrictEnergyHandler(){
            public int getEnergyContainerCount(){return 1;}public long getEnergy(int i){return stored[0];}public void setEnergy(int i,long n){stored[0]=n;}public long getMaxEnergy(int i){return 10000;}public long getNeededEnergy(int i){return 10000-stored[0];}
            public long insertEnergy(int i,long amount,mekanism.api.Action action){long n=Math.min(amount,10000-stored[0]);if(action.execute())stored[0]+=n;return amount-n;}
            public long extractEnergy(int i,long amount,mekanism.api.Action action){long n=Math.min(amount,stored[0]);if(action.execute())stored[0]-=n;return n;}
        };
        var bridge=MekanismEnergy.wrap(handler);h.assertTrue(bridge!=null,"Mekanism strict-energy bridge must load");int accepted=bridge.receiveEnergy(100,true);h.assertTrue(stored[0]==0,"Simulation cannot mutate Mekanism energy");
        h.assertTrue(bridge.receiveEnergy(100,false)==accepted,"Simulation and actual insertion agree");
        double ratio=mekanism.api.energy.IEnergyConversionHelper.INSTANCE.feConversion().getConversion();h.assertTrue(Math.abs(stored[0]-accepted*ratio)<1e-8,"FE input creates exactly its native energy equivalent");
        int extracted=bridge.extractEnergy(accepted,false);h.assertTrue(extracted==accepted&&stored[0]==0,"Round-trip conversion cannot create or destroy energy");
        for(int i=0;i<100;i++){int n=bridge.receiveEnergy(1,false);h.assertTrue(Math.abs(stored[0]-n*ratio)<1e-8,"Fractional conversion must defer a sub-quantum transfer");bridge.extractEnergy(n,false);}
    }
    public static void ae2(GameTestHelper h){
        var item=net.minecraft.core.registries.BuiltInRegistries.ITEM.get(net.minecraft.resources.Identifier.parse("ae2:charged_staff"));var stack=new net.minecraft.world.item.ItemStack(item);var bridge=AE2Energy.wrap(stack);
        h.assertTrue(bridge!=null&&bridge.canReceive(),"AE2 powered item API is usable");var nativePower=(appeng.api.implementations.items.IAEItemPowerStorage)item;
        double before=nativePower.getAECurrentPower(stack);int simulated=bridge.receiveEnergy(100,true);h.assertTrue(nativePower.getAECurrentPower(stack)==before,"AE2 simulation cannot charge the item");int charged=bridge.receiveEnergy(100,false);double ratio=com.foundations.calculator.core.CalculatorConfig.decimal("compat.aeEnergyToFE",2);
        h.assertTrue(charged==simulated&&Math.abs(nativePower.getAECurrentPower(stack)-before-charged/ratio)<1e-8,"AE2 injection returns unaccepted energy; bridge must report accepted FE correctly");
        h.assertTrue(bridge.canExtract()==nativePower.getPowerFlow(stack).isAllowExtraction(),"AE2 extraction permissions are respected");
    }
    private EnergyIntegrationAssertions(){}
}
