package com.foundations.calculator;

import com.foundations.calculator.content.Content;
import com.foundations.calculator.core.*;
import java.util.List;
import com.foundations.calculator.content.CalculatorSapling;
import com.foundations.calculator.content.MachineBlockEntity;
import com.foundations.calculator.core.StoredEnergy;
import com.foundations.calculator.core.UuidTags;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;

/** Opt-in regressions against a real world and real capabilities; absent from release JARs. */
@EventBusSubscriber(modid=FoundationsCalculator.ID)
public final class NativePortSmoke {
    private static int assertions;
    private static void check(boolean passed,String message){
        if(!passed)throw new IllegalStateException("Native port regression: "+message);
        assertions++;
    }
    private static MachineBlockEntity place(ServerLevel level,BlockPos pos,String kind){
        level.getChunkAt(pos);
        if(level.getBlockEntity(pos) instanceof MachineBlockEntity previous)previous.suppressDrops=true;
        level.setBlockAndUpdate(pos,Blocks.AIR.defaultBlockState());
        level.setBlockAndUpdate(pos,Content.block(kind).defaultBlockState());
        check(level.getBlockEntity(pos) instanceof MachineBlockEntity,"Registered machine block creates its native block entity");
        return (MachineBlockEntity)level.getBlockEntity(pos);
    }
    private static void performance(net.minecraft.server.level.ServerLevel level,com.foundations.calculator.content.MachineBlockEntity m){
        m.inventory.setStackInSlot(0,new ItemStack(Items.DIRT,2));
        var input=new ProcessInput(List.of(m.inventory.getStackInSlot(0)));
        var expected=RecipeIndex.forMachine(level,"extraction_chamber",null).stream().filter(r->r.value().matches(input,level)).findFirst();
        check(expected.isPresent()&&expected.equals(RecipeIndex.firstMatch(level,"extraction_chamber",null,input)),"First match preserves sorted selection");
        check(RecipeIndex.firstMatch(level,"extraction_chamber",null,input,r->false).isEmpty(),"Eligibility remains live");
        var selection=new MachineRecipeSelection();
        var first=selection.find(level,"extraction_chamber",null,m.inventory,0,1);
        check(first.isPresent()&&first==selection.find(level,"extraction_chamber",null,m.inventory,0,1),"Unchanged lane reuses its selection");
        check(selection.find(level,"extraction_chamber",null,m.inventory,2,1).isEmpty(),"Another lane cannot reuse a match from slot zero");
        m.inventory.setStackInSlot(2,new ItemStack(Items.DIRT,2));
        var lane=selection.find(level,"extraction_chamber",null,m.inventory,2,1);
        check(lane.isPresent(),"Changing lane contents restores matching");
        RecipeIndex.clear();
        check(lane!=selection.find(level,"extraction_chamber",null,m.inventory,2,1),"Reload invalidates a lane cache");

        var id=UUID.randomUUID();var data=ResearchData.get(level.getServer());
        var before=data.snapshot(id);
        ResearchData.completed(level,id,"calculator");
        var after=data.snapshot(id);
        check(before.mastery().getOrDefault("calculator",0L)==0,"Previous sent snapshot stays unchanged");
        check(after.mastery().getOrDefault("calculator",0L)==1&&!before.equals(after),"Mastery change produces a different packet");
        check(after.equals(data.snapshot(id)),"Unchanged state compares equal for packet suppression");
        var group="performance_"+UUID.randomUUID();
        try{data.unlock(id,group);var unlocked=data.snapshot(id);check(unlocked.groups().contains(group)&&!after.equals(unlocked),"Unlock changes packet contents");}
        finally{data.revoke(id,group);}
    }
    @SuppressWarnings("unchecked") private static void expandedOutputs(ServerLevel level,BlockPos pos){
        for(String kind:List.of("extraction_chamber","precision_chamber")){
            var setting=(net.neoforged.neoforge.common.ModConfigSpec.ConfigValue<Integer>)CalculatorConfig.value("machine."+kind+".outputSlots").orElseThrow();int before=setting.get();
            var m=place(level,pos,kind);check(m.outputBuffer.configured()==6,"Default output capacity remains six");
            try{setting.set(54);CalculatorConfig.settingsChanged();
                for(int i=0;i<6;i++)m.inventory.setStackInSlot(14+i,new ItemStack(Items.COBBLESTONE,64));
                m.pending.add(new ItemStack(Items.DIAMOND,12));m.outputBuffer.flush(m.pending);
                check(m.pending.isEmpty()&&m.outputBuffer.get(6).getCount()==12,"Pending results enter expanded bank");
                m.outputBuffer.set(53,new ItemStack(Items.EMERALD,5));m.inventory.setStackInSlot(20,new ItemStack(Items.REDSTONE));
                var saved=m.saveWithFullMetadata(level.registryAccess());var copy=(MachineBlockEntity)BlockEntity.loadStatic(pos,m.getBlockState(),saved,level.registryAccess());
                check(copy!=null&&copy.outputBuffer.get(6).getCount()==12&&copy.outputBuffer.get(53).getCount()==5,"Native ValueIO preserves both ends of the extra bank");
                check(copy.inventory.getStackInSlot(14).getCount()==64&&copy.inventory.getStackInSlot(20).is(Items.REDSTONE),"Legacy output and battery indices stay unchanged");
                var out=level.getCapability(Capabilities.Item.BLOCK,pos,Direction.DOWN);var resource=ItemResource.of(new ItemStack(Items.DIAMOND));
                check(out!=null&&out.size()==73,"Native automation exposes all output slots");
                try(var transaction=Transaction.openRoot()){check(out.extract(25,resource,4,transaction)==4,"Native extraction reaches first extra slot");}
                check(m.outputBuffer.get(6).getCount()==12,"Aborted native extraction restores extra outputs");
                try(var transaction=Transaction.openRoot()){check(out.extract(25,resource,4,transaction)==4,"Committed extra output extraction succeeds");transaction.commit();}
                check(m.outputBuffer.get(6).getCount()==8,"Committed extra output extraction subtracts once");
                try(var transaction=Transaction.openRoot()){check(out.insert(25,resource,4,transaction)==0,"Native insertion into extra output is rejected");transaction.commit();}
                m.program.putInt("Side"+Direction.DOWN.get3DDataValue(),3);
                try(var transaction=Transaction.openRoot()){check(out.extract(25,resource,4,transaction)==0,"Cached native extra output follows disabled side");transaction.commit();}
                m.program.putInt("Side"+Direction.DOWN.get3DDataValue(),0);
                setting.set(6);CalculatorConfig.settingsChanged();check(m.outputBuffer.visible()==54,"Reduced capacity keeps saved overflow accessible");
                try(var transaction=Transaction.openRoot()){check(out.extract(72,ItemResource.of(new ItemStack(Items.EMERALD)),5,transaction)==5,"Saved last output can be drained after capacity reduction");transaction.commit();}
                m.outputBuffer.extract(6,64,false);check(m.outputBuffer.visible()==6,"Draining overflow restores the original six-slot view");
                saved.remove("ExtendedOutputs");var legacy=(MachineBlockEntity)BlockEntity.loadStatic(pos,m.getBlockState(),saved,level.registryAccess());
                check(legacy!=null&&legacy.outputBuffer.get(53).isEmpty()&&legacy.inventory.getStackInSlot(14).getCount()==64,"Legacy save without extra output key loads safely");
            }finally{setting.set(before);CalculatorConfig.settingsChanged();}
        }
    }
    @SubscribeEvent public static void started(ServerStartedEvent event){
        if(!Boolean.getBoolean("foundations.portSmoke"))return;
        assertions=0;
        var level=event.getServer().overworld();
        var pos=new BlockPos(128,96,128);
        expandedOutputs(level,pos.offset(20,0,0));
        var machine=place(level,pos,"processing_chamber");
        performance(level,machine);
        machine.inventory.setStackInSlot(0,ItemStack.EMPTY);
        machine.inventory.setStackInSlot(2,ItemStack.EMPTY);
        var resource=ItemResource.of(new ItemStack(Items.DIAMOND));
        var input=level.getCapability(Capabilities.Item.BLOCK,pos,Direction.UP);
        check(input!=null,"Real registered native item capability exists");
        machine.program.putInt("Regression",7);
        machine.pending.add(new ItemStack(Items.EMERALD,2));
        try(var transaction=Transaction.openRoot()){
            check(input.insert(0,resource,32,transaction)==32,"Native input accepts permitted items");
            machine.program.putInt("Regression",99);
            machine.pending.clear();
        }
        check(machine.inventory.getStackInSlot(0).isEmpty(),"Aborted automation restores inventory");
        check(machine.program.getIntOr("Regression",0)==7,"Aborted automation restores program data");
        check(machine.pending.size()==1&&machine.pending.getFirst().getCount()==2,"Aborted automation restores pending outputs");
        try(var transaction=Transaction.openRoot()){
            check(input.insert(0,resource,16,transaction)==16,"Committed insert reports its actual amount");
            transaction.commit();
        }
        check(machine.inventory.getStackInSlot(0).getCount()==16,"Committed native insert reaches canonical storage");
        machine.program.putInt("Side"+Direction.UP.get3DDataValue(),2);
        try(var transaction=Transaction.openRoot()){
            check(input.insert(0,resource,1,transaction)==0,"A cached capability follows the changed side gate");
            transaction.commit();
        }
        machine.inventory.setStackInSlot(14,new ItemStack(Items.DIAMOND,10));
        var output=level.getCapability(Capabilities.Item.BLOCK,pos,Direction.DOWN);
        check(output!=null,"Native output capability exists");
        try(var transaction=Transaction.openRoot()){
            check(output.extract(14,resource,4,transaction)==4,"Output extraction reports the real amount");
        }
        check(machine.inventory.getStackInSlot(14).getCount()==10,"Aborted output extraction restores the full stack");
        try(var transaction=Transaction.openRoot()){
            check(output.extract(14,resource,4,transaction)==4,"Committed output extraction succeeds");
            transaction.commit();
        }
        check(machine.inventory.getStackInSlot(14).getCount()==6,"Committed extraction subtracts once");
        var owner=UUID.fromString("a655402d-87d4-4871-b12c-e7ad6bf117db");
        UuidTags.put(machine.program,"Owner",owner);
        machine.energy.load(1000);machine.progress=37;
        var saved=machine.saveWithFullMetadata(level.registryAccess());
        check(saved.getLongOr("Energy",0)==1000,"Machine energy is saved under the retained key");
        var restored=BlockEntity.loadStatic(pos,machine.getBlockState(),saved,level.registryAccess());
        check(restored instanceof MachineBlockEntity,"Native ValueIO reload constructs the registered machine");
        var copy=(MachineBlockEntity)restored;
        check(copy.energy.stored()==1000&&copy.progress==37,"Energy and processing state survive native reload");
        check(copy.inventory.getStackInSlot(0).getCount()==16&&copy.inventory.getStackInSlot(14).getCount()==6,"Inventory survives native ValueIO reload");
        check(copy.pending.size()==1&&copy.pending.getFirst().getCount()==2,"Pending outputs survive native reload");
        check(owner.equals(UuidTags.get(copy.program,"Owner")),"Owner UUID survives native reload");
        var bulkPos=pos.offset(4,0,0);
        var chest=place(level,bulkPos,"reinforced_chest");
        var bulk=level.getCapability(Capabilities.Item.BLOCK,bulkPos,Direction.UP);
        check(bulk!=null,"Bulk storage exposes its native capability");
        try(var transaction=Transaction.openRoot()){
            check(bulk.insert(0,resource,192,transaction)==192,"Bulk insertion exceeds one ordinary stack");
            transaction.commit();
        }
        check(bulk.getAmountAsLong(0)==192&&chest.bulk.getStackInSlot(0).getCount()==64,"Native bulk count remains separate from display stacks");
        try(var transaction=Transaction.openRoot()){
            check(bulk.extract(0,resource,64,transaction)==64,"Bulk extraction participates in a real transaction");
        }
        check(chest.bulk.count(0)==192,"Aborted bulk extraction restores its separate count");
        var savedChest=chest.saveWithFullMetadata(level.registryAccess());
        var copiedChest=(MachineBlockEntity)BlockEntity.loadStatic(bulkPos,chest.getBlockState(),savedChest,level.registryAccess());
        check(copiedChest!=null&&copiedChest.bulk.count(0)==192,"Bulk counts survive actual ValueIO save and reload");
        var powerPos=pos.offset(8,0,0);
        var power=place(level,powerPos,"power_cube");
        var powerInput=level.getCapability(Capabilities.Energy.BLOCK,powerPos,Direction.UP);
        var powerOther=level.getCapability(Capabilities.Energy.BLOCK,powerPos,Direction.DOWN);
        check(powerInput!=null&&powerOther!=null,"Real native energy capabilities exist on both faces");
        int accepted;
        try(var transaction=Transaction.openRoot()){
            accepted=powerInput.insert(73,transaction);
            check(accepted>0&&accepted<=73,"Native energy input obeys its transfer budget");
        }
        check(power.energy.stored()==0&&powerOther.getAmountAsLong()==0,"Aborted energy input restores the shared machine balance");
        try(var transaction=Transaction.openRoot()){
            check(powerInput.insert(73,transaction)==accepted,"Committed native energy input reports its actual amount");
            transaction.commit();
        }
        check(power.energy.stored()==accepted&&powerOther.getAmountAsLong()==accepted,"Both energy faces expose one canonical balance");
        int extracted=Math.min(19,accepted);
        try(var transaction=Transaction.openRoot()){
            check(powerOther.extract(19,transaction)==extracted,"Native energy output extracts the permitted amount");
        }
        check(power.energy.stored()==accepted,"Aborted energy output restores the machine balance");
        try(var transaction=Transaction.openRoot()){
            check(powerOther.extract(19,transaction)==extracted,"Committed native energy output succeeds");
            transaction.commit();
        }
        check(power.energy.stored()==accepted-extracted,"Committed energy output subtracts once");
        power.program.putInt("Side"+Direction.UP.get3DDataValue(),2);
        try(var transaction=Transaction.openRoot()){
            check(powerInput.insert(1,transaction)==0,"Cached energy input follows a live output-only side gate");
            transaction.commit();
        }
        power.program.putInt("Side"+Direction.DOWN.get3DDataValue(),1);
        try(var transaction=Transaction.openRoot()){
            check(powerOther.extract(1,transaction)==0,"Cached energy output follows a live input-only side gate");
            transaction.commit();
        }
        var copiedPower=(MachineBlockEntity)BlockEntity.loadStatic(powerPos,power.getBlockState(),power.saveWithFullMetadata(level.registryAccess()),level.registryAccess());
        check(copiedPower!=null&&copiedPower.energy.stored()==accepted-extracted,"The native energy capability balance survives actual world serialization");
        var longEnergy=new StoredEnergy(Long.MAX_VALUE,()->{});
        long exact=9_007_199_254_740_993L;
        longEnergy.load(exact);
        try(var transaction=Transaction.openRoot()){
            check(longEnergy.extractLong(73,transaction)==73,"Native energy handler accepts a transactional long extraction");
        }
        check(longEnergy.stored()==exact,"Aborted native energy extraction preserves all long bits");
        for(int index=0;index<4;index++){
            String kind=new String[]{"amethyst","tanzanite","pear","diamond"}[index];
            BlockPos treePos=pos.offset(20+index*20,0,20);
            for(int x=-5;x<=5;x++)for(int z=-5;z<=5;z++)for(int y=0;y<=12;y++)level.setBlock(treePos.offset(x,y,z),Blocks.AIR.defaultBlockState(),2);
            level.setBlockAndUpdate(treePos.below(),kind.equals("diamond")?Content.block("end_diamond_block").defaultBlockState():Blocks.DIRT.defaultBlockState());
            var sapling=(CalculatorSapling)Content.block(kind+"_sapling");
            level.setBlockAndUpdate(treePos,sapling.defaultBlockState());
            sapling.advanceTree(level,treePos,level.getBlockState(treePos),level.getRandom());
            sapling.advanceTree(level,treePos,level.getBlockState(treePos),level.getRandom());
            check(level.getBlockState(treePos).is(Content.block(kind+"_log")),"Native "+kind+" tree grows from its actual sapling");
        }
        org.slf4j.LoggerFactory.getLogger("FoundationsCalculator").info("FOUNDATIONS_NATIVE_PORT_SMOKE_PASSED assertions={}",assertions);
    }
}
