package com.foundations.calculator;

import java.util.*;
import com.foundations.calculator.content.*;
import com.foundations.calculator.core.*;
import com.foundations.calculator.menu.CalculatorMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.*;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.*;
import net.minecraft.world.level.GameType;
import net.neoforged.neoforge.gametest.*;

/** The reported dirty-chip path, using loaded recipes, normal menu insertion and natural ticks. */
@GameTestHolder(FoundationsCalculator.ID)
@PrefixGameTestTemplate(false)
public final class CircuitProcessingGameTests {
    @GameTest(template="large",timeoutTicks=560)
    public static void processingChamberConvertsEveryDirtyAndDamagedVariant(GameTestHelper h){
        checkFamily(h,"processing_chamber",List.of("circuit_dirty","circuit_damaged"),500);
    }
    @GameTest(template="large",timeoutTicks=1060)
    public static void restorationChamberConvertsEveryDirtyVariant(GameTestHelper h){
        checkFamily(h,"restoration_chamber",List.of("circuit_dirty"),1000);
    }
    @GameTest(template="large",timeoutTicks=1060)
    public static void reassemblyChamberConvertsEveryDamagedVariant(GameTestHelper h){
        checkFamily(h,"reassembly_chamber",List.of("circuit_damaged"),1000);
    }
    private static void checkFamily(GameTestHelper h,String kind,List<String> inputFamilies,int ticks){
        var machines=new ArrayList<MachineBlockEntity>();var player=h.makeMockPlayer(GameType.SURVIVAL);
        for(String family:inputFamilies)for(int variant=0;variant<14;variant++){
            int index=machines.size();var p=new BlockPos(2+(index%7)*2,4,2+(index/7)*2);
            h.setBlock(p,Content.block(kind));var m=(MachineBlockEntity)h.getBlockEntity(p);machines.add(m);player.setPos(m.getBlockPos().getCenter());
            var menu=CalculatorMenu.forMachine(1,player.getInventory(),m);
            menu.setCarried(new ItemStack(Content.item(family+"_"+variant)));menu.clicked(0,0,ClickType.PICKUP,player);
            menu.setCarried(new ItemStack(Items.REDSTONE));menu.clicked(20,0,ClickType.PICKUP,player);
            h.assertTrue(menu.getCarried().isEmpty(),"Normal input and fuel clicks must be accepted");
            var status=MachineDiagnostics.machine(m);h.assertTrue(status.status()==MachineStatus.NEED_POWER&&status.requiredEnergy()==1000,"Loaded recipe must match "+kind+" / "+family+"_"+variant);
        }
        h.runAtTickTime(50,()->{
            for(var m:machines)h.assertTrue(m.progress>0&&m.energy.getEnergyStored()==1000,"Chip processing must start from consumable power");
        });
        h.runAtTickTime(ticks+20,()->{
            for(int index=0;index<machines.size();index++){
                var m=machines.get(index);var output=m.inventory.getStackInSlot(14);
                h.assertTrue(output.is(Content.item("circuit_board_"+(index%14)))&&output.getCount()==1,"Circuit variant must survive processing in "+kind);
                h.assertTrue(m.inventory.getStackInSlot(0).isEmpty()&&m.inventory.getStackInSlot(20).isEmpty()&&m.energy.getEnergyStored()==0,"One chip and exactly 1000 FE must be consumed");
                h.assertTrue(CircuitData.tag(output).contains("Stable")&&!CircuitData.analysed(output),"Produced board must initialize its analysis rolls");
            }h.succeed();
        });
    }
}
