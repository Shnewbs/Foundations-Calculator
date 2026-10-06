package com.foundations.calculator.core;

import java.util.*;
import com.foundations.calculator.FoundationsCalculator;
import com.foundations.calculator.api.FoundationsEnergy;
import com.foundations.calculator.compat.*;
import com.foundations.calculator.content.MachineBlockEntity;
import com.foundations.calculator.core.PowerPolicy.Scope;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

/** On-demand, server-authoritative reports. No tick scanner, retained world refs, or transfer mutations. */
@EventBusSubscriber(modid=FoundationsCalculator.ID)
public final class PowerDiagnostics {
    private static final Map<ServerPlayer,Long> LAST_REPORT = new WeakHashMap<>();
    private static boolean allowed(ServerPlayer player) {
        if (!CalculatorConfig.flag("power.diagnostics.enabled",true)) {
            player.sendSystemMessage(Component.literal("Power diagnostics are disabled by the server.")); return false;
        }
        long now=player.serverLevel().getGameTime(); Long last=LAST_REPORT.get(player);
        if (last!=null && now>=last && now-last<CalculatorConfig.integer("power.diagnostics.cooldownTicks",20)) return false;
        LAST_REPORT.put(player,now); return true;
    }
    private static boolean allowed(CommandSourceStack source) {
        if (!CalculatorConfig.flag("power.diagnostics.enabled",true)) {
            source.sendFailure(Component.literal("Power diagnostics are disabled by the server.")); return false;
        }
        return !(source.getEntity() instanceof ServerPlayer player) || allowed(player);
    }
    public static boolean installed(PowerPolicy policy) { return policy.modId.isEmpty() || ModList.get().isLoaded(policy.modId); }
    /** Guarded calls keep optional API classes out of the standalone runtime loading path. */
    public static EnergyConversion conversion(String id) {
        return switch(id) {
            case "fe" -> PowerPolicy.FE.conversion(1,true);
            case "gtceu" -> installed(PowerPolicy.GREGTECH)?GregTechEnergy.conversion():null;
            case "mekanism" -> installed(PowerPolicy.MEKANISM)?MekanismEnergy.conversion():null;
            case "modernIndustrialization" -> installed(PowerPolicy.MI)?ModernIndustrializationEnergy.conversion():null;
            case "ae2" -> installed(PowerPolicy.AE2)?AE2Energy.conversion():null;
            case "grandPower" -> installed(PowerPolicy.GRANDPOWER)?GrandPowerEnergy.conversion():null;
            default -> null;
        };
    }
    private static String unit(String id) { return switch(id) { case "mekanism" -> "J"; case "gtceu","modernIndustrialization" -> "EU"; case "ae2" -> "AE"; default -> "FE"; }; }
    private static String source(PowerPolicy policy) {
        return switch(policy.id) {
            case "gtceu" -> CalculatorConfig.integer("compat.gtceu.fePerEU",0)>0?"Calculator override":"inherited GT ratio";
            case "mekanism" -> CalculatorConfig.decimal("power.mekanism.joulesPerFE",0)>0?"Calculator override":"inherited Mekanism ratio";
            case "modernIndustrialization" -> CalculatorConfig.decimal("power.modernIndustrialization.fePerEU",0)>0?"Calculator override":"inherited MI ratio";
            case "ae2" -> "compat.aeEnergyToFE";
            default -> "fixed same-unit ratio";
        };
    }
    public static List<String> overview() {
        List<String> lines=new ArrayList<>();
        lines.add("Foundations Calculator " + ModList.get().getModContainerById(FoundationsCalculator.ID).orElseThrow().getModInfo().getVersion() + " | effective server power configuration");
        lines.add("External route preference: "+(CalculatorConfig.flag("power.routing.preferNative",true)?"native first":"FE first")+". GT cable output always uses packets.");
        for(var policy:PowerPolicy.ALL) {
            if(!installed(policy)) { lines.add(policy.label+": not installed"); continue; }
            var plan=conversion(policy.id);
            String rate=plan==null?"INVALID / UNREPRESENTABLE RATE - transfers blocked":plan.base().nativeUnits()+" "+unit(policy.id)+" = "+plan.base().feUnits()+" FE ("+source(policy)+")";
            lines.add(policy.label+": "+rate);
            lines.add("  "+(policy==PowerPolicy.AE2?"no native grid port":"block in/out "+policy.input(Scope.BLOCK)+"/"+policy.output(Scope.BLOCK))+
                    "; item in/out "+policy.input(Scope.ITEM)+"/"+policy.output(Scope.ITEM)+"; losses in/out "+policy.inputLoss()+"%/"+policy.outputLoss()+"%");
            boolean mismatch=switch(policy.id){
                case "gtceu" -> GregTechEnergy.ratio()!=GregTechEnergy.inheritedRatio()||GregTechEnergy.upstreamRatiosDiffer();
                case "mekanism" -> Double.compare(MekanismEnergy.effectiveRatio(),MekanismEnergy.inheritedRatio())!=0;
                case "modernIndustrialization" -> Double.compare(ModernIndustrializationEnergy.effectiveRatio(),ModernIndustrializationEnergy.inheritedRatio())!=0;
                default -> false;
            };
            if(mismatch)lines.add("  WARNING: ratio differs from the other mod's FE conversion. Align both configs or avoid mixed-converter loops; Calculator cannot enforce another mod's exchange rate.");
            if(plan!=null && (policy.inputLoss()!=0||policy.outputLoss()!=0)) {
                lines.add("  Input quantum: "+plan.input().nativeUnits()+" "+unit(policy.id)+" -> "+plan.input().feUnits()+" FE; output quantum: "+plan.output().feUnits()+" FE -> "+plan.output().nativeUnits()+" "+unit(policy.id)+" (GT packets/AE items have their own rounding).");
            }
            if(policy==PowerPolicy.MEKANISM && !MekanismEnergy.enabled())lines.add("  Mekanism bridge is disabled locally or by Mekanism's FE-conversion setting.");
        }
        lines.add("Directions are relative to Foundations storage. Another mod may still use its own FE conversion.");
        lines.add("Use /foundations power inspect while looking at a machine; /foundations power item for a held battery.");
        return List.copyOf(lines);
    }
    private static String neighbor(MachineBlockEntity machine,Direction side) {
        var level=machine.getLevel(); var pos=machine.getBlockPos().relative(side);
        if(level==null||!level.hasChunkAt(pos))return "neighbor chunk not loaded (not loaded by this report)";
        if(!machine.energySideOutput(side)) {
            if(!machine.energySideInput(side))return "both local directions closed";
            return machine.energy.stored()>=machine.energy.capacity()?"input buffer full":"input open; incoming neighbor chooses its API";
        }
        int offered=(int)Math.min(machine.energy.stored(),MachineProfiles.transfer(machine.kind()));
        if(installed(PowerPolicy.GREGTECH)) {
            String gt=GregTechEnergy.targetStatus(machine,side,offered); if(gt!=null)return gt;
        }
        var route=FoundationsEnergy.blockRoute(level,pos,side.getOpposite()); var target=route.storage();
        if(target==null)return "no compatible neighbor port";
        String prefix="outbound "+route.id()+": ";
        if(!target.canReceive())return prefix+"input disabled by side, direction, integration or native rules";
        if(offered<=0)return prefix+"source empty / transfer rate zero";
        var plan=conversion(route.id());
        if(plan!=null && offered<plan.output().feUnits())return prefix+"offer "+offered+" FE is below output quantum "+plan.output().feUnits()+" FE";
        int accepted=Math.clamp(target.receiveEnergy(offered,true),0,offered);
        if(accepted>0)return prefix+"receiver simulates accepting "+accepted+" FE; no energy moved by report";
        if(target.getEnergyStored()>=target.getMaxEnergyStored())return prefix+"receiver full";
        return prefix+"receiver refused (native limit, conversion quantum or charge rule)";
    }
    public static List<String> machine(MachineBlockEntity machine) {
        List<String> lines=new ArrayList<>();
        lines.add("Power | "+machine.kind()+" at "+machine.getBlockPos().toShortString());
        lines.add("Stored "+String.format("%,d",machine.energy.stored())+" / "+String.format("%,d",machine.energy.capacity())+" FE; port limit "+MachineProfiles.transfer(machine.kind())+" FE; item limit "+MachineProfiles.charging(machine.kind())+" FE");
        var state=MachineDiagnostics.machine(machine);
        lines.add("Machine: "+state.status().label+(state.requiredEnergy()>0?"; requires "+state.requiredEnergy()+" FE":""));
        if(machine.program.contains("WorldActionBlocked"))lines.add("Last blocked world action: "+
            machine.program.getStringOr("WorldActionBlocked","")+" at "+BlockPos.of(machine.program.getLongOr("WorldActionPos",0L)).toShortString());
        if(installed(PowerPolicy.GREGTECH))lines.add("GT limits: input "+GregTechEnergy.setting(machine,"inputVoltage",Integer.MAX_VALUE)+" EU, "+GregTechEnergy.setting(machine,"inputAmperage",4)+" A/t; output "+GregTechEnergy.setting(machine,"outputVoltage",32)+" EU, "+GregTechEnergy.setting(machine,"outputAmperage",1)+" A/t (shared by faces)");
        for(var side:Direction.values()) {
            String mode=new String[]{"auto","input","output","disabled"}[Math.floorMod(machine.program.getIntOr("Side"+side.get3DDataValue(),0),4)];
            lines.add(side.getName()+" ["+mode+"]: "+neighbor(machine,side));
        }
        if(machine.hasBatterySlot()) {
            var route=FoundationsEnergy.itemRoute(machine.inventory.getStackInSlot(20));
            lines.add("Slot 20 adapter: "+route.id()+" (may be a module/calculator slot for this machine)");
        }
        lines.add("Ratios and conversion losses: /foundations power. Report shows configuration and simulation, not transfer history.");
        return List.copyOf(lines);
    }
    public static boolean showMachine(ServerPlayer player,MachineBlockEntity machine) {
        if(player.level()!=machine.getLevel()||player.distanceToSqr(machine.getBlockPos().getCenter())>64||!allowed(player))return false;
        for(String line:machine(machine))player.sendSystemMessage(Component.literal(line));
        return true;
    }
    private static int emit(CommandSourceStack source,List<String> lines) {
        for(String line:lines)source.sendSuccess(()->Component.literal(line),false); return 1;
    }
    private static int inspect(CommandSourceStack source,BlockPos pos) {
        if(!allowed(source))return 0;
        if(!source.getLevel().hasChunkAt(pos)) { source.sendFailure(Component.literal("That chunk is not loaded."));return 0; }
        if(!(source.getLevel().getBlockEntity(pos) instanceof MachineBlockEntity machine)) {
            source.sendFailure(Component.literal("Look at a Foundations Calculator machine."));return 0;
        }
        return emit(source,machine(machine));
    }
    @SubscribeEvent public static void commands(RegisterCommandsEvent event) {
        var power=Commands.literal("power").executes(ctx->allowed(ctx.getSource())?emit(ctx.getSource(),overview()):0);
        power.then(Commands.literal("inspect").executes(ctx->{
            var source=ctx.getSource(); var player=source.getPlayerOrException();
            var start=player.getEyePosition(); var end=start.add(player.getLookAngle().scale(8));
            BlockHitResult hit=player.level().clip(new ClipContext(start,end,ClipContext.Block.OUTLINE,ClipContext.Fluid.NONE,player));
            if(hit.getType()!=HitResult.Type.BLOCK) { source.sendFailure(Component.literal("Look at a nearby Calculator machine.")); return 0; }
            return inspect(source,hit.getBlockPos());
        }).then(Commands.argument("pos",BlockPosArgument.blockPos()).requires(s->s.hasPermission(2))
                .executes(ctx->inspect(ctx.getSource(),BlockPosArgument.getBlockPos(ctx,"pos")))));
        power.then(Commands.literal("item").executes(ctx->{
            var source=ctx.getSource();var player=source.getPlayerOrException();if(!allowed(source))return 0;
            var stack=player.getMainHandItem();var route=FoundationsEnergy.itemRoute(stack);var storage=route.storage();
            if(storage==null)return emit(source,List.of("Held item: no compatible energy capability"));
            return emit(source,List.of("Held item: "+stack.getHoverName().getString()+"; selected route "+route.id(),
                    "Base-equivalent storage: "+storage.getEnergyStored()+" / "+storage.getMaxEnergyStored()+" FE",
                    "Can charge: "+storage.canReceive()+"; can discharge: "+storage.canExtract()+". Reports do not mutate the item."));
        }));
        event.getDispatcher().register(Commands.literal("foundations").then(power));
    }
    private PowerDiagnostics() {}
}
