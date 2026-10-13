package com.foundations.calculator;

import com.foundations.calculator.content.*;
import com.foundations.calculator.core.*;
import java.util.*;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.gametest.*;

@GameTestHolder(FoundationsCalculator.ID)
@PrefixGameTestTemplate(false)
public final class PerformanceGameTests {
    @GameTest(template="empty") public static void laneSelectionAndFirstMatchStayEquivalent(GameTestHelper h){
        h.setBlock(new BlockPos(1,1,1),Content.block("extraction_chamber"));
        var m=(MachineBlockEntity)h.getBlockEntity(new BlockPos(1,1,1));
        m.inventory.setStackInSlot(0,new ItemStack(Items.DIRT,2));
        var input=new ProcessInput(List.of(m.inventory.getStackInSlot(0)));
        var expected=RecipeIndex.forMachine(h.getLevel(),"extraction_chamber",null).stream().filter(r->r.value().matches(input,h.getLevel())).findFirst();
        h.assertTrue(expected.isPresent()&&expected.equals(RecipeIndex.firstMatch(h.getLevel(),"extraction_chamber",null,input)),"First match preserves sorted selection");
        h.assertTrue(RecipeIndex.firstMatch(h.getLevel(),"extraction_chamber",null,input,r->false).isEmpty(),"Eligibility remains live");
        var selection=new MachineRecipeSelection();
        var first=selection.find(h.getLevel(),"extraction_chamber",null,m.inventory,0,1);
        h.assertTrue(first.isPresent()&&first==selection.find(h.getLevel(),"extraction_chamber",null,m.inventory,0,1),"Unchanged lane reuses its selection");
        h.assertTrue(selection.find(h.getLevel(),"extraction_chamber",null,m.inventory,2,1).isEmpty(),"Another lane cannot reuse a match from slot zero");
        m.inventory.setStackInSlot(2,new ItemStack(Items.DIRT,2));
        var lane=selection.find(h.getLevel(),"extraction_chamber",null,m.inventory,2,1);
        h.assertTrue(lane.isPresent(),"Changing lane contents restores matching");
        RecipeIndex.clear();
        h.assertTrue(lane!=selection.find(h.getLevel(),"extraction_chamber",null,m.inventory,2,1),"Reload invalidates a lane cache");
        h.succeed();
    }
    @GameTest(template="empty") public static void researchSnapshotsDoNotAliasLiveMastery(GameTestHelper h){
        var id=UUID.randomUUID();var data=ResearchData.get(h.getLevel().getServer());
        var before=data.snapshot(id);
        ResearchData.completed(h.getLevel(),id,"calculator");
        var after=data.snapshot(id);
        h.assertTrue(before.mastery().getOrDefault("calculator",0L)==0,"Previous sent snapshot stays unchanged");
        h.assertTrue(after.mastery().getOrDefault("calculator",0L)==1&&!before.equals(after),"Mastery change produces a different packet");
        h.assertTrue(after.equals(data.snapshot(id)),"Unchanged state compares equal for packet suppression");
        var group="performance_"+UUID.randomUUID();
        try{data.unlock(id,group);var unlocked=data.snapshot(id);h.assertTrue(unlocked.groups().contains(group)&&!after.equals(unlocked),"Unlock changes packet contents");}
        finally{data.revoke(id,group);}
        h.succeed();
    }
}
