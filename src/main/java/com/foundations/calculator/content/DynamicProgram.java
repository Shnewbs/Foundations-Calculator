package com.foundations.calculator.content;
import java.util.*;
import com.foundations.calculator.core.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
/** Three independent calculator recipe lanes within the original 7x7x7 housing. */
public final class DynamicProgram {
    private DynamicProgram() {}
    private static boolean stable(BlockState s){var id=BuiltInRegistries.BLOCK.getKey(s.getBlock());return id.getNamespace().equals("foundations_calculator")&&id.getPath().startsWith("stable_stone");}
    private static boolean glass(BlockState s){return s.is(Content.block("flawless_glass"));}
    public static boolean formed(MachineBlockEntity m){
        if(!CalculatorConfig.flag("world.dynamicRequiresStructure",true))return true;
        int radius=CalculatorConfig.integer("world.dynamicStructureRadius",3);BlockPos center=m.getBlockPos().relative(m.getBlockState().getValue(MachineBlock.FACING).getOpposite(),radius);
        for(int y=-radius;y<=radius;y++)for(int x=-radius;x<=radius;x++)for(int z=-radius;z<=radius;z++){
            BlockPos p=center.offset(x,y,z);if(p.equals(m.getBlockPos()))continue;if(!m.getLevel().hasChunkAt(p))return false;BlockState state=m.getLevel().getBlockState(p);
            boolean edge=Math.abs(x)==radius||Math.abs(z)==radius;
            if(Math.abs(y)==radius){if(edge?!stable(state):!glass(state))return false;}
            else if(edge){if(Math.abs(x)==radius&&Math.abs(z)==radius?!stable(state):!glass(state))return false;}
            else if(!state.isAir())return false;
        }return true;
    }
    public static void tick(MachineBlockEntity m){
        if(m.workDue(CalculatorConfig.integer("world.dynamicCheckInterval",20))||!m.program.contains("DynamicFormed")){m.program.putBoolean("DynamicFormed",formed(m));m.setActive(m.program.getBooleanOr("DynamicFormed",false));}
        if(!m.program.getBooleanOr("DynamicFormed",false)||m.getLevel().getGameTime()%CalculatorConfig.integer("world.dynamicInterval",1)!=0)return;
        String[] types={"calculator","scientific","atomic"};int[] starts={0,2,4},sizes={2,2,3};
        for(int lane=0;lane<3;lane++){
            List<ItemStack> inputs=new ArrayList<>();for(int i=0;i<sizes[lane];i++)inputs.add(m.inventory.getStackInSlot(starts[lane]+i));var input=new ProcessInput(inputs);String type=types[lane];
            var found=RecipeIndex.forMachine(m.getLevel(),type,m.owner()).stream().filter(r->r.value().matches(input,m.getLevel())).findFirst();if(found.isEmpty())continue;
            var recipe=found.get().value();int cost=CalculatorConfig.energy(m.kind(),RecipePolicies.energy(found.get(),1));if(m.energy.getEnergyStored()<cost)continue;
            ItemStackHandler temporary=new ItemStackHandler(25);for(int i=0;i<sizes[lane];i++)temporary.setStackInSlot(i,inputs.get(i).copy());
            var results=ProcessTransactions.consume(temporary,recipe.allocation(input),recipe,m.getLevel());
            for(int i=0;i<sizes[lane];i++)m.inventory.setStackInSlot(starts[lane]+i,temporary.getStackInSlot(i));
            m.energy.extractEnergy(cost,false);m.pending.addAll(results);ResearchData.completed(m.getLevel(),m.owner(),type);m.setChanged();break;
        }
    }
}
