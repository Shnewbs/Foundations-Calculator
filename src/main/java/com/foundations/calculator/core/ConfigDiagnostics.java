package com.foundations.calculator.core;

import com.foundations.calculator.FoundationsCalculator;
import com.mojang.brigadier.arguments.StringArgumentType;
import java.util.*;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

/** Read-only operator summary for the deliberately large server configuration. */
@EventBusSubscriber(modid=FoundationsCalculator.ID)
public final class ConfigDiagnostics {
    private static int emit(net.minecraft.commands.CommandSourceStack source,String... lines){for(String line:lines)source.sendSuccess(()->Component.literal(line),false);return 1;}
    private static List<String> machines(){return CalculatorConfig.keys().stream().filter(k->k.startsWith("machine.")&&k.endsWith(".enabled"))
            .map(k->k.substring(8,k.length()-8)).distinct().sorted().toList();}
    private static int machine(net.minecraft.commands.CommandSourceStack source,String id){
        if(!machines().contains(id)){source.sendFailure(Component.literal("Unknown Calculator machine: "+id));return 0;}
        long cap=CalculatorConfig.machineCapacity(id,MachineDefinition.forMachine(id).capacity());
        return emit(source,
            "Calculator machine: "+id,
            "Capabilities: inputs="+MachineDefinition.forMachine(id).inputCount()+"; outputs="+MachineDefinition.forMachine(id).outputs()+"; upgrades="+MachineDefinition.forMachine(id).upgrades(),
            "Enabled: "+CalculatorConfig.machineEnabled(id)+"; capacity: "+String.format("%,d",cap)+" FE",
            "Transfer: standard FE "+String.format("%,d",MachineProfiles.transfer(id))+"; native long "+String.format("%,d",MachineProfiles.transferLong(id))+" FE/t; item charge: "+String.format("%,d",MachineProfiles.charging(id))+" FE/t",
            "Automation: "+CalculatorConfig.machineFlag(id,"itemAutomation")+"; FE input/output: "+CalculatorConfig.machineFlag(id,"energyInput")+"/"+CalculatorConfig.machineFlag(id,"energyOutput"));
    }
    @SubscribeEvent public static void commands(RegisterCommandsEvent event){
        var config=Commands.literal("config").requires(s->s.hasPermission(2));
        config.executes(ctx->emit(ctx.getSource(),
            "Foundations Calculator config: "+CalculatorConfig.keys().size()+" server keys",
            "Balance preset: "+CalculatorConfig.presetName()+" (applyPreset="+CalculatorConfig.flag("balance.applyPreset",false)+")",
            "Capacity multipliers — machine "+CalculatorConfig.decimal("energy.machineCapacityMultiplier",1)+"x, item "+CalculatorConfig.decimal("energy.itemCapacityMultiplier",1)+"x",
            "Transfer multiplier: "+CalculatorConfig.decimal("energy.transferMultiplier",1)+"x; long-energy API: "+CalculatorConfig.flag("api.longEnergy.enabled",true),
            "Use /foundations config presets or /foundations config machine <id>."));
        config.then(Commands.literal("presets").executes(ctx->emit(ctx.getSource(),
            "Presets are opt-in: set balance.applyPreset=true and balance.preset to 1–4.",
            "0 Custom/R9 — explicit settings only",
            "1 Classic — R9 timing/capacity/energy behavior",
            "2 Balanced — 2x capacity/transfer, 0.85x process energy, 0.90x time",
            "3 Expert — 0.75x capacity/transfer, 1.50x process energy, 1.15x time",
            "4 High Power — 16x capacity, 8x transfer, 0.75x time")));
        config.then(Commands.literal("machine").then(Commands.argument("id",StringArgumentType.word())
            .suggests((ctx,b)->{machines().forEach(b::suggest);return b.buildFuture();})
            .executes(ctx->machine(ctx.getSource(),StringArgumentType.getString(ctx,"id")))));
        config.then(Commands.literal("get").then(Commands.argument("key",StringArgumentType.greedyString())
            .suggests((ctx,b)->{CalculatorConfig.keys().stream().filter(k->k.startsWith(b.getRemaining())).limit(50).forEach(b::suggest);return b.buildFuture();})
            .executes(ctx->{
                String key=StringArgumentType.getString(ctx,"key");
                var value=CalculatorConfig.configuredValue(key);
                if(value.isEmpty()){ctx.getSource().sendFailure(Component.literal("Unknown Calculator setting: "+key));return 0;}
                return emit(ctx.getSource(),key+" = "+value.get(),"Configured value. Use /foundations config machine <id> for derived machine totals.");
            })));
        config.then(Commands.literal("find").then(Commands.argument("text",StringArgumentType.greedyString())
            .executes(ctx->{
                String text=StringArgumentType.getString(ctx,"text").toLowerCase(Locale.ROOT);
                var matches=CalculatorConfig.keys().stream().filter(k->k.toLowerCase(Locale.ROOT).contains(text)).sorted().toList();
                if(matches.isEmpty())return emit(ctx.getSource(),"No Calculator settings match: "+text);
                emit(ctx.getSource(),"Matching settings: "+matches.size()+" (showing up to 20). Use /foundations config get <key>.");
                for(String key:matches.stream().limit(20).toList())emit(ctx.getSource(),key);
                return 1;
            })));
        event.getDispatcher().register(Commands.literal("foundations").then(config));
    }
    private ConfigDiagnostics(){}
}
