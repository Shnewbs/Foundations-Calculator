package com.foundations.calculator;

import com.foundations.calculator.content.*;
import com.foundations.calculator.core.*;
import com.foundations.calculator.menu.*;
import com.mojang.authlib.GameProfile;
import java.util.UUID;
import java.util.function.*;
import net.minecraft.core.*;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.*;
import net.neoforged.neoforge.common.*;
import net.neoforged.neoforge.common.util.FakePlayerFactory;
import net.neoforged.neoforge.event.level.BlockEvent;

/** Actual server tools, live config and events; never bundled with gameplay. */
public final class Rc1Assertions {
    @SuppressWarnings("unchecked") private static <T> void setting(String key,T value,Runnable task){
        var setting=(ModConfigSpec.ConfigValue<T>)CalculatorConfig.value(key).orElseThrow();var before=setting.get();
        try{setting.set(value);CalculatorConfig.settingsChanged();task.run();}
        finally{setting.set(before);CalculatorConfig.settingsChanged();}
    }
    public static void run(ServerLevel level,BlockPos pos,BiConsumer<Boolean,String> assertions){
        int[] count={0};BiConsumer<Boolean,String> check=(passed,message)->{assertions.accept(passed,message);count[0]++;};
        var player=FakePlayerFactory.get(level,new GameProfile(UUID.fromString("256438c9-187f-4012-937c-bb64a024af88"),"RcRegression"));
        player.gameMode.changeGameModeForPlayer(GameType.SURVIVAL);player.setPos(pos.getX()+.5,pos.getY()+1,pos.getZ()+.5);
        player.getInventory().clearContent();player.setItemInHand(InteractionHand.MAIN_HAND,new ItemStack(Content.item("flawless_calculator")));
        var battery=player.getMainHandItem();var power=(CalculatorItem)battery.getItem();power.longStorage(battery).receive(10000,false);
        var module=new ItemStack(Content.item("atomic_terrain_module"));var tag=CircuitData.tag(module);
        tag.putString("TerrainSource","minecraft:dirt");tag.put("TerrainTarget",NbtUtils.writeBlockState(Blocks.STONE.defaultBlockState()));UtilityItems.put(module,tag);
        player.getInventory().setItem(8,new ItemStack(Items.STONE,4));
        var context=new UseOnContext(player,InteractionHand.MAIN_HAND,new BlockHitResult(Vec3.atCenterOf(pos),Direction.UP,pos,false));
        level.setBlockAndUpdate(pos,Blocks.DIRT.defaultBlockState());
        long initial=power.energyLong(battery);var dirt=level.getBlockState(pos);
        Consumer<BlockEvent.EntityPlaceEvent> deny=event->{if(event.getPos().equals(pos)){
            check.accept(level.getBlockState(pos).equals(dirt),"Place preflight leaves original world state visible");
            check.accept(event.getPlacedBlock().is(Blocks.STONE),"Place preflight exposes proposed replacement");event.setCanceled(true);
        }};
        NeoForge.EVENT_BUS.addListener(deny);
        try{check.accept(AtomicTerrainModule.use(module,battery,context)==InteractionResult.FAIL,"Atomic terrain respects cancelled placement");}
        finally{NeoForge.EVENT_BUS.unregister(deny);}
        check.accept(level.getBlockState(pos).equals(dirt)&&power.energyLong(battery)==initial&&player.getInventory().getItem(8).getCount()==4,"Denied terrain preserves world, energy and replacement inventory");
        check.accept(AtomicTerrainModule.use(module,battery,context)==InteractionResult.CONSUME,"Authorized atomic terrain still works");
        check.accept(level.getBlockState(pos).is(Blocks.STONE)&&power.energyLong(battery)==initial-1&&player.getInventory().getItem(8).getCount()==3,"Authorized atomic terrain commits exactly once");

        Consumer<BlockEvent.BreakEvent> denyBreak=event->{if(event.getPos().equals(pos))event.setCanceled(true);};
        NeoForge.EVENT_BUS.addListener(denyBreak);
        try{
            var terrain=new ItemStack(Content.item("terrain_module"));
            check.accept(!PlayerWorldActions.replace(context,pos,level.getBlockState(pos),Blocks.DIRT.defaultBlockState(),()->true),"Cancelled break rejects replacement");
            UtilityItems.onBlock("terrain_module",terrain,battery,context);
            check.accept(level.getBlockState(pos).is(Blocks.STONE)&&power.energyLong(battery)==initial-1,"Ordinary terrain uses protection preflight without FE loss");
            level.setBlockAndUpdate(pos,Blocks.OBSIDIAN.defaultBlockState());
            UtilityItems.onBlock("obsidian_key",new ItemStack(Content.item("obsidian_key")),battery,context);
            check.accept(level.getBlockState(pos).is(Blocks.OBSIDIAN),"Obsidian key respects break cancellation");
        }finally{NeoForge.EVENT_BUS.unregister(denyBreak);}
        level.setBlockAndUpdate(pos,Blocks.DIRT.defaultBlockState());
        Consumer<BlockEvent.EntityPlaceEvent> drain=event->{if(event.getPos().equals(pos))power.longStorage(battery).extract(Long.MAX_VALUE,false);};
        NeoForge.EVENT_BUS.addListener(drain);
        try{check.accept(AtomicTerrainModule.use(module,battery,context)==InteractionResult.FAIL,"Permission callback resource changes are revalidated");}
        finally{NeoForge.EVENT_BUS.unregister(drain);}
        check.accept(level.getBlockState(pos).is(Blocks.DIRT)&&player.getInventory().getItem(8).getCount()==3,"Resource change cannot grant free terrain replacement");
        Consumer<BlockEvent.BreakEvent> mutate=event->{if(event.getPos().equals(pos))level.setBlockAndUpdate(pos,Blocks.GOLD_BLOCK.defaultBlockState());};
        NeoForge.EVENT_BUS.addListener(mutate);
        try{check.accept(!PlayerWorldActions.replace(context,pos,level.getBlockState(pos),Blocks.STONE.defaultBlockState(),()->true),"Permission listener target mutations abort the tool");}
        finally{NeoForge.EVENT_BUS.unregister(mutate);}
        check.accept(level.getBlockState(pos).is(Blocks.GOLD_BLOCK),"Tool does not overwrite a listener's newer world state");

        var nutrition=new ItemStack(Content.item("nutrition_module"));NutritionData.set(nutrition,"health",20);NutritionData.set(nutrition,"hunger",20);
        player.setHealth(10);player.getFoodData().setFoodLevel(10);
        setting("content.nutrition_module.enabled",false,()->NutritionData.restore(player,nutrition,2));
        check.accept(player.getHealth()==10&&player.getFoodData().getFoodLevel()==10&&NutritionData.get(nutrition,"health")==20,"Disabled nutrition cannot run automatically");
        NutritionData.restore(player,nutrition,2);check.accept(player.getHealth()==12&&player.getFoodData().getFoodLevel()==12,"Enabled nutrition keeps its configured transfer behavior");

        var smelter=new ItemStack(Content.item("smelting_module"));var unchanged=smelter.copy();
        for(int i=0;i<1000;i++)SmeltingModule.tick(smelter,battery,level);
        check.accept(ItemStack.matches(smelter,unchanged)&&!smelter.has(DataComponents.CUSTOM_DATA),"Thousand idle smelter calls perform no component writes");
        var progress=CircuitData.tag(smelter);progress.putInt("SmeltProgress",10);UtilityItems.put(smelter,progress);SmeltingModule.tick(smelter,battery,level);
        check.accept(CircuitData.tag(smelter).getInt("SmeltProgress")==0,"Removing smelting input still clears progress");

        var selected=CircuitData.tag(battery);selected.putInt("SelectedModule",1);UtilityItems.put(battery,selected);
        var storage=new StorageModuleMenu(91,player.getInventory(),InteractionHand.MAIN_HAND,new ItemStack(Content.item("storage_module")),()->{});
        check.accept(storage.stillValid(player),"Storage projection initially matches its selected module");
        selected.putInt("SelectedModule",2);UtilityItems.put(battery,selected);
        check.accept(!storage.stillValid(player),"Changing selected module invalidates old storage projection");
        var calculator=new ItemStack(Content.item("calculator"));player.setItemInHand(InteractionHand.MAIN_HAND,calculator);
        var menu=CalculatorMenu.forItem(92,player.getInventory(),InteractionHand.MAIN_HAND);
        setting("content.calculator.enabled",false,()->check.accept(!menu.clickMenuButton(player,0),"Existing portable menu cannot bypass disabled content"));
        org.slf4j.LoggerFactory.getLogger("FoundationsCalculator").info("FOUNDATIONS_RC1_REGRESSIONS_PASSED assertions={}",count[0]);
        player.getInventory().clearContent();player.setHealth(20);player.getFoodData().setFoodLevel(20);
        level.setBlockAndUpdate(pos,Blocks.AIR.defaultBlockState());
    }
    private Rc1Assertions(){}
}
