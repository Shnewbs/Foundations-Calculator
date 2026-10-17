package com.foundations.calculator;

import java.util.*;
import com.foundations.calculator.content.*;
import com.foundations.calculator.core.*;
import com.foundations.calculator.menu.CalculatorMenu;
import net.minecraft.core.*;
import net.minecraft.gametest.framework.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.gametest.*;

@GameTestHolder(FoundationsCalculator.ID)
@PrefixGameTestTemplate(false)
public final class OutputExpansionGameTests {
    private static final List<String> KINDS=List.of("extraction_chamber","precision_chamber");
    private static MachineBlockEntity place(GameTestHelper h,String kind){var pos=new BlockPos(8,4,8);h.setBlock(pos,Content.block(kind));return (MachineBlockEntity)h.getBlockEntity(pos);}
    private static void fill(MachineBlockEntity m){for(int i=0;i<6;i++)m.inventory.setStackInSlot(14+i,new ItemStack(Items.COBBLESTONE,64));}
    @GameTest(template="large",batch="expanded_outputs") public static void defaultsAndReducedCapacityRetainItems(GameTestHelper h){
        for(String kind:KINDS){var m=place(h,kind);fill(m);m.pending.add(new ItemStack(Items.DIAMOND,9));
            h.assertTrue(m.outputBuffer.configured()==6,"Default capacity remains six");m.outputBuffer.flush(m.pending);
            h.assertTrue(m.pending.size()==1&&m.outputBuffer.get(6).isEmpty(),"Default capacity leaves blocked results pending");
            R6GameTests.setting("machine."+kind+".outputSlots",54,()->{m.outputBuffer.flush(m.pending);m.outputBuffer.set(53,new ItemStack(Items.EMERALD,3));h.assertTrue(m.pending.isEmpty()&&m.outputBuffer.get(6).getCount()==9,"Expanded bank receives pending results");});
            h.assertTrue(m.outputBuffer.visible()==54,"Lowered capacity keeps occupied last row accessible");
            var player=h.makeMockPlayer(GameType.SURVIVAL);player.setPos(m.getBlockPos().getCenter());var menu=CalculatorMenu.forMachine(1,player.getInventory(),m);
            h.assertTrue(menu.clickMenuButton(player,1016),"Saved overflow can be paged after lowering capacity");menu.quickMoveStack(player,19);
            h.assertTrue(player.getInventory().countItem(Items.EMERALD)==3&&m.outputBuffer.get(53).isEmpty(),"Shift-click drains the last saved row once");
            for(int i=0;i<6;i++)h.assertTrue(m.outputBuffer.get(i).getCount()==64,"A disappearing last row cannot clear legacy outputs during shift-click");
            var output=m.automation(Direction.DOWN);
            h.assertTrue(output.extractItem(25,9,false).getCount()==9&&m.outputBuffer.visible()==6,"Automation drains overflow and restores six visible slots");
        }h.succeed();
    }
    @GameTest(template="large",batch="expanded_outputs") public static void fullBankBackpressureAndLiveAutomationGates(GameTestHelper h){
        for(String kind:KINDS)R6GameTests.setting("machine."+kind+".outputSlots",54,()->{var m=place(h,kind);
            for(int i=0;i<54;i++)m.outputBuffer.set(i,new ItemStack(Items.COBBLESTONE,64));m.pending.add(new ItemStack(Items.DIAMOND,4));m.outputBuffer.flush(m.pending);
            h.assertTrue(m.pending.getFirst().getCount()==4,"Full bank never loses pending results");var out=m.automation(Direction.DOWN);
            h.assertTrue(out.insertItem(25,new ItemStack(Items.EMERALD),false).getCount()==1&&!out.isItemValid(25,new ItemStack(Items.EMERALD)),"Extra outputs reject external insertion");
            m.program.putInt("Side"+Direction.DOWN.get3DDataValue(),3);h.assertTrue(out.extractItem(25,64,false).isEmpty(),"Cached output obeys disabled side immediately");m.program.putInt("Side"+Direction.DOWN.get3DDataValue(),0);
            h.assertTrue(out.extractItem(25,64,true).getCount()==64&&m.outputBuffer.get(6).getCount()==64,"Simulation leaves bank untouched");out.extractItem(25,64,false);m.outputBuffer.flush(m.pending);
            h.assertTrue(m.pending.isEmpty()&&m.outputBuffer.get(6).is(Items.DIAMOND),"Opening space flushes the retained result exactly once");
        });h.succeed();
    }
    @GameTest(template="large",batch="expanded_outputs") public static void extendedOutputsAndLegacyIndicesSurviveReload(GameTestHelper h){
        for(String kind:KINDS){var m=place(h,kind);m.inventory.setStackInSlot(14,new ItemStack(Items.IRON_INGOT,2));m.inventory.setStackInSlot(20,new ItemStack(Items.REDSTONE));m.inventory.setStackInSlot(21,new ItemStack(Content.item("speed_upgrade")));m.outputBuffer.set(53,new ItemStack(Items.DIAMOND,17));
            var saved=m.saveWithFullMetadata(h.getLevel().registryAccess());var copy=(MachineBlockEntity)BlockEntity.loadStatic(m.getBlockPos(),m.getBlockState(),saved,h.getLevel().registryAccess());
            h.assertTrue(copy!=null&&copy.outputBuffer.get(53).getCount()==17&&copy.inventory.getStackInSlot(14).getCount()==2,"Base and last extra output survive reload");
            h.assertTrue(copy.inventory.getStackInSlot(20).is(Items.REDSTONE)&&Content.path(copy.inventory.getStackInSlot(21)).equals("speed_upgrade"),"Battery and upgrade indices are unchanged");
            saved.remove("ExtendedOutputs");var legacy=(MachineBlockEntity)BlockEntity.loadStatic(m.getBlockPos(),m.getBlockState(),saved,h.getLevel().registryAccess());
            h.assertTrue(legacy!=null&&legacy.outputBuffer.get(53).isEmpty()&&legacy.inventory.getStackInSlot(14).getCount()==2,"Legacy saves load with an empty extra bank");
        }h.succeed();
    }
    @GameTest(template="large",batch="expanded_outputs") public static void outputPagesAreIndependentAndShiftClickCanonicalStorage(GameTestHelper h){
        for(String kind:KINDS)R6GameTests.setting("machine."+kind+".outputSlots",54,()->{var m=place(h,kind);m.outputBuffer.set(0,new ItemStack(Items.IRON_INGOT,2));m.outputBuffer.set(53,new ItemStack(Items.DIAMOND,7));
            var a=h.makeMockPlayer(GameType.SURVIVAL);a.setPos(m.getBlockPos().getCenter());var b=h.makeMockPlayer(GameType.SURVIVAL);b.setPos(m.getBlockPos().getCenter());
            var menu=CalculatorMenu.forMachine(1,a.getInventory(),m);var other=CalculatorMenu.forMachine(2,b.getInventory(),m);
            h.assertTrue(menu.slots.size()==61&&menu.maxOutputRow()==16,"Slot protocol and player indices stay unchanged");
            h.assertTrue(menu.clickMenuButton(a,1016)&&menu.outputRow()==16&&other.outputRow()==0,"Each viewer has an independent selected row");
            h.assertTrue(menu.slots.get(19).getItem().getCount()==7&&other.slots.get(14).getItem().is(Items.IRON_INGOT),"Pages expose canonical stacks without moving base contents");
            h.assertTrue(!menu.clickMenuButton(a,1017)&&menu.outputRow()==16,"Invalid page packets are rejected");
            menu.quickMoveStack(a,19);h.assertTrue(m.outputBuffer.get(53).isEmpty()&&a.getInventory().countItem(Items.DIAMOND)==7&&m.outputBuffer.get(0).getCount()==2,"Offscreen shift-click transfers once and preserves base output");
        });h.succeed();
    }
    @GameTest(template="large",batch="expanded_outputs") public static void processCodecSupportsBoundedLargeResultTables(GameTestHelper h){
        var ops=h.getLevel().registryAccess().createSerializationContext(com.mojang.serialization.JsonOps.INSTANCE);
        var input=new CountedIngredient(net.minecraft.world.item.crafting.Ingredient.of(Items.GRAVEL),1,0);
        var result=new ProcessRecipe.Result(new ItemStack(Items.RAW_IRON),1,"");
        var recipe=new ProcessRecipe("extraction_chamber",List.of(input),Collections.nCopies(54,result),50,20,0,false);
        var json=ProcessRecipe.CODEC.codec().encodeStart(ops,recipe).getOrThrow();
        h.assertTrue(ProcessRecipe.CODEC.codec().parse(ops,json).getOrThrow().outputs().size()==54,"All 54 results survive codec roundtrip");
        json.getAsJsonObject().getAsJsonArray("results").add(json.getAsJsonObject().getAsJsonArray("results").get(0).deepCopy());
        h.assertTrue(ProcessRecipe.CODEC.codec().parse(ops,json).error().isPresent(),"Result tables above 54 are rejected");
        var random=net.minecraft.util.RandomSource.create(123);h.assertTrue(!result.roll(random).isEmpty()&&new ProcessRecipe.Result(new ItemStack(Items.DIAMOND),0,"").roll(random).isEmpty(),"Independent chance zero and one retain their semantics");h.succeed();
    }
    @GameTest(template="large",batch="expanded_outputs") public static void kubeJsCreatesLargeTablesForBothChambers(GameTestHelper h){
        if(net.neoforged.fml.ModList.get().isLoaded("kubejs"))for(String kind:KINDS){
            var found=h.getLevel().getRecipeManager().byKey(net.minecraft.resources.ResourceLocation.parse("foundations_calculator:kubejs_outputs_"+kind));
            h.assertTrue(found.isPresent()&&found.get().value() instanceof ProcessRecipe r&&r.machine().equals(kind)&&r.outputs().size()==54,"KubeJS schema must preserve all 54 results for "+kind);
        }h.succeed();
    }
    @GameTest(template="large",batch="expanded_outputs") public static void bothChambersCommitLargeRecipeTablesExactlyOnce(GameTestHelper h){
        for(String kind:KINDS)R6GameTests.setting("machine."+kind+".outputSlots",54,()->{var m=place(h,kind);m.inventory.setStackInSlot(0,new ItemStack(Items.BARRIER));m.energy.load(17);
            MachineBlockEntity.tick(h.getLevel(),m.getBlockPos(),m.getBlockState(),m);
            h.assertTrue(m.inventory.getStackInSlot(0).isEmpty()&&m.energy.stored()==0&&m.pending.isEmpty(),"Expanded recipe consumes one input and exact energy once");
            for(int i=0;i<8;i++)h.assertTrue(m.outputBuffer.get(i).getCount()==64,"Each guaranteed result is retained, including extras");
            h.assertTrue(m.outputBuffer.get(8).isEmpty(),"Chance-zero result does not occupy a slot");
            MachineBlockEntity.tick(h.getLevel(),m.getBlockPos(),m.getBlockState(),m);h.assertTrue(m.outputBuffer.get(7).getCount()==64,"No repeated output without another input");
        });h.succeed();
    }
    private OutputExpansionGameTests(){}
}
