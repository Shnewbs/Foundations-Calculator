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
import com.foundations.calculator.core.*;
import com.foundations.calculator.api.EnergyPort;

public class MachineBlockEntity extends BlockEntity implements MenuProvider {
    public static final int SIZE=25;
    private static final Set<String> EARLY_ENERGY_PUSHERS=Set.of("calculator_locator","conductor_mast");
    private static final Set<String> ENERGY_PUSHERS=Set.of("power_cube","advanced_power_cube","creative_power_cube","hand_cranked_generator","analysing_chamber");
    private static final Direction[] DIRECTIONS=Direction.values();
    private net.minecraft.world.level.block.Block kindBlock;
    private String cachedKind;
    private final EnergyOperations.DiscountCache energyDiscount=new EnergyOperations.DiscountCache();
    public final StoredEnergy energy;
    public final BulkStorage bulk;
    public final MachineOutputs outputBuffer=MachineOutputs.supports(kind())?new MachineOutputs(this):null;
    public final BasicInventory inventory=new BasicInventory(SIZE){
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
    private MachineRecipeSelection[] dynamicSelections;
    MachineRecipeSelection dynamicSelection(int lane) {
        if(dynamicSelections==null)dynamicSelections=new MachineRecipeSelection[]{new MachineRecipeSelection(),new MachineRecipeSelection(),new MachineRecipeSelection()};
        return dynamicSelections[lane];
    }
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
            side==null?0:program.getIntOr("Side"+side.get3DDataValue(),0),false);
    }
    public boolean itemOutput(Direction side) {
        return AutomationRules.permits(CalculatorConfig.machineFlag(kind(),"itemAutomation"),
            !kind().equals("module_workstation")&&!kind().equals("magnetic_flux"),
            side==null?0:program.getIntOr("Side"+side.get3DDataValue(),0),true);
    }
    /** Returns whether a packet was requested, useful to native regression tests. */
    public boolean syncClientState() {
        if(level==null||level.isClientSide()||isRemoved())return false;
        ItemStack display=inventory.getStackInSlot(0);
        boolean changed=clientProgram.differs(program)||sentProgress!=progress||sentTotalTicks!=totalTicks
            ||sentEnergy!=energy.stored()||sentCrank!=lastCrankTick||!ItemStack.matches(clientDisplay,display);
        if(!changed&&CalculatorConfig.flag("performance.changeOnlySync",true))return false;
        clientProgram.capture(program::copy);clientDisplay=display.copy();sentProgress=progress;
        sentTotalTicks=totalTicks;sentEnergy=energy.stored();sentCrank=lastCrankTick;
        level.sendBlockUpdated(worldPosition,getBlockState(),getBlockState(),2);return true;
    }
    public void startOpen(Player player) {
        if(level==null||level.isClientSide()||!kind().equals("reinforced_chest")||player.isSpectator())return;
        if(chestOpeners.add(player.getUUID()))updateLid();
    }
    public void stopOpen(Player player) {
        if(chestOpeners.remove(player.getUUID()))updateLid();
    }
    private void updateLid() {
        if(level==null||level.isClientSide()||isRemoved())return;
        boolean open=!chestOpeners.isEmpty();if(open==program.getBooleanOr("LidOpen",false))return;
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
        if(dynamicSelections!=null)for(var selection:dynamicSelections)selection.clear();
        super.setRemoved();
    }
    public MachineBlockEntity(BlockPos p,BlockState state){
        super(Content.MACHINE_ENTITY.get(),p,state);bulk=new BulkStorage(kind().equals("storage_chamber"),()->CalculatorConfig.integer(kind().equals("storage_chamber")?"storage.circuitChamberPerBin":kind().equals("algorithm_assimilator")?"storage.assimilatorPerBin":"storage.reinforcedChestPerBin",kind().equals("storage_chamber")?1024:kind().equals("algorithm_assimilator")?64:256),this::setChanged);
        energy=new StoredEnergy(()->CalculatorConfig.machineCapacity(kind(),MachineDefinition.forMachine(kind()).capacity()),this::setChanged);
    }
    public int redstoneMode(){return program.contains("RedstoneMode")?program.getIntOr("RedstoneMode",0):CalculatorConfig.machine(kind(),"defaultRedstoneMode",0);}
    public java.util.UUID owner(){return com.foundations.calculator.core.UuidTags.has(program,"Owner")?com.foundations.calculator.core.UuidTags.get(program,"Owner"):null;}
    public String kind(){
        var block=getBlockState().getBlock();
        if(block!=kindBlock){cachedKind=net.minecraft.core.registries.BuiltInRegistries.BLOCK.getKey(block).getPath();kindBlock=block;}
        return cachedKind;
    }
    public int inputCount(){return MachineDefinition.forMachine(kind()).inputCount();}
    public boolean bulkInventory(){return storage()||kind().equals("algorithm_assimilator");}
    private void flushResults(){if(outputBuffer!=null){outputBuffer.flush(pending);return;}if(!bulkInventory()){ProcessTransactions.flush(pending,inventory);return;}for(var iterator=pending.listIterator();iterator.hasNext();){ItemStack stack=iterator.next();for(int i=0;i<bulk.getSlots()&&!stack.isEmpty();i++)stack=bulk.insertItem(i,stack,false);if(stack.isEmpty())iterator.remove();else iterator.set(stack);}}
    public boolean storage(){return kind().equals("reinforced_chest")||kind().equals("storage_chamber");}
    public boolean hasOutputs(){return MachineDefinition.forMachine(kind()).outputs();}
    public boolean hasBatterySlot(){return MachineDefinition.forMachine(kind()).batterySlot();}
    public boolean supportsUpgrades(){return MachineDefinition.forMachine(kind()).upgrades();}
    public boolean usesEnergy(){return MachineDefinition.forMachine(kind()).usesEnergy();}
    public boolean chargesItems(){return MachineProfiles.storage(kind())||MachineProfiles.generator(kind());}
    /** Effective per-cycle FE after installed Energy Upgrades. */
    public int upgradeEnergyCost(int base){return (int)Math.min(Integer.MAX_VALUE,upgradeEnergyCost((long)base));}
    public long upgradeEnergyCost(long base){
        int amount=supportsUpgrades()?upgrades("energy_upgrade"):0;
        return energyDiscount.apply(base,amount,CalculatorConfig.decimal("upgrades.energyDiscount",.1));
    }
    /** Effective processing duration after installed Speed Upgrades. */
    public int upgradeProcessTicks(int base){
        int amount=supportsUpgrades()?upgrades("speed_upgrade"):0;
        return Math.max(1,(int)(Math.max(1,base)/(1.0+amount*CalculatorConfig.decimal("upgrades.speedBonus",.25))));
    }
    public boolean energySideInput(Direction side){if(!CalculatorConfig.machineFlag(kind(),"energyInput"))return false;int mode=side==null?0:program.getIntOr("Side"+side.get3DDataValue(),0);return mode==1||mode==0&&!MachineProfiles.generator(kind());}
    public boolean energySideOutput(Direction side){if(!CalculatorConfig.machineFlag(kind(),"energyOutput"))return false;int mode=side==null?0:program.getIntOr("Side"+side.get3DDataValue(),0);return mode==2||mode==0&&(MachineProfiles.generator(kind())||MachineProfiles.storage(kind()));}
    public boolean sideInput(Direction side){int mode=side==null?0:program.getIntOr("Side"+side.get3DDataValue(),0);return mode==0||mode==1;}
    public boolean sideOutput(Direction side){int mode=side==null?0:program.getIntOr("Side"+side.get3DDataValue(),0);return mode==0||mode==2;}
    public com.foundations.calculator.api.LongEnergyStorage longEnergyPort(Direction side){if(!usesEnergy())return null;return new com.foundations.calculator.api.LongEnergyStorage(){
        public long receive(long n,boolean simulate){return energySideInput(side)?energy.receive(Math.min(Math.max(0L,n),MachineProfiles.transferLong(kind())),simulate):0;}
        public long extract(long n,boolean simulate){return energySideOutput(side)?energy.extract(Math.min(Math.max(0L,n),MachineProfiles.transferLong(kind())),simulate):0;}
        public long stored(){return energy.stored();}public long capacity(){return energy.capacity();}
        public boolean canReceive(){return energySideInput(side);}public boolean canExtract(){return energySideOutput(side);}
    };}
    public net.neoforged.neoforge.transfer.energy.EnergyHandler transactionalEnergyPort(Direction side){
        if(!usesEnergy())return null;
        return new com.foundations.calculator.platform.SidedEnergyHandler(energy,
            ()->energySideInput(side)&&com.foundations.calculator.core.PowerPolicy.FE.input(com.foundations.calculator.core.PowerPolicy.Scope.BLOCK),
            ()->energySideOutput(side)&&com.foundations.calculator.core.PowerPolicy.FE.output(com.foundations.calculator.core.PowerPolicy.Scope.BLOCK),
            ()->MachineProfiles.transferLong(kind()));
    }
    public EnergyPort energyPort(Direction side){var power=longEnergyPort(side);if(power==null)return null;return new EnergyPort(){
        public int receiveEnergy(int n,boolean simulate){return (int)Math.min(Integer.MAX_VALUE,power.receive(Math.max(0,n),simulate));}
        public int extractEnergy(int n,boolean simulate){return (int)Math.min(Integer.MAX_VALUE,power.extract(Math.max(0,n),simulate));}
        public int getEnergyStored(){return (int)Math.min(Integer.MAX_VALUE,power.stored());}public int getMaxEnergyStored(){return (int)Math.min(Integer.MAX_VALUE,power.capacity());}
        public boolean canReceive(){return power.canReceive();}public boolean canExtract(){return power.canExtract();}
    };}
    public ItemInventory automation(Direction face){
        ItemInventory base=bulkInventory()?bulk:inventory;
        return new ItemInventory(){
            public int getSlots(){return base.getSlots()+(outputBuffer==null?0:MachineOutputs.EXTRA);}
            public ItemStack getStackInSlot(int slot){return slot>=base.getSlots()&&outputBuffer!=null?outputBuffer.get(slot-base.getSlots()+6):base.getStackInSlot(slot);}
            private boolean extra(int slot,boolean output){
                int mode=face==null?0:program.getIntOr("Side"+face.get3DDataValue(),0);
                return mode==(output?2:1)&&(slot==20&&hasBatterySlot()&&CalculatorConfig.flag("automation.allowBatterySlotAccess",true)||slot>=21&&supportsUpgrades()&&CalculatorConfig.flag("automation.allowUpgradeSlotAccess",false));
            }
            public ItemStack insertItem(int slot,ItemStack stack,boolean simulate){
                if(slot>=base.getSlots()&&outputBuffer!=null)return stack;
                boolean normal=slot<inputCount()&&(face!=Direction.DOWN||program.getIntOr("Side"+Direction.DOWN.get3DDataValue(),0)==1);
                return itemInput(face)&&(bulkInventory()||normal||extra(slot,false))?base.insertItem(slot,stack,simulate):stack;
            }
            public ItemStack extractItem(int slot,int count,boolean simulate){
                if(slot>=base.getSlots()&&outputBuffer!=null)return itemOutput(face)?outputBuffer.extract(slot-base.getSlots()+6,count,simulate):ItemStack.EMPTY;
                return itemOutput(face)&&(bulkInventory()||slot>=14&&slot<20||extra(slot,true))?base.extractItem(slot,count,simulate):ItemStack.EMPTY;
            }
            public int getSlotLimit(int slot){return slot>=base.getSlots()&&outputBuffer!=null?64:base.getSlotLimit(slot);}
            public boolean isItemValid(int slot,ItemStack stack){return !(slot>=base.getSlots()&&outputBuffer!=null)&&itemInput(face)&&base.isItemValid(slot,stack)&&(bulkInventory()||
                    slot<inputCount()&&(face!=Direction.DOWN||program.getIntOr("Side"+Direction.DOWN.get3DDataValue(),0)==1)||extra(slot,false));}
        };
    }
    public static void tick(Level level,BlockPos pos,BlockState state,MachineBlockEntity m){
        m.serverTick();
        // Early returns for idle, disabled, redstone-paused or blocked-output machines must not suppress UI updates.
        if(m.workDue(CalculatorConfig.integer("performance.clientSyncInterval",10)))m.syncClientState();
    }
    public static void clientTick(Level level,BlockPos pos,BlockState state,MachineBlockEntity m){m.previousLid=m.lid;float speed=(float)ClientConfig.chestSpeed();m.lid=Math.clamp(m.lid+(m.program.getBooleanOr("LidOpen",false)?speed:-speed),0,1);}
    private void serverTick(){
        if(level==null||level.isClientSide())return;
        if(kind().equals("reinforced_chest"))recheckOpeners();
        if(storage()||!CalculatorConfig.machineEnabled(kind()))return;
        int redstone=redstoneMode();
        if(redstone==3)return;
        if(redstone==1||redstone==2){
            boolean powered=level.hasNeighborSignal(worldPosition);
            if(redstone==1&&!powered||redstone==2&&powered)return;
        }
        MachinePrograms.export(this);
        if(!pending.isEmpty()){
            flushResults();setChanged();
            if(!pending.isEmpty()){if(CalculatorConfig.flag("upgrades.allowVoid",true)&&upgrades("void_upgrade")>0)pending.clear();else return;}
        }
        String k=kind();
        if(MachineProfiles.storage(k))EnergyItems.discharge(inventory,0,energy,MachineProfiles.chargingLong(k));
        if(k.equals("creative_power_cube"))energy.receiveEnergy(Integer.MAX_VALUE,false);
        if(chargesItems())chargeItem();else MachinePrograms.discharge(this);
        if(EARLY_ENERGY_PUSHERS.contains(k))pushEnergy();
        if(MachinePrograms.tick(this))return;
        if(k.equals("dynamic_calculator")){DynamicProgram.tick(this);return;}
        if(k.endsWith("extractor")){generate();pushEnergy();return;}
        if(ENERGY_PUSHERS.contains(k))pushEnergy();
        if(k.equals("analysing_chamber")){analyse();return;}
        if(k.equals("health_processor")||k.equals("hunger_processor")){processNutrition();return;}
        if(k.equals("reinforced_furnace")){smelt();return;}
        String machine=switch(k){case "atomic_calculator"->"atomic";
            case "docking_station"->switch(Content.path(inventory.getStackInSlot(20))){case "calculator"->"calculator";case "scientific_calculator"->"scientific";case "flawless_calculator"->"flawless";case "atomic_calculator"->"atomic";default->"none";};
            default->k;};
        var found=recipeSelection.find(level,machine,owner(),inventory,inputCount());
        if(found.isEmpty()){if(progress>0){progress=0;activeRecipe="";setChanged();}return;}
        RecipeHolder<ProcessRecipe> holder=found.get();ProcessRecipe recipe=holder.value();
        long cost=upgradeEnergyCost(CalculatorConfig.energyLong(k,RecipePolicies.energy(holder,k.equals("docking_station")?10:0)));
        totalTicks=upgradeProcessTicks(CalculatorConfig.ticks(k,recipe.ticks()));
        if(!activeRecipe.equals(holder.id().identifier().toString())){progress=0;activeRecipe=holder.id().identifier().toString();}
        if(energy.stored()<cost){if(!CalculatorConfig.machineFlag(k,"retainProgressWithoutPower"))progress=0;return;}
        if(++progress>=totalTicks){
            // Revalidate against live inputs at commit, even when the selector was cached.
            int[] used=recipe.allocation(ProcessTransactions.input(inventory,inputCount()));
            if(used!=null){
                energy.extract(cost,false);
                pending.addAll(ProcessTransactions.consume(inventory,used,recipe,level));
                ResearchData.completed(level,owner(),machine);
                flushResults();
            }
            progress=0;
        }setChanged();
    }
    public int upgrades(String id){int n=0;for(int i=21;i<25;i++)if(Content.path(inventory.getStackInSlot(i)).equals(id))n+=inventory.getStackInSlot(i).getCount();return Math.min(CalculatorConfig.integer("upgrades.maxPerType",16),n);}
    private void chargeItem(){
        ItemStack stack=inventory.getStackInSlot(20);
        if(stack.getItem() instanceof CalculatorItem && !PowerPolicy.FE.output(PowerPolicy.Scope.ITEM))return;
        var other=com.foundations.calculator.api.FoundationsEnergy.longItem(stack);
        if(other==null)return;
        long offered=Math.min(energy.stored(),MachineProfiles.chargingLong(kind()));
        long accepted=other.receive(offered,false);energy.extract(Math.clamp(accepted,0L,offered),false);
        if(accepted>0)setChanged();
    }
    public final com.foundations.calculator.core.TransferBudget euInputBudget=new com.foundations.calculator.core.TransferBudget();
    public final com.foundations.calculator.core.TransferBudget euOutputBudget=new com.foundations.calculator.core.TransferBudget();
    private void pushEnergy(){
        for(Direction side:DIRECTIONS){
            if(energy.stored()<=0)break;
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
                if(CalculatorConfig.flag("automation.balanceCubes",true)&&program.getIntOr("Side"+side.get3DDataValue(),0)==0
                    &&bank.program.getIntOr("Side"+side.getOpposite().get3DDataValue(),0)==0)
                    amount=EnergyBalancing.transfer(energy.stored(),energy.capacity(),bank.energy.stored(),bank.energy.capacity(),amount);
                energy.extract(Math.clamp(receiver.receive(amount,false),0L,amount),false);
                continue;
            }
            EnergyPort other=com.foundations.calculator.api.FoundationsEnergy.block(level,worldPosition.relative(side),side.getOpposite());
            if(other==null||!other.canReceive())continue;
            int n=(int)Math.min(energy.stored(),MachineProfiles.transfer(kind()));
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
                    for(int i=0;i<count;i++)if(!com.foundations.calculator.core.PlatformItems.remainder(feed).isEmpty())pending.add(com.foundations.calculator.core.PlatformItems.remainder(feed));
                    inventory.extractItem(1,count,false);nutrient+=recipe.value();
                    for(var result:recipe.outputs()){ItemStack out=result.roll(level.getRandom());if(!out.isEmpty())pending.add(out);}
                    setChanged();break;
                }
            }
        }
        if(energy.stored()>=energy.capacity())return;
        if(burnTime==0&&nutrient>=CalculatorConfig.integer("generation.extractorNutrientCost",400)){
            ItemStack fuel=inventory.getStackInSlot(0);int burn=PlatformFuel.burnTime(this,fuel);
            if(burn>0){
                ItemStack rem=com.foundations.calculator.core.PlatformItems.remainder(fuel);inventory.extractItem(0,1,false);
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
        var r=level.getServer().getRecipeManager().getRecipeFor(RecipeType.SMELTING,new SingleRecipeInput(in),level);
        long cost=upgradeEnergyCost(CalculatorConfig.energyLong(kind(),500));
        if(r.isEmpty()){progress=0;return;}
        if(energy.stored()<cost){if(!CalculatorConfig.machineFlag(kind(),"retainProgressWithoutPower"))progress=0;return;}
        String recipeId=r.get().id().identifier().toString();if(!activeRecipe.equals(recipeId)){activeRecipe=recipeId;progress=0;}
        totalTicks=upgradeProcessTicks(CalculatorConfig.ticks(kind(),200));
        if(++progress>=totalTicks){
            ItemStack out=r.get().value().assemble(new SingleRecipeInput(in));
            ItemStack remainder=com.foundations.calculator.core.PlatformItems.remainder(in);inventory.extractItem(0,1,false);
            pending.add(out);if(!remainder.isEmpty())pending.add(remainder);energy.extract(cost,false);progress=0;
            ProcessTransactions.flush(pending,inventory);
        }setChanged();
    }
    private void analyse(){
        ItemStack circuit=inventory.getStackInSlot(0);
        if(!Content.path(circuit).startsWith("circuit_board_")||CircuitData.analysed(circuit)){progress=0;return;}
        long cost=upgradeEnergyCost(CalculatorConfig.energyLong(kind(),0));totalTicks=upgradeProcessTicks(CalculatorConfig.ticks(kind(),1));
        if(energy.stored()<cost){if(!CalculatorConfig.machineFlag(kind(),"retainProgressWithoutPower"))progress=0;return;}
        if(++progress<totalTicks){setChanged();return;}progress=0;energy.extract(cost,false);
        circuit=inventory.extractItem(0,1,false);
        CircuitData.initialize(circuit,level.getRandom());
        CompoundTag tag=CircuitData.tag(circuit);
        ProcessInput input=new ProcessInput(List.of(circuit));
        for(int category=0;category<=6;category++){
            int roll=tag.getIntOr(category==0?"Energy":"Item"+category,0);
            var reward=RecipeIndex.firstMatch(level,"analysis_"+category,null,input,r->r.value()==roll);
            if(reward.isPresent()){
                ProcessRecipe recipe=reward.get().value();
                for(var result:recipe.outputs()){ItemStack out=result.roll(level.getRandom());if(!out.isEmpty())pending.add(out);}
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
            var recipe=RecipeIndex.firstMatch(level,kind(),null,input);
            int value=recipe.isPresent()?recipe.get().value().value():0;
            int count=recipe.isPresent()?recipe.get().value().allocation(input)[0]:1;
            if(recipe.isEmpty()&&type.equals("hunger")){
                var food=in.get(net.minecraft.core.component.DataComponents.FOOD);if(food!=null)value=food.nutrition();
            }
            if(value>0&&value<=Integer.MAX_VALUE-nutrient){
                for(int i=0;i<count;i++)if(!com.foundations.calculator.core.PlatformItems.remainder(in).isEmpty())pending.add(com.foundations.calculator.core.PlatformItems.remainder(in));
                inventory.extractItem(0,count,false);nutrient+=value;
                if(recipe.isPresent())for(var result:recipe.get().value().outputs()){ItemStack out=result.roll(level.getRandom());if(!out.isEmpty())pending.add(out);}
                setChanged();
            }
        }
        int n=NutritionData.add(module,type,Math.min(CalculatorConfig.integer("nutrition.machineTransfer",4),nutrient));
        if(n>0){nutrient-=n;setChanged();}
    }
    @Override public void preRemoveSideEffects(BlockPos pos,BlockState state){dropContents();super.preRemoveSideEffects(pos,state);}
    public void restoreAutomationInventory(ItemStack[] items,CompoundTag bulkTag,CompoundTag data,java.util.List<ItemStack> queued){
        boolean previous=updatingModules;updatingModules=true;
        try{for(int i=0;i<items.length;i++)inventory.setStackInSlot(i,items[i].copy());bulk.load(bulkTag,level.registryAccess());program=data.copy();pending.clear();queued.forEach(stack->pending.add(stack.copy()));}
        finally{updatingModules=previous;}setChanged();
    }
    public void dropContents(){
        if(level==null||level.isClientSide()||suppressDrops)return;
        if(kind().equals("module_workstation")){ModuleWorkstation.flush(this);updatingModules=true;for(int i=0;i<16;i++)inventory.setStackInSlot(i,ItemStack.EMPTY);}
        for(int i=0;i<SIZE;i++){Containers.dropItemStack(level,worldPosition.getX()+.5,worldPosition.getY()+.5,worldPosition.getZ()+.5,inventory.getStackInSlot(i));inventory.setStackInSlot(i,ItemStack.EMPTY);}
        if(bulkInventory())for(int i=0;i<bulk.getSlots();i++)while(bulk.count(i)>0)Containers.dropItemStack(level,worldPosition.getX()+.5,worldPosition.getY()+.5,worldPosition.getZ()+.5,bulk.extractItem(i,64,false));
        if(outputBuffer!=null){for(ItemStack stack:outputBuffer.snapshot())Containers.dropItemStack(level,worldPosition.getX()+.5,worldPosition.getY()+.5,worldPosition.getZ()+.5,stack);outputBuffer.restore(java.util.stream.IntStream.range(0,MachineOutputs.EXTRA).mapToObj(i->ItemStack.EMPTY).toArray(ItemStack[]::new));}
        for(ItemStack s:pending)Containers.dropItemStack(level,worldPosition.getX()+.5,worldPosition.getY()+.5,worldPosition.getZ()+.5,s);
        pending.clear();
    }
    @Override protected void saveAdditional(net.minecraft.world.level.storage.ValueOutput output){
        super.saveAdditional(output);if(outputBuffer!=null)outputBuffer.serialize(output.child("ExtendedOutputs"));output.store("Program",CompoundTag.CODEC,program.copy());
        if(bulkInventory())bulk.serialize(output.child("Bulk"));inventory.serialize(output.child("Inventory"));output.putLong("Energy",energy.stored());
        output.putInt("Progress",progress);output.putInt("BurnTime",burnTime);output.putInt("Nutrient",nutrient);output.putString("Recipe",activeRecipe);
        var list=output.list("Pending",ItemStack.CODEC);for(ItemStack stack:pending)if(!stack.isEmpty())list.add(stack);
    }
    @Override protected void loadAdditional(net.minecraft.world.level.storage.ValueInput input){
        super.loadAdditional(input);if(outputBuffer!=null)outputBuffer.deserialize(input.childOrEmpty("ExtendedOutputs"));program=input.read("Program",CompoundTag.CODEC).orElseGet(CompoundTag::new).copy();
        chestOpeners.clear();program.remove("LidOpen");program.remove("Checked");program.remove("DynamicFormed");greenhouseGeometry=null;recipeSelection.clear();clientProgram.clear();
        inventory.deserialize(input.childOrEmpty("Inventory"));energy.load(input.getLongOr("Energy",0L));
        if(bulkInventory()){bulk.deserialize(input.childOrEmpty("Bulk"));if(input.child("Bulk").isEmpty())for(int i=0;i<SIZE;i++){ItemStack left=inventory.getStackInSlot(i);for(int n=0;n<bulk.getSlots()&&!left.isEmpty();n++)left=bulk.insertItem(n,left,false);inventory.setStackInSlot(i,left);}}
        progress=Math.max(0,input.getIntOr("Progress",0));burnTime=Math.max(0,input.getIntOr("BurnTime",0));nutrient=Math.max(0,input.getIntOr("Nutrient",0));activeRecipe=input.getStringOr("Recipe","");
        pending.clear();input.listOrEmpty("Pending",ItemStack.CODEC).forEach(pending::add);
    }
    public void setActive(boolean active){if(level!=null&&getBlockState().getValue(MachineBlock.ACTIVE)!=active)level.setBlockAndUpdate(worldPosition,getBlockState().setValue(MachineBlock.ACTIVE,active));}
    public boolean action(Player player,int action){
        if(level==null||level.isClientSide()||player.level()!=level||player.distanceToSqr(net.minecraft.world.phys.Vec3.atCenterOf(worldPosition))>64||
            !level.mayInteract(player,worldPosition))return false;
        if(action==27){
            if(player instanceof net.minecraft.server.level.ServerPlayer sp && PowerDiagnostics.showMachine(sp,this)){
                sp.closeContainer();return true;
            }
            return false;
        }
        if(action==26){program.putInt("RedstoneMode",(redstoneMode()+1)%4);setChanged();return true;}
        if(action>=20&&action<26){String key="Side"+(action-20);program.putInt(key,(program.getIntOr(key,0)+1)%4);setChanged();return true;}
        switch(kind()){
            case "basic_greenhouse","advanced_greenhouse","flawless_greenhouse"->{
                if(action==10){program.putBoolean("Paused",!program.getBooleanOr("Paused",false));}
                else if(action==11&&!kind().equals("flawless_greenhouse"))program.putInt("HouseState",1);
                else if(action==12&&!kind().equals("flawless_greenhouse"))program.putInt("HouseState",3);
                else return false;
            }
            case "weather_controller"->{if(action==10)program.putInt("Mode",(program.getIntOr("Mode",0)+1)%3);else if(action==11)program.putBoolean("Target",!program.getBooleanOr("Target",false));else return false;progress=0;}
            case "magnetic_flux"->{if(action==10)program.putBoolean("Whitelist",!program.getBooleanOr("Whitelist",false));else if(action==11)program.putBoolean("MatchTags",!program.getBooleanOr("MatchTags",false));else return false;}
            default->{return false;}
        }setChanged();return true;
    }
    public void onDataPacket(net.minecraft.network.Connection connection,net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket packet,HolderLookup.Provider lookup){handleUpdateTag(packet.getTag(),lookup);}
    public net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket getUpdatePacket(){return net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket.create(this);}
    public CompoundTag getUpdateTag(HolderLookup.Provider lookup){CompoundTag tag=new CompoundTag();tag.put("Program",program.copy());tag.putInt("Progress",progress);tag.putInt("TotalTicks",totalTicks);tag.putLong("Energy",energy.stored());tag.putLong("LastCrank",lastCrankTick);if(!inventory.getStackInSlot(0).isEmpty())tag.put("Display",PlatformItems.save(inventory.getStackInSlot(0),lookup));return tag;}
    public void handleUpdateTag(CompoundTag tag,HolderLookup.Provider lookup){program=tag.getCompoundOrEmpty("Program");progress=tag.getIntOr("Progress",0);totalTicks=tag.getIntOr("TotalTicks",0);energy.load(tag.getLongOr("Energy",0L));lastCrankTick=tag.getLongOr("LastCrank",0L);inventory.setStackInSlot(0,tag.contains("Display")?PlatformItems.load(tag.get("Display"),lookup).orElse(ItemStack.EMPTY):ItemStack.EMPTY);}
    public Component getDisplayName(){return getBlockState().getBlock().getName();}
    public AbstractContainerMenu createMenu(int id,Inventory inv,Player p){return bulkInventory()?new com.foundations.calculator.menu.BulkStorageMenu(id,inv,this):CalculatorMenu.forMachine(id,inv,this);}
}
