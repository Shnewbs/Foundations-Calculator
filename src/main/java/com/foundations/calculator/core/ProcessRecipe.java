package com.foundations.calculator.core;

import java.util.*;
import com.foundations.calculator.content.Content;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public record ProcessRecipe(String machine, List<CountedIngredient> inputs, List<Result> outputs, int energy, int ticks, int value, boolean research,String researchGroup) implements Recipe<ProcessInput> {
    public ProcessRecipe(String machine,List<CountedIngredient> inputs,List<Result> outputs,int energy,int ticks,int value,boolean research){this(machine,inputs,outputs,energy,ticks,value,research,"");}
    public record Result(ItemStack stack, double chance, String randomCircuit) {
        public static final Codec<Result> CODEC=RecordCodecBuilder.create(i->i.group(
            ItemStack.CODEC.fieldOf("stack").forGetter(Result::stack),
            Codec.doubleRange(0,1).optionalFieldOf("chance",1.0).forGetter(Result::chance),
            Codec.STRING.validate(s->Set.of("","circuit_board","circuit_dirty","circuit_damaged").contains(s)?com.mojang.serialization.DataResult.success(s):com.mojang.serialization.DataResult.error(()->"Unknown random circuit family: "+s)).optionalFieldOf("random_circuit", "").forGetter(Result::randomCircuit)
        ).apply(i,Result::new));
        public ItemStack roll(RandomSource random) {
            if(random.nextDouble()>=chance)return ItemStack.EMPTY;
            ItemStack s=randomCircuit.isEmpty()?stack.copy():new ItemStack(Content.item(randomCircuit+"_"+random.nextInt(14)),stack.getCount());
            if(Content.path(s).startsWith("circuit_board_"))CircuitData.initialize(s,random);
            return s;
        }
    }
    public static final MapCodec<ProcessRecipe> CODEC=RecordCodecBuilder.mapCodec(i->i.group(
        Codec.STRING.fieldOf("machine").forGetter(ProcessRecipe::machine),
        CountedIngredient.CODEC.listOf(1,14).fieldOf("ingredients").forGetter(ProcessRecipe::inputs),
        Result.CODEC.listOf(0,6).fieldOf("results").forGetter(ProcessRecipe::outputs),
        Codec.intRange(0,Integer.MAX_VALUE).optionalFieldOf("energy",0).forGetter(ProcessRecipe::energy),
        Codec.intRange(1,1000000).optionalFieldOf("ticks",200).forGetter(ProcessRecipe::ticks),
        Codec.intRange(0,Integer.MAX_VALUE).optionalFieldOf("value",0).forGetter(ProcessRecipe::value),
        Codec.BOOL.optionalFieldOf("research",false).forGetter(ProcessRecipe::research),
        Codec.string(0,256).optionalFieldOf("research_group","").forGetter(ProcessRecipe::researchGroup)
    ).apply(i,ProcessRecipe::new));
    public int[] allocation(ProcessInput input) {
        int[] available=input.stacks().stream().mapToInt(ItemStack::getCount).toArray();
        int[] required=inputs.stream().mapToInt(CountedIngredient::count).toArray();
        return IngredientAllocation.allocate(available,required,(i,s)->inputs.get(i).test(input.getItem(s)));
    }
    public boolean matches(ProcessInput input,Level level){return input.stacks().stream().allMatch(RecipePolicies::itemEnabled)&&allocation(input)!=null;}
    public ItemStack assemble(ProcessInput input,HolderLookup.Provider lookup){return getResultItem(lookup).copy();}
    public boolean canCraftInDimensions(int width,int height){return width*height>=inputs.size();}
    public ItemStack getResultItem(HolderLookup.Provider lookup){return outputs.isEmpty()?ItemStack.EMPTY:outputs.getFirst().stack();}
    public RecipeSerializer<?> getSerializer(){return Content.PROCESS_SERIALIZER.get();}
    public RecipeType<?> getType(){return Content.PROCESS_TYPE.get();}
    public boolean isSpecial(){return true;}
    public static final class Serializer implements RecipeSerializer<ProcessRecipe>{
        private static final StreamCodec<RegistryFriendlyByteBuf,ProcessRecipe> STREAM=ByteBufCodecs.fromCodecWithRegistries(CODEC.codec());
        public MapCodec<ProcessRecipe> codec(){return CODEC;}
        public StreamCodec<RegistryFriendlyByteBuf,ProcessRecipe> streamCodec(){return STREAM;}
    }
}
