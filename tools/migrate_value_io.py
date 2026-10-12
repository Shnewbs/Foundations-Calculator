#!/usr/bin/env python3
"""Migrate the legacy platform source to native ValueIO without changing save keys."""
from pathlib import Path
import re
ROOT = Path(__file__).resolve().parents[1] / 'src/main/java/com/foundations/calculator'

def migrate():
    helper = ROOT / 'core/PlatformItems.java'
    helper.write_text('''package com.foundations.calculator.core;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.*;
import net.minecraft.world.item.ItemStack;
/** Registry-aware stacks and stack-sensitive crafting remainders. */
public final class PlatformItems {
    private PlatformItems() {}
    public static ItemStack remainder(ItemStack stack){var remainder=stack.getCraftingRemainder();return remainder==null?ItemStack.EMPTY:remainder.create();}
    public static Tag save(ItemStack stack,HolderLookup.Provider lookup){return ItemStack.CODEC.encodeStart(lookup.createSerializationContext(NbtOps.INSTANCE),stack).getOrThrow();}
    public static java.util.Optional<ItemStack> load(Tag tag,HolderLookup.Provider lookup){return tag==null?java.util.Optional.empty():ItemStack.CODEC.parse(lookup.createSerializationContext(NbtOps.INSTANCE),tag).result();}
}
''')
    for p in ROOT.rglob('*.java'):
        s = p.read_text()
        s = re.sub(r'([\w.]+)\.hasCraftingRemainingItem\(\)',r'!com.foundations.calculator.core.PlatformItems.remainder(\1).isEmpty()',s)
        s = re.sub(r'([\w.]+)\.getCraftingRemainingItem\(\)',r'com.foundations.calculator.core.PlatformItems.remainder(\1)',s)
        s = s.replace('.getFoodProperties(null)','.get(net.minecraft.core.component.DataComponents.FOOD)').replace('Ingredient.CODEC_NONEMPTY','Ingredient.CODEC').replace('.serverLevel()','.level()')
        s = s.replace('MobEffects.CONFUSION','MobEffects.NAUSEA').replace('MobEffects.MOVEMENT_SLOWDOWN','MobEffects.SLOWNESS').replace('MobEffects.DAMAGE_BOOST','MobEffects.STRENGTH')
        s = re.sub(r'\bMobEffects\.JUMP\b','MobEffects.JUMP_BOOST',s)
        s = s.replace('.getMaxBuildHeight()','.getMaxY()')
        if '.getLongArray("Fallow").orElse(' not in s:s=s.replace('.getLongArray("Fallow")','.getLongArray("Fallow").orElse(new long[0])')
        if p.name=='MachineBlockEntity.java':
            start=s.find('    protected void saveAdditional(CompoundTag')
            if start>=0:
                end=s.index('    public void setActive(',start)
                s=s[:start]+'''    @Override protected void saveAdditional(net.minecraft.world.level.storage.ValueOutput output){
        super.saveAdditional(output);output.store("Program",CompoundTag.CODEC,program.copy());
        if(bulkInventory())bulk.serialize(output.child("Bulk"));inventory.serialize(output.child("Inventory"));output.putLong("Energy",energy.stored());
        output.putInt("Progress",progress);output.putInt("BurnTime",burnTime);output.putInt("Nutrient",nutrient);output.putString("Recipe",activeRecipe);
        var list=output.list("Pending",ItemStack.CODEC);for(ItemStack stack:pending)if(!stack.isEmpty())list.add(stack);
    }
    @Override protected void loadAdditional(net.minecraft.world.level.storage.ValueInput input){
        super.loadAdditional(input);program=input.read("Program",CompoundTag.CODEC).orElseGet(CompoundTag::new).copy();
        chestOpeners.clear();program.remove("LidOpen");program.remove("Checked");program.remove("DynamicFormed");greenhouseGeometry=null;recipeSelection.clear();clientProgram.clear();
        inventory.deserialize(input.childOrEmpty("Inventory"));energy.load(input.getLongOr("Energy",0L));
        if(bulkInventory()){bulk.deserialize(input.childOrEmpty("Bulk"));if(input.child("Bulk").isEmpty())for(int i=0;i<SIZE;i++){ItemStack left=inventory.getStackInSlot(i);for(int n=0;n<bulk.getSlots()&&!left.isEmpty();n++)left=bulk.insertItem(n,left,false);inventory.setStackInSlot(i,left);}}
        progress=Math.max(0,input.getIntOr("Progress",0));burnTime=Math.max(0,input.getIntOr("BurnTime",0));nutrient=Math.max(0,input.getIntOr("Nutrient",0));activeRecipe=input.getStringOr("Recipe","");
        pending.clear();input.listOrEmpty("Pending",ItemStack.CODEC).forEach(pending::add);
    }
'''+s[end:]
            s=s.replace('inventory.getStackInSlot(0).save(lookup)','PlatformItems.save(inventory.getStackInSlot(0),lookup)').replace('ItemStack.parse(lookup,tag.get("Display"))','PlatformItems.load(tag.get("Display"),lookup)')
        if p.name=='BulkStorage.java':
            s=s.replace('samples[i].save(lookup)','PlatformItems.save(samples[i],lookup)').replace('ItemStack.parse(lookup,tag.get("Item"))','PlatformItems.load(tag.get("Item"),lookup)')
            if 'void serialize(net.minecraft.world.level.storage.ValueOutput' not in s:
                s=s.rstrip()[:-1]+'''    public void serialize(net.minecraft.world.level.storage.ValueOutput output){var entries=output.childrenList("Entries");for(int i=0;i<samples.length;i++)if(amounts[i]>0){var entry=entries.addChild();entry.putInt("Slot",i);entry.putInt("Amount",amounts[i]);entry.store("Item",ItemStack.CODEC,samples[i]);}}
    public void deserialize(net.minecraft.world.level.storage.ValueInput input){Arrays.fill(samples,ItemStack.EMPTY);Arrays.fill(amounts,0);for(var entry:input.childrenListOrEmpty("Entries")){int slot=entry.getIntOr("Slot",-1);if(slot>=0&&slot<samples.length){samples[slot]=entry.read("Item",ItemStack.CODEC).orElse(ItemStack.EMPTY).copyWithCount(1);amounts[slot]=samples[slot].isEmpty()?0:Math.max(0,entry.getIntOr("Amount",0));}}}
}
'''
        fuel={'MachineBlockEntity.java':'level','MachinePrograms.java':'m.getLevel()','GreenhouseProgram.java':'m.getLevel()','MachineDiagnostics.java':'level','CalculatorMenu.java':'playerInventory.player.level()'}.get(p.name)
        if fuel:s=s.replace('.getBurnTime(RecipeType.SMELTING)',f'.getBurnTime(RecipeType.SMELTING,{fuel}.fuelValues())').replace('.getBurnTime(net.minecraft.world.item.crafting.RecipeType.SMELTING)',f'.getBurnTime(net.minecraft.world.item.crafting.RecipeType.SMELTING,{fuel}.fuelValues())')
        if p.name in ['MachineBlockEntity.java','SmeltingModule.java','MachineDiagnostics.java']:s=s.replace('level.getRecipeManager()','level.getServer().getRecipeManager()')
        if s!=p.read_text():p.write_text(s)

if __name__=='__main__':migrate()
