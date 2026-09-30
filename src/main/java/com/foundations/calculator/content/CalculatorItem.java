package com.foundations.calculator.content;

import java.util.List;
import com.foundations.calculator.menu.CalculatorMenu;
import com.foundations.calculator.core.CircuitData;
import com.foundations.calculator.core.CalculatorConfig;
import com.foundations.calculator.core.NutritionData;
import com.foundations.calculator.api.LongEnergyStorage;
import com.foundations.calculator.menu.StorageModuleMenu;
import com.foundations.calculator.menu.PortableCraftingMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.energy.IEnergyStorage;

public class CalculatorItem extends Item {
    private final String kind;
    public final int capacity;
    public CalculatorItem(String kind,int capacity,Properties p){super(p);this.kind=kind;this.capacity=capacity;}
    public long configuredCapacityLong(){return capacity<=0?0:CalculatorConfig.itemCapacity(kind,capacity);}
    public int configuredCapacity(){return (int)Math.min(Integer.MAX_VALUE,configuredCapacityLong());}
    private long internalEnergyLong(ItemStack stack){
        Long modern=stack.get(Content.ENERGY_LONG.get());
        return Math.max(0L,modern!=null?modern:stack.getOrDefault(Content.ENERGY.get(),0));
    }
    private int internalEnergy(ItemStack stack){return (int)Math.min(Integer.MAX_VALUE,internalEnergyLong(stack));}
    private void setInternalEnergy(ItemStack stack,long value){
        // Do not truncate already-stored energy when a server lowers capacity. Receive
        // computes room against the current cap; extract drains excess normally.
        long n=Math.max(0L,value);
        stack.set(Content.ENERGY_LONG.get(),n);
        stack.set(Content.ENERGY.get(),(int)Math.min(Integer.MAX_VALUE,n));
    }
    private net.minecraft.core.NonNullList<ItemStack> batteries(ItemStack stack){var list=net.minecraft.core.NonNullList.withSize(16,ItemStack.EMPTY);if(kind.equals("flawless_calculator"))stack.getOrDefault(Content.MODULES.get(),net.minecraft.world.item.component.ItemContainerContents.EMPTY).copyInto(list);return list;}
    public long energyLong(ItemStack stack){long total=internalEnergyLong(stack);for(ItemStack module:batteries(stack))if(Content.path(module).equals("energy_module")&&module.getItem() instanceof CalculatorItem item){long add=item.internalEnergyLong(module);total=add>Long.MAX_VALUE-total?Long.MAX_VALUE:total+add;}return total;}
    public long maxEnergyLong(ItemStack stack){long total=configuredCapacityLong();for(ItemStack module:batteries(stack))if(Content.path(module).equals("energy_module")&&module.getItem() instanceof CalculatorItem item){long add=item.configuredCapacityLong();total=add>Long.MAX_VALUE-total?Long.MAX_VALUE:total+add;}return total;}
    public int energy(ItemStack stack){return (int)Math.min(Integer.MAX_VALUE,energyLong(stack));}
    public int maxEnergy(ItemStack stack){return (int)Math.min(Integer.MAX_VALUE,maxEnergyLong(stack));}
    public LongEnergyStorage longStorage(ItemStack stack){return new LongEnergyStorage(){
        public long receive(long amount,boolean simulate){return transferLong(amount,simulate,true);}
        public long extract(long amount,boolean simulate){return transferLong(amount,simulate,false);}
        private long transferLong(long amount,boolean simulate,boolean receive){
            long requested=Math.max(0L,amount),remaining=requested,base=internalEnergyLong(stack);
            long moved=Math.min(remaining,receive?Math.max(0L,configuredCapacityLong()-base):base);remaining-=moved;
            if(!simulate&&moved>0)setInternalEnergy(stack,receive?base+moved:base-moved);
            var modules=batteries(stack);boolean changed=false;
            for(ItemStack module:modules)if(remaining>0&&Content.path(module).equals("energy_module")&&module.getItem() instanceof CalculatorItem item){
                long movedModule=receive?item.longStorage(module).receive(remaining,simulate):item.longStorage(module).extract(remaining,simulate);remaining-=movedModule;changed|=movedModule>0;
            }
            if(!simulate&&changed)stack.set(Content.MODULES.get(),net.minecraft.world.item.component.ItemContainerContents.fromItems(modules));return requested-remaining;
        }
        public long stored(){return energyLong(stack);}public long capacity(){return maxEnergyLong(stack);}
        public boolean canReceive(){return capacity>0;}public boolean canExtract(){return capacity>0;}
    };}
    public IEnergyStorage storage(ItemStack stack){return new IEnergyStorage(){
        private LongEnergyStorage power(){return longStorage(stack);}
        public int receiveEnergy(int n,boolean simulate){return (int)Math.min(Integer.MAX_VALUE,power().receive(Math.max(0,n),simulate));}
        public int extractEnergy(int n,boolean simulate){return (int)Math.min(Integer.MAX_VALUE,power().extract(Math.max(0,n),simulate));}
        public int getEnergyStored(){return (int)Math.min(Integer.MAX_VALUE,power().stored());}
        public int getMaxEnergyStored(){return (int)Math.min(Integer.MAX_VALUE,power().capacity());}
        public boolean canReceive(){return power().canReceive();}public boolean canExtract(){return power().canExtract();}
    };}
    public InteractionResultHolder<ItemStack> use(Level level,Player player,InteractionHand hand){
        ItemStack carrier=player.getItemInHand(hand),stack=carrier;String mode=kind;
        if(!CalculatorConfig.enabled(kind))return InteractionResultHolder.fail(carrier);
        if(kind.equals("flawless_calculator")){
            if(player.isShiftKeyDown()){
                if(!level.isClientSide){
                    CompoundTag tag=CircuitData.tag(carrier);int selected=tag.getInt("SelectedModule");
                    for(int attempt=0;attempt<17;attempt++){selected=(selected+1)%17;tag.putInt("SelectedModule",selected);UtilityItems.put(carrier,tag);if(selected==0||!ModuleWorkstation.selected(carrier).isEmpty())break;}
                    ItemStack module=ModuleWorkstation.selected(carrier);player.displayClientMessage(Component.literal("Module: ").append(module.isEmpty()?Component.literal("Flawless calculation"):module.getHoverName()),true);
                }return InteractionResultHolder.sidedSuccess(carrier,level.isClientSide);
            }
            ItemStack selected=ModuleWorkstation.selected(carrier);if(!selected.isEmpty()){stack=selected;mode=switch(Content.path(selected)){case "atomic_assembly"->"dynamic_module";case "flawless_assembly"->"flawless_calculator";default->Content.path(selected);};}
        }
        if(!CalculatorConfig.enabled(mode))return InteractionResultHolder.fail(carrier);
        if(level.isClientSide){if(mode.equals("info_calculator"))com.foundations.calculator.client.GuideScreen.open();return InteractionResultHolder.success(carrier);}
        if(mode.equals("info_calculator"))return InteractionResultHolder.consume(carrier);
        if(List.of("calculator","scientific_calculator","atomic_calculator","flawless_calculator","dynamic_module").contains(mode)){
            String selectedMode=mode;
            ((ServerPlayer)player).openMenu(new SimpleMenuProvider((id,inv,p)->CalculatorMenu.forModuleItem(id,inv,hand,selectedMode),stack.getHoverName()),b->{b.writeBoolean(false);b.writeEnum(hand);b.writeUtf(selectedMode);});
            return InteractionResultHolder.consume(carrier);
        }
        if(mode.equals("crafting_calculator")){
            int cost=CalculatorConfig.integer("module.crafting_calculator.cost",1);
            if(storage(carrier).extractEnergy(cost,true)<cost){player.displayClientMessage(Component.literal("Charge the calculator first."),true);return InteractionResultHolder.fail(carrier);}
            storage(carrier).extractEnergy(cost,false);player.openMenu(new SimpleMenuProvider((id,inv,p)->new PortableCraftingMenu(id,inv,hand),stack.getHoverName()));return InteractionResultHolder.consume(carrier);
        }
        if(mode.equals("smelting_module")){
            ((ServerPlayer)player).openMenu(new SimpleMenuProvider((id,inv,p)->new com.foundations.calculator.menu.SmeltingModuleMenu(id,inv,hand),stack.getHoverName()),b->b.writeEnum(hand));return InteractionResultHolder.consume(carrier);
        }
        if(mode.equals("storage_module")){
            ItemStack module=stack;Runnable save=module==carrier?()->{}:()->ModuleWorkstation.saveSelected(carrier,module);
            player.openMenu(new SimpleMenuProvider((id,inv,p)->new StorageModuleMenu(id,inv,hand,module,save),stack.getHoverName()));return InteractionResultHolder.consume(carrier);
        }
        if(UtilityItems.use(mode,stack,carrier,level,player)){if(stack!=carrier)ModuleWorkstation.saveSelected(carrier,stack);return InteractionResultHolder.consume(carrier);}
        return InteractionResultHolder.pass(carrier);
    }
    public InteractionResult useOn(UseOnContext c){
        ItemStack carrier=c.getItemInHand(),stack=carrier;String mode=kind;
        if(kind.equals("flawless_calculator")){ItemStack selected=ModuleWorkstation.selected(carrier);if(!selected.isEmpty()){stack=selected;mode=switch(Content.path(selected)){case "atomic_assembly"->"dynamic_module";case "flawless_assembly"->"flawless_calculator";default->Content.path(selected);};}}
        InteractionResult result=UtilityItems.onBlock(mode,stack,carrier,c);
        if(stack!=carrier&&!c.getLevel().isClientSide)ModuleWorkstation.saveSelected(carrier,stack);
        if(result!=InteractionResult.PASS)return result;
        Player player=c.getPlayer();var state=c.getLevel().getBlockState(c.getClickedPos());
        if(kind.equals("wrench"))return WrenchActions.use(c);
        return super.useOn(c);
    }
    public boolean canFitInsideContainerItems(){return !kind.equals("storage_module")&&!kind.equals("smelting_module")&&!kind.equals("flawless_calculator");}
    public void inventoryTick(ItemStack stack,Level level,net.minecraft.world.entity.Entity entity,int slot,boolean selected){
        if(!level.isClientSide&&CalculatorConfig.flag("module.smelting_module.backgroundProcessing",true)){
            boolean open=entity instanceof Player p&&p.containerMenu instanceof com.foundations.calculator.menu.SmeltingModuleMenu menu&&menu.carrier==stack;
            if(kind.equals("smelting_module")&&!open)SmeltingModule.tick(stack,stack,level);
            if(kind.equals("flawless_calculator")&&!open){
                var installed=net.minecraft.core.NonNullList.withSize(16,ItemStack.EMPTY);stack.getOrDefault(Content.MODULES.get(),net.minecraft.world.item.component.ItemContainerContents.EMPTY).copyInto(installed);
                for(int i=0;i<CalculatorConfig.integer("module.flawless_calculator.maxModules",16);i++)if(Content.path(installed.get(i)).equals("smelting_module")){
                    ItemStack smelter=installed.get(i);SmeltingModule.tick(smelter,stack,level);
                    var latest=net.minecraft.core.NonNullList.withSize(16,ItemStack.EMPTY);stack.getOrDefault(Content.MODULES.get(),net.minecraft.world.item.component.ItemContainerContents.EMPTY).copyInto(latest);latest.set(i,smelter);stack.set(Content.MODULES.get(),net.minecraft.world.item.component.ItemContainerContents.fromItems(latest));
                }
            }
        }
        if(kind.equals("flawless_calculator")&&!level.isClientSide&&level.getGameTime()%CalculatorConfig.integer("nutrition.restoreInterval",10)==0&&entity instanceof Player player){
            var modules=net.minecraft.core.NonNullList.withSize(16,ItemStack.EMPTY);stack.getOrDefault(Content.MODULES.get(),net.minecraft.world.item.component.ItemContainerContents.EMPTY).copyInto(modules);
            for(ItemStack module:modules){
                if(Content.path(module).equals("nutrition_module"))NutritionData.restore(player,module,CalculatorConfig.integer("nutrition.automaticRestoreLimit",2));
                
            }stack.set(Content.MODULES.get(),net.minecraft.world.item.component.ItemContainerContents.fromItems(modules));
        }
        if(kind.equals("nutrition_module")&&!level.isClientSide&&level.getGameTime()%CalculatorConfig.integer("nutrition.restoreInterval",10)==0&&entity instanceof Player player)NutritionData.restore(player,stack,CalculatorConfig.integer("nutrition.automaticRestoreLimit",2));
    }
    public boolean isBarVisible(ItemStack stack){return capacity>0;}
    public int getBarWidth(ItemStack stack){long cap=maxEnergyLong(stack);return cap<=0?0:(int)Math.min(13,13.0*energyLong(stack)/Math.max(1L,cap));}
    public int getBarColor(ItemStack stack){return 0x48cbdc;}
    public void appendHoverText(ItemStack stack,TooltipContext context,List<Component> tooltip,TooltipFlag flag){
        super.appendHoverText(stack,context,tooltip,flag);
        if(com.foundations.calculator.core.ClientConfig.flag(com.foundations.calculator.core.ClientConfig.ENERGY_TOOLTIPS)&&capacity>0)tooltip.add(Component.literal(com.foundations.calculator.core.EnergyDisplay.formatFE(energyLong(stack),maxEnergyLong(stack))));
        for(String type:List.of("health","hunger"))if(NutritionData.capacity(stack,type)>0)tooltip.add(Component.literal(type+": "+NutritionData.get(stack,type)));
        if(kind.equals("flawless_calculator")){ItemStack module=ModuleWorkstation.selected(stack);tooltip.add(Component.literal("Module: ").append(module.isEmpty()?Component.literal("Flawless calculation"):module.getHoverName()));tooltip.add(Component.literal("Sneak-use to select an installed module."));}
        if(kind.equals("locator_module")&&CircuitData.tag(stack).hasUUID("Owner"))tooltip.add(Component.literal("Owner: "+CircuitData.tag(stack).getString("OwnerName")));
        if(com.foundations.calculator.core.ClientConfig.flag(com.foundations.calculator.core.ClientConfig.INSTRUCTIONS)&&kind.contains("terrain_module"))tooltip.add(Component.literal("Sneak-use a block to select material; use to transform ("+CalculatorConfig.integer("module."+kind+".cost",1)+" FE)."));
        if(com.foundations.calculator.core.ClientConfig.flag(com.foundations.calculator.core.ClientConfig.INSTRUCTIONS)&&kind.equals("warp_module"))tooltip.add(Component.literal("Sneak-use stable stone to bind; use to warp ("+CalculatorConfig.integer("module.warp_module.cost",1000)+" FE)."));
        if(kind.startsWith("circuit_board_"))tooltip.add(Component.literal(CircuitData.analysed(stack)?(CircuitData.stable(stack)?"Stable · analysed":"Analysed"):"Not analysed"));
        if(kind.equals("speed_upgrade"))tooltip.add(Component.literal(String.format("Machine processing: +%.0f%% speed factor per upgrade",CalculatorConfig.decimal("upgrades.speedBonus",.25)*100)).withStyle(net.minecraft.ChatFormatting.AQUA));
        if(kind.equals("energy_upgrade"))tooltip.add(Component.literal(String.format("Machine processing: +%.0f%% efficiency factor per upgrade",CalculatorConfig.decimal("upgrades.energyDiscount",.1)*100)).withStyle(net.minecraft.ChatFormatting.AQUA));
        if(kind.equals("transfer_upgrade"))tooltip.add(Component.literal("Exports up to "+CalculatorConfig.integer("upgrades.itemsPerTransfer",4)+" items/tick per upgrade (max "+CalculatorConfig.integer("upgrades.maxTransfer",64)+")").withStyle(net.minecraft.ChatFormatting.AQUA));
        if(kind.equals("void_upgrade"))tooltip.add(Component.literal(CalculatorConfig.flag("upgrades.allowVoid",true)?"Discards output that remains blocked after normal insertion.":"Disabled by server configuration.").withStyle(net.minecraft.ChatFormatting.AQUA));
        if(Content.PENDING.contains(kind))tooltip.add(Component.literal("Port pending — not functional in this alpha").withStyle(net.minecraft.ChatFormatting.RED));
    }
    public boolean isFoil(ItemStack stack){return kind.startsWith("circuit_board_")&&CircuitData.stable(stack)||super.isFoil(stack);}
}
