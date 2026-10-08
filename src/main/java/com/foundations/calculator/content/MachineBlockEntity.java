package com.foundations.calculator.content;

import java.util.*;
import com.foundations.calculator.core.*;
import com.foundations.calculator.menu.CalculatorMenu;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.Component;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.*;
import net.neoforged.neoforge.energy.IEnergyStorage;

public class MachineBlockEntity extends BlockEntity implements MenuProvider {
    public static final int SIZE=25;
    public final StoredEnergy energy;
    public final BulkStorage bulk;
    public final ItemStackHandler inventory=new ItemStackHandler(SIZE){
        protected void onContentsChanged(int slot){setChanged();if(kind().equals("module_workstation"))ModuleWorkstation.changed(MachineBlockEntity.this,slot);}
        public boolean isItemValid(int slot,ItemStack stack){
            if(kind().equals("module_workstation"))return slot==20?Content.path(stack).equals("flawless_calculator"):slot<CalculatorConfig.integer("module.flawless_calculator.maxModules",16)&&ModuleWorkstation.isModule(stack);
            if(kind().equals("calculator_plug")&&slot==0)return stack.is(Content.item("soil"))||Content.path(stack).startsWith("circuit_board_");
            if(kind().equals("calculator_locator")&&slot==0)return Content.path(stack).equals("locator_module");
            if(kind().equals("atomic_multiplier")&&slot>0&&slot<8)return Content.path(stack).startsWith("circuit_board_");
            if(MachineProfiles.storage(kind())&&slot==0)return EnergyItems.canDischarge(stack);
            if(kind().endsWith("greenhouse")&&slot<14)return GreenhouseProgram.acceptsInput(MachineBlockEntity.this,slot,stack);
            if(slot>=21)return Content.path(stack).endsWith("_upgrade");
            if(slot==20)return chargesItems()?EnergyItems.canCharge(stack):EnergyItems.canDischarge(stack)||Content.path(stack).contains("calculator")||NutritionData.capacity(stack,"health")>0||NutritionData.capacity(stack,"hunger")>0;
            return true;
        }
        public int getSlotLimit(int slot){return kind().equals("module_workstation")||kind().equals("calculator_plug")||kind().equals("magnetic_flux")?1:slot>=21?CalculatorConfig.integer("upgrades.maxPerType",16):64;}
    };
    public final List<ItemStack> pending=new ArrayList<>();
    public int progress,totalTicks=200,burnTime,nutrient;
    public CompoundTag program=new CompoundTag();
    public boolean updatingModules,suppressDrops;
    public ItemStack installedCalculator=ItemStack.EMPTY;
    private String activeRecipe="";
    public float lid,previousLid;
    public long lastCrankTick=Long.MIN_VALUE;
    GreenhouseGeometry greenhouseGeometry;
    private final MachineRecipeSelection recipeSelection=new MachineRecipeSelection();
    private final Set<UUID> chestOpeners=new HashSet<>();
    private final ChangeStamp<CompoundTag> clientProgram=new ChangeStamp<>();
    private ItemStack clientDisplay=ItemStack.EMPTY;
    private int sentProgress,sentTotalTicks;
    private long sentEnergy,sentCrank;
    public boolean workDue(int interval) {
        return level!=null&&StaggeredWork.due(level.getGameTime(),worldPosition.asLong(),Math.max(1,interval),
            CalculatorConfig.flag("performance.staggerWork",true));
    }
    public boolean itemInput(Direction side) {
        return AutomationRules.permits(CalculatorConfig.machineFlag(kind(),"itemAutomation"),
            !kind().equals("module_workstation")&&!kind().equals("magnetic_flux"),
            side==null?0:program.getInt("Side"+side.get3DDataValue()),false);
    }
    public boolean itemOutput(Direction side) {
        return AutomationRules.permits(CalculatorConfig.machineFlag(kind(),"itemAutomation"),
            !kind().equals("module_workstation")&&!kind().equals("magnetic_flux"),
            side==null?0:program.getInt("Side"+side.get3DDataValue()),true);
    }
    /** Returns whether a packet was requested, useful to native regression tests. */
    public boolean syncClientState() {
        if(level==null||level.isClientSide||isRemoved())return false;
        ItemStack display=inventory.getStackInSlot(0);
        boolean changed=clientProgram.differs(program)||sentProgress!=progress||sentTotalTicks!=totalTicks
            ||sentEnergy!=energy.stored()||sentCrank!=lastCrankTick||!ItemStack.matches(clientDisplay,display);
        if(!changed&&CalculatorConfig.flag("performance.changeOnlySync",true))return false;
        clientProgram.capture(program::copy);clientDisplay=display.copy();sentProgress=progress;
        sentTotalTicks=totalTicks;sentEnergy=energy.stored();sentCrank=lastCrankTick;
        level.sendBlockUpdated(worldPosition,getBlockState(),getBlockState(),2);return true;
    }
    public void startOpen(Player player) {
        if(level==null||level.isClientSide||!kind().equals("reinforced_chest")||player.isSpectator())return;
        if(chestOpeners.add(player.getUUID()))updateLid();
    }
    public void stopOpen(Player player) {
        if(chestOpeners.remove(player.getUUID()))updateLid();
    }
    private void updateLid() {
        if(level==null||level.isClientSide||isRemoved())return;
        boolean open=!chestOpeners.isEmpty();if(open==program.getBoolean("LidOpen"))return;
        program.putBoolean("LidOpen",open);setChanged();syncClientState();
        if(CalculatorConfig.flag("world.chestSounds",true))level.playSound(null,worldPosition,
            open?net.minecraft.sounds.SoundEvents.CHEST_OPEN:net.minecraft.sounds.SoundEvents.CHEST_CLOSE,
            net.minecraft.sounds.SoundSource.BLOCKS,.5f,1);
    }
    private void recheckOpeners() {
        if(chestOpeners.isEmpty()||!workDue(CalculatorConfig.integer("performance.chestRecheckInterval",20)))return;
        chestOpeners.removeIf(id->{var player=level.getServer().getPlayerList().getPlayer(id);
            return player==null||!player.isAlive()||player.isSpectator()||player.level()!=level
                ||!(player.containerMenu instanceof com.foundations.calculator.menu.BulkStorageMenu menu)
                ||menu.machine!=this||!menu.stillValid(player);});
        updateLid();
    }
    @Override public void setRemoved() {
        chestOpeners.clear();greenhouseGeometry=null;recipeSelection.clear();clientProgram.clear();clientDisplay=ItemStack.EMPTY;
        super.setRemoved();
    }
    public MachineBlockEntity(BlockPos p,BlockState state){
        super(Content.MACHINE_ENTITY.get(),p,state);bulk=new BulkStorage(kind().equals("storage_chamber"),()->CalculatorConfig.integer(kind().equals("storage_chamber")?"storage.circuitChamberPerBin":kind().equals("algorithm_assimilator")?"storage.assimilatorPerBin":"storage.reinforcedChestPerBin",kind().equals("storage_chamber")?1024:kind().equals("algorithm_assimilator")?64:256),this::setChanged);
        energy=new StoredEnergy(()->CalculatorConfig.machineCapacity(kind(),MachineDefinition.forMachine(kind()).capacity()),this::setChanged);
    }
    public int redstoneMode(){return program.contains("RedstoneMode")?program.getInt("RedstoneMode"):CalculatorConfig.machine(kind(),"defaultRedstoneMode",0);}
    public java.util.UUID owner(){return program.hasUUID("Owner")?program.getUUID("Owner"):null;}
    public String kind(){return net.minecraft.core.registries.BuiltInRegistries.BLOCK.getKey(getBlockState().getBlock()).getPath();}
    public int inputCount(){return MachineDefinition.forMachine(kind()).inputCount();}
    public boolean bulkInventory(){return storage()||kind().equals("algorithm_assimilator");}
    private void flushResults(){if(!bulkInventory()){ProcessTransactions.flush(pending,inventory);return;}for(var iterator=pending.listIterator();iterator.hasNext();){ItemStack stack=iterator.next();for(int i=0;i<bulk.getSlots()&&!stack.isEmpty();i++)stack=bulk.insertItem(i,stack,false);if(stack.isEmpty())iterator.remove();else iterator.set(stack);}}
    public boolean storage(){return kind().equals("reinforced_chest")||kind().equals("storage_chamber");}
    public boolean hasOutputs(){return MachineDefinition.forMachine(kind()).outputs();}
    public boolean hasBatterySlot(){return MachineDefinition.forMachine(kind()).batterySlot();}
    public boolean supportsUpgrades(){return MachineDefinition.forMachine(kind()).upgrades();}
    public boolean usesEnergy(){return MachineDefinition.forMachine(kind()).usesEnergy();}
    public boolean chargesItems(){return MachineProfiles.storage(kind())||MachineProfiles.generator(kind());}
    /** Effective per-cycle FE after installed Energy Upgrades. */
    public int upgradeEnergyCost(int base){
        int amount=supportsUpgrades()?upgrades("energy_upgrade"):0;
        return (int)Math.max(0,Math.max(0,base)/(1.0+amount*CalculatorConfig.decimal("upgrades.energyDiscount",.1)));
    }
    /** Effective processing duration after installed Speed Upgrades. */
    public int upgradeProcessTicks(int base){
        int amount=supportsUpgrades()?upgrades("speed_upgrade"):0;
        return Math.max(1,(int)(Math.max(1,base)/(1.0+amount*CalculatorConfig.decimal("upgrades.speedBonus",.25))));
    }
    public boolean energySideInput(Direction side){if(!CalculatorConfig.machineFlag(kind(),"energyInput"))return false;int mode=side==null?0:program.getInt("Side"+side.get3DDataValue());return mode==1||mode==0&&!MachineProfiles.generator(kind());}
    public boolean energySideOutput(Direction side){if(!CalculatorConfig.machineFlag(kind(),"energyOutput"))return false;int mode=side==null?0:program.getInt("Side"+side.get3DDataValue());return mode==2||mode==0&&(MachineProfiles.generator(kind())||MachineProfiles.storage(kind()));}
    public boolean sideInput(Direction side){int mode=side==null?0:program.getInt("Side"+side.get3DDataValue());return mode==0||mode==1;}
    public boolean sideOutput(Direction side){int mode=side==null?0:program.getInt("Side"+side.get3DDataValue());return mode==0||mode==2;}
    public com.foundations.calculator.api.LongEnergyStorage longEnergyPort(Direction side){if(!usesEnergy())return null;return new com.foundations.calculator.api.LongEnergyStorage(){
        public long receive(long n,boolean simulate){return energySideInput(side)?energy.receive(Math.min(Math.max(0L,n),MachineProfiles.transferLong(kind())),simulate):0;}
        public long extract(long n,boolean simulate){return energySideOutput(side)?energy.extract(Math.min(Math.max(0L,n),MachineProfiles.transferLong(kind())),simulate):0;}
        public long stored(){return energy.stored();}public long capacity(){return energy.capacity();}
        public boolean canReceive(){return energySideInput(side);}public boolean canExtract(){return energySideOutput(side);}
    };}
    public IEnergyStorage energyPort(Direction side){var power=longEnergyPort(side);if(power==null)return null;return new IEnergyStorage(){
        public int receiveEnergy(int n,boolean simulate){return (int)Math.min(Integer.MAX_VALUE,power.receive(Math.max(0,n),simulate));}
        public int extractEnergy(int n,boolean simulate){return (int)Math.min(Integer.MAX_VALUE,power.extract(Math.max(0,n),simulate));}
        public int getEnergyStored(){return (int)Math.min(Integer.MAX_VALUE,power.stored());}public int getMaxEnergyStored(){return (int)Math.min(Integer.MAX_VALUE,power.capacity());}
        public boolean canReceive(){return power.canReceive();}public boolean canExtract(){return power.canExtract();}
    };}
    public IItemHandler automation(Direction face){
        IItemHandler base=bulkInventory()?bulk:inventory;
        return new IItemHandler(){
            public int getSlots(){return base.getSlots();}
            public ItemStack getStackInSlot(int slot){return base.getStackInSlot(slot);}
            private boolean extra(int slot,boolean output){
                int mode=face==null?0:program.getInt("Side"+face.get3DDataValue());
                return mode==(output?2:1)&&(slot==20&&hasBatterySlot()&&CalculatorConfig.flag("automation.allowBatterySlotAccess",true)||slot>=21&&supportsUpgrades()&&CalculatorConfig.flag("automation.allowUpgradeSlotAccess",false));
            }
            public ItemStack insertItem(int slot,ItemStack stack,boolean simulate){
                boolean normal=slot<inputCount()&&(face!=Direction.DOWN||program.getInt("Side"+Direction.DOWN.get3DDataValue())==1);
                return itemInput(face)&&(bulkInventory()||normal||extra(slot,false))?base.insertItem(slot,stack,simulate):stack;
            }
            public ItemStack extractItem(int slot,int count,boolean simulate){
                return itemOutput(face)&&(bulkInventory()||slot>=14&&slot<20||extra(slot,true))?base.extractItem(slot,count,simulate):ItemStack.EMPTY;
            }
            public int getSlotLimit(int slot){return base.getSlotLimit(slot);}
            public boolean isItemValid(int slot,ItemStack stack){return itemInput(face)&&base.isItemValid(slot,stack)&&(bulkInventory()||
                    slot<inputCount()&&(face!=Direction.DOWN||program.getInt("Side"+Direction.DOWN.get3DDataValue())==1)||extra(slot,false));}
        };
    }
    public static void tick(Level level,BlockPos pos,BlockState state,MachineBlockEntity m){
        m.serverTick();
        // Early returns for idle, disabled, redstone-paused or blocked-output machines must not suppress UI updates.
        if(m.workDue(CalculatorConfig.integer("performance.clientSyncInterval",10)))m.syncClientState();
    }
    public static void clientTick(Level level,BlockPos pos,BlockState state,MachineBlockEntity m){m.previousLid=m.lid;float speed=(float)ClientConfig.chestSpeed();m.lid=Math.clamp(m.lid+(m.program.getBoolean("LidOpen")?speed:-speed),0,1);}
    private void serverTick(){
        if(level==null||level.isClientSide)return;
        if(kind().equals("reinforced_chest"))recheckOpeners();
        if(storage()||!CalculatorConfig.machineEnabled(kind()))return;
        int redstone=redstoneMode();
        boolean powered=level.hasNeighborSignal(worldPosition);
        if(redstone==3||redstone==1&&!powered||redstone==2&&powered)return;
        MachinePrograms.export(this);
        if(!pending.isEmpty()){
            flushResults();setChanged();
            if(!pending.isEmpty()){if(CalculatorConfig.flag("upgrades.allowVoid",true)&&upgrades("void_upgrade")>0)pending.clear();else return;}
        }
        String k=kind();
        if(MachineProfiles.storage(k))EnergyItems.discharge(inventory,0,energy,MachineProfiles.charging(k));
        if(k.equals("creative_power_cube"))energy.receiveEnergy(Integer.MAX_VALUE,false);
        if(chargesItems())chargeItem();else MachinePrograms.discharge(this);
        if(Set.of("calculator_locator","conductor_mast").contains(k))pushEnergy();
        if(MachinePrograms.tick(this))return;
        if(k.equals("dynamic_calculator")){DynamicProgram.tick(this);return;}
        if(k.endsWith("extractor")){generate();pushEnergy();return;}
        if(Set.of("power_cube","advanced_power_cube","creative_power_cube","hand_cranked_generator","analysing_chamber").contains(k))pushEnergy();
        if(k.equals("analysing_chamber")){analyse();return;}
        if(k.equals("health_processor")||k.equals("hunger_processor")){processNutrition();return;}
        if(k.equals("reinforced_furnace")){smelt();return;}
        String machine=switch(k){case "atomic_calculator"->"atomic";
            case "docking_station"->switch(Content.path(inventory.getStackInSlot(20))){case "calculator"->"calculator";case "scientific_calculator"->"scientific";case "flawless_calculator"->"flawless";case "atomic_calculator"->"atomic";default->"none";};
            default->k;};
        var found=recipeSelection.find(level,machine,owner(),inventory,inputCount());
        if(found.isEmpty()){if(progress>0){progress=0;activeRecipe="";setChanged();}return;}
        RecipeHolder<ProcessRecipe> holder=found.get();ProcessRecipe recipe=holder.value();
        int cost=upgradeEnergyCost(CalculatorConfig.energy(k,RecipePolicies.energy(holder,k.equals("docking_station")?10:0)));
        totalTicks=upgradeProcessTicks(CalculatorConfig.ticks(k,recipe.ticks()));
        if(!activeRecipe.equals(holder.id().toString())){progress=0;activeRecipe=holder.id().toString();}
        if(energy.getEnergyStored()<cost){if(!CalculatorConfig.machineFlag(k,"retainProgressWithoutPower"))progress=0;return;}
        if(++progress>=totalTicks){
            // Revalidate against live inputs at commit, even when the selector was cached.
            int[] used=recipe.allocation(ProcessTransactions.input(inventory,inputCount()));
            if(used!=null){
                energy.extractEnergy(cost,false);
                pending.addAll(ProcessTransactions.consume(inventory,used,recipe,level));
                ResearchData.completed(level,owner(),machine);
                ProcessTransactions.flush(pending,inventory);
            }
            progress=0;
        }setChanged();
    }
    public int upgrades(String id){int n=0;for(int i=21;i<25;i++)if(Content.path(inventory.getStackInSlot(i)).equals(id))n+=inventory.getStackInSlot(i).getCount();return Math.min(CalculatorConfig.integer("upgrades.maxPerType",16),n);}
    private void chargeItem(){
        ItemStack stack=inventory.getStackInSlot(20);IEnergyStorage other=com.foundations.calculator.api.FoundationsEnergy.item(stack);
        if(other==null)return;
        int offered=(int)Math.min(energy.stored(),MachineProfiles.charging(kind()));
        int accepted=other.receiveEnergy(offered,false);energy.extractEnergy(Math.clamp(accepted,0,offered),false);
        if(accepted>0)setChanged();
    }
    public final com.foundations.calculator.core.TransferBudget euInputBudget=new com.foundations.calculator.core.TransferBudget();
    public final com.foundations.calculator.core.TransferBudget euOutputBudget=new com.foundations.calculator.core.TransferBudget();
    private void pushEnergy(){
        for(Direction side:Direction.values()){
            if(!energySideOutput(side))continue;
            if(!level.hasChunkAt(worldPosition.relative(side)))continue;
            int nativeSent=com.foundations.calculator.api.FoundationsEnergy.sendNative(this,side,(int)Math.min(energy.stored(),MachineProfiles.transfer(kind())));
            if(nativeSent>=0){energy.extractEnergy(nativeSent,false);continue;}
            // Our own storage banks have long-valued buffers/rates; the public FE facade
            // remains int-valued for external receivers. Preserve both FE policy gates.
            if(MachineProfiles.storage(kind())
                &&level.getBlockEntity(worldPosition.relative(side)) instanceof MachineBlockEntity bank
                &&MachineProfiles.storage(bank.kind())){
                if(!com.foundations.calculator.core.PowerPolicy.FE.input(com.foundations.calculator.core.PowerPolicy.Scope.BLOCK)
                    ||!com.foundations.calculator.core.PowerPolicy.FE.output(com.foundations.calculator.core.PowerPolicy.Scope.BLOCK))continue;
                var receiver=bank.longEnergyPort(side.getOpposite());
                if(receiver==null||!receiver.canReceive())continue;
                long amount=Math.min(energy.stored(),MachineProfiles.transferLong(kind()));
                if(CalculatorConfig.flag("automation.balanceCubes",true)&&program.getInt("Side"+side.get3DDataValue())==0
                    &&bank.program.getInt("Side"+side.getOpposite().get3DDataValue())==0)
                    amount=EnergyBalancing.transfer(energy.stored(),energy.capacity(),bank.energy.stored(),bank.energy.capacity(),amount);
                energy.extract(Math.clamp(receiver.receive(amount,false),0L,amount),false);
                continue;
            }
            IEnergyStorage other=com.foundations.calculator.api.FoundationsEnergy.block(level,worldPosition.relative(side),side.getOpposite());
            if(other==null||!other.canReceive())continue;
            int n=(int)Math.min(energy.stored(),MachineProfiles.transfer(kind()));
            // Automatic storage ports equalize banks instead of sending the same FE
            // backwards every tick. Explicit input/output sides retain forced routing.
            if(CalculatorConfig.flag("automation.balanceCubes",true)&&MachineProfiles.storage(kind())&&program.getInt("Side"+side.get3DDataValue())==0
                &&level.getBlockEntity(worldPosition.relative(side)) instanceof MachineBlockEntity bank
                &&MachineProfiles.storage(bank.kind())&&bank.program.getInt("Side"+side.getOpposite().get3DDataValue())==0){
                n=(int)EnergyBalancing.transfer(energy.stored(),energy.capacity(),bank.energy.stored(),bank.energy.capacity(),n);
            }
            energy.extractEnergy(Math.clamp(other.receiveEnergy(n,false),0,n),false);
        }
    }
    private void generate(){
        String machine=kind();ItemStack feed=inventory.getStackInSlot(1);
        if(!feed.isEmpty()){
            ProcessInput input=new ProcessInput(List.of(feed));
            for(var r:RecipeIndex.forMachine(level,machine,owner())){
                ProcessRecipe recipe=r.value();
                if(recipe.machine().equals(machine)&&recipe.matches(input,level)&&recipe.value()<=CalculatorConfig.integer("generation.extractorNutrientCapacity",5000)-nutrient){
                    int count=recipe.allocation(input)[0];
                    for(int i=0;i<count;i++)if(feed.hasCraftingRemainingItem())pending.add(feed.getCraftingRemainingItem());
                    inventory.extractItem(1,count,false);nutrient+=recipe.value();
                    for(var result:recipe.outputs()){ItemStack out=result.roll(level.random);if(!out.isEmpty())pending.add(out);}
                    setChanged();break;
                }
            }
        }
        if(energy.stored()>=energy.capacity())return;
        if(burnTime==0&&nutrient>=CalculatorConfig.integer("generation.extractorNutrientCost",400)){
            ItemStack fuel=inventory.getStackInSlot(0);int burn=fuel.getBurnTime(RecipeType.SMELTING);
            if(burn>0){
                ItemStack rem=fuel.getCraftingRemainingItem();inventory.extractItem(0,1,false);
                if(!rem.isEmpty())pending.add(rem);burnTime=burn;nutrient-=CalculatorConfig.integer("generation.extractorNutrientCost",400);
            }
        }
        if(burnTime>0){
            int output=switch(machine){case "starch_extractor"->40;case "redstone_extractor"->80;default->160;};
            energy.receiveEnergy(CalculatorConfig.integer("generation."+machine.replace("_extractor","")+"PerTick",output),false);burnTime--;setChanged();
        }
    }
    private void smelt(){
        ItemStack in=inventory.getStackInSlot(0);if(in.isEmpty()){progress=0;return;}
        var r=level.getRecipeManager().getRecipeFor(RecipeType.SMELTING,new SingleRecipeInput(in),level);
        int cost=upgradeEnergyCost(CalculatorConfig.energy(kind(),500));
        if(r.isEmpty()){progress=0;return;}
        if(energy.getEnergyStored()<cost){if(!CalculatorConfig.machineFlag(kind(),"retainProgressWithoutPower"))progress=0;return;}
        String recipeId=r.get().id().toString();if(!activeRecipe.equals(recipeId)){activeRecipe=recipeId;progress=0;}
        totalTicks=upgradeProcessTicks(CalculatorConfig.ticks(kind(),200));
        if(++progress>=totalTicks){
            ItemStack out=r.get().value().assemble(new SingleRecipeInput(in),level.registryAccess());
            ItemStack remainder=in.getCraftingRemainingItem();inventory.extractItem(0,1,false);
            pending.add(out);if(!remainder.isEmpty())pending.add(remainder);energy.extractEnergy(cost,false);progress=0;
            ProcessTransactions.flush(pending,inventory);
        }setChanged();
    }
    private void analyse(){
        ItemStack circuit=inventory.getStackInSlot(0);
        if(!Content.path(circuit).startsWith("circuit_board_")||CircuitData.analysed(circuit)){progress=0;return;}
        int cost=upgradeEnergyCost(CalculatorConfig.energy(kind(),0));totalTicks=upgradeProcessTicks(CalculatorConfig.ticks(kind(),1));
        if(energy.getEnergyStored()<cost){if(!CalculatorConfig.machineFlag(kind(),"retainProgressWithoutPower"))progress=0;return;}
        if(++progress<totalTicks){setChanged();return;}progress=0;energy.extractEnergy(cost,false);
        circuit=inventory.extractItem(0,1,false);
        CircuitData.initialize(circuit,level.random);
        CompoundTag tag=CircuitData.tag(circuit);
        ProcessInput input=new ProcessInput(List.of(circuit));
        for(int category=0;category<=6;category++){
            int roll=tag.getInt(category==0?"Energy":"Item"+category);
            var reward=RecipeIndex.forMachine(level,"analysis_"+category).stream()
                .filter(r->r.value().value()==roll&&r.value().matches(input,level)).findFirst();
            if(reward.isPresent()){
                ProcessRecipe recipe=reward.get().value();
                for(var result:recipe.outputs()){ItemStack out=result.roll(level.random);if(!out.isEmpty())pending.add(out);}
                energy.receiveEnergy(recipe.energy(),false);
            }
        }
        CircuitData.markAnalysed(circuit);
        pending.add(circuit);
        flushResults();setChanged();
    }
    private void processNutrition(){
        String type=kind().equals("health_processor")?"health":"hunger";
        MachinePrograms.nutritionNetwork(this,type);
        ItemStack in=inventory.getStackInSlot(0),module=inventory.getStackInSlot(20);
        int points=NutritionData.get(in,type);
        if(points>0){int n=Math.min(CalculatorConfig.integer("nutrition.machineTransfer",4),Math.min(points,Integer.MAX_VALUE-nutrient));NutritionData.set(in,type,points-n);nutrient+=n;setChanged();}
        else if(NutritionData.capacity(in,type)==0&&!in.isEmpty()){
            ProcessInput input=new ProcessInput(List.of(in));
            var recipe=RecipeIndex.forMachine(level,kind()).stream().filter(r->r.value().matches(input,level)).findFirst();
            int value=recipe.isPresent()?recipe.get().value().value():0;
            int count=recipe.isPresent()?recipe.get().value().allocation(input)[0]:1;
            if(recipe.isEmpty()&&type.equals("hunger")){
                var food=in.getFoodProperties(null);if(food!=null)value=food.nutrition();
            }
            if(value>0&&value<=Integer.MAX_VALUE-nutrient){
                for(int i=0;i<count;i++)if(in.hasCraftingRemainingItem())pending.add(in.getCraftingRemainingItem());
                inventory.extractItem(0,count,false);nutrient+=value;
                if(recipe.isPresent())for(var result:recipe.get().value().outputs()){ItemStack out=result.roll(level.random);if(!out.isEmpty())pending.add(out);}
                setChanged();
            }
        }
        int n=NutritionData.add(module,type,Math.min(CalculatorConfig.integer("nutrition.machineTransfer",4),nutrient));
        if(n>0){nutrient-=n;setChanged();}
    }
    public void dropContents(){
        if(level==null||level.isClientSide||suppressDrops)return;
        if(kind().equals("module_workstation")){ModuleWorkstation.flush(this);updatingModules=true;for(int i=0;i<16;i++)inventory.setStackInSlot(i,ItemStack.EMPTY);}
        for(int i=0;i<SIZE;i++){Containers.dropItemStack(level,worldPosition.getX()+.5,worldPosition.getY()+.5,worldPosition.getZ()+.5,inventory.getStackInSlot(i));inventory.setStackInSlot(i,ItemStack.EMPTY);}
        if(bulkInventory())for(int i=0;i<bulk.getSlots();i++)while(bulk.count(i)>0)Containers.dropItemStack(level,worldPosition.getX()+.5,worldPosition.getY()+.5,worldPosition.getZ()+.5,bulk.extractItem(i,64,false));
        for(ItemStack s:pending)Containers.dropItemStack(level,worldPosition.getX()+.5,worldPosition.getY()+.5,worldPosition.getZ()+.5,s);
        pending.clear();
    }
    protected void saveAdditional(CompoundTag tag,HolderLookup.Provider lookup){
        super.saveAdditional(tag,lookup);tag.put("Program",program.copy());if(bulkInventory())tag.put("Bulk",bulk.save(lookup));tag.put("Inventory",inventory.serializeNBT(lookup));tag.putLong("Energy",energy.stored());
        tag.putInt("Progress",progress);tag.putInt("BurnTime",burnTime);tag.putInt("Nutrient",nutrient);tag.putString("Recipe",activeRecipe);
        ListTag list=new ListTag();for(ItemStack s:pending)if(!s.isEmpty())list.add(s.save(lookup));tag.put("Pending",list);
    }
    protected void loadAdditional(CompoundTag tag,HolderLookup.Provider lookup){
        super.loadAdditional(tag,lookup);program=tag.getCompound("Program").copy();
        chestOpeners.clear();program.remove("LidOpen");program.remove("Checked");program.remove("DynamicFormed");
        greenhouseGeometry=null;recipeSelection.clear();clientProgram.clear();
        inventory.deserializeNBT(lookup,tag.getCompound("Inventory"));energy.load(tag.getLong("Energy"));
        if(bulkInventory()){bulk.load(tag.getCompound("Bulk"),lookup);if(!tag.contains("Bulk"))for(int i=0;i<SIZE;i++){ItemStack left=inventory.getStackInSlot(i);for(int n=0;n<bulk.getSlots()&&!left.isEmpty();n++)left=bulk.insertItem(n,left,false);inventory.setStackInSlot(i,left);}}
        progress=Math.max(0,tag.getInt("Progress"));burnTime=Math.max(0,tag.getInt("BurnTime"));nutrient=Math.max(0,tag.getInt("Nutrient"));activeRecipe=tag.getString("Recipe");
        pending.clear();for(Tag t:tag.getList("Pending",Tag.TAG_COMPOUND))ItemStack.parse(lookup,t).ifPresent(pending::add);
    }
    public void setActive(boolean active){if(level!=null&&getBlockState().getValue(MachineBlock.ACTIVE)!=active)level.setBlockAndUpdate(worldPosition,getBlockState().setValue(MachineBlock.ACTIVE,active));}
    public boolean action(Player player,int action){
        if(level==null||level.isClientSide||player.level()!=level||player.distanceToSqr(worldPosition.getCenter())>64||
            !level.mayInteract(player,worldPosition))return false;
        if(action==27){
            if(player instanceof net.minecraft.server.level.ServerPlayer sp && PowerDiagnostics.showMachine(sp,this)){
                sp.closeContainer();return true;
            }
            return false;
        }
        if(action==26){program.putInt("RedstoneMode",(redstoneMode()+1)%4);setChanged();return true;}
        if(action>=20&&action<26){String key="Side"+(action-20);program.putInt(key,(program.getInt(key)+1)%4);setChanged();return true;}
        switch(kind()){
            case "basic_greenhouse","advanced_greenhouse","flawless_greenhouse"->{
                if(action==10){program.putBoolean("Paused",!program.getBoolean("Paused"));}
                else if(action==11&&!kind().equals("flawless_greenhouse"))program.putInt("HouseState",1);
                else if(action==12&&!kind().equals("flawless_greenhouse"))program.putInt("HouseState",3);
                else return false;
            }
            case "weather_controller"->{if(action==10)program.putInt("Mode",(program.getInt("Mode")+1)%3);else if(action==11)program.putBoolean("Target",!program.getBoolean("Target"));else return false;progress=0;}
            case "magnetic_flux"->{if(action==10)program.putBoolean("Whitelist",!program.getBoolean("Whitelist"));else if(action==11)program.putBoolean("MatchTags",!program.getBoolean("MatchTags"));else return false;}
            default->{return false;}
        }setChanged();return true;
    }
    public void onDataPacket(net.minecraft.network.Connection connection,net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket packet,HolderLookup.Provider lookup){handleUpdateTag(packet.getTag(),lookup);}
    public net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket getUpdatePacket(){return net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket.create(this);}
    public CompoundTag getUpdateTag(HolderLookup.Provider lookup){CompoundTag tag=new CompoundTag();tag.put("Program",program.copy());tag.putInt("Progress",progress);tag.putInt("TotalTicks",totalTicks);tag.putLong("Energy",energy.stored());tag.putLong("LastCrank",lastCrankTick);if(!inventory.getStackInSlot(0).isEmpty())tag.put("Display",inventory.getStackInSlot(0).save(lookup));return tag;}
    public void handleUpdateTag(CompoundTag tag,HolderLookup.Provider lookup){program=tag.getCompound("Program");progress=tag.getInt("Progress");totalTicks=tag.getInt("TotalTicks");energy.load(tag.getLong("Energy"));lastCrankTick=tag.getLong("LastCrank");inventory.setStackInSlot(0,tag.contains("Display")?ItemStack.parse(lookup,tag.get("Display")).orElse(ItemStack.EMPTY):ItemStack.EMPTY);}
    public Component getDisplayName(){return getBlockState().getBlock().getName();}
    public AbstractContainerMenu createMenu(int id,Inventory inv,Player p){return bulkInventory()?new com.foundations.calculator.menu.BulkStorageMenu(id,inv,this):CalculatorMenu.forMachine(id,inv,this);}
}
