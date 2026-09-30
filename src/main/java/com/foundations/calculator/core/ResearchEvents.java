package com.foundations.calculator.core;
import com.foundations.calculator.FoundationsCalculator;
import com.foundations.calculator.content.Content;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
@EventBusSubscriber(modid=FoundationsCalculator.ID)
public final class ResearchEvents {
    @SubscribeEvent public static void login(PlayerEvent.PlayerLoggedInEvent e){if(e.getEntity() instanceof ServerPlayer p)ResearchData.sync(p);}
    @SubscribeEvent public static void commands(RegisterCommandsEvent e){
        var root=Commands.literal("foundations");var research=Commands.literal("research").executes(ctx->{var p=ctx.getSource().getPlayerOrException();ctx.getSource().sendSuccess(()->Component.literal("Research: "+String.join(", ",ResearchData.get(p.server).groups(p.getUUID()))),false);return 1;});
        for(boolean grant:new boolean[]{true,false})research.then(Commands.literal(grant?"grant":"revoke").requires(source->source.hasPermission(2)).then(Commands.argument("player",EntityArgument.player()).then(Commands.argument("group",StringArgumentType.word()).suggests((ctx,builder)->{ctx.getSource().getServer().getRecipeManager().getAllRecipesFor(Content.PROCESS_TYPE.get()).stream().map(r->r.value().researchGroup()).filter(s->!s.isEmpty()).distinct().sorted().forEach(builder::suggest);return builder.buildFuture();}).executes(ctx->{var p=EntityArgument.getPlayer(ctx,"player");String group=StringArgumentType.getString(ctx,"group");var data=ResearchData.get(p.server);boolean changed=grant?data.unlock(p.getUUID(),group):data.revoke(p.getUUID(),group);ResearchData.syncAll(p.server);ctx.getSource().sendSuccess(()->Component.literal((grant?"Granted ":"Revoked ")+group+" for "+p.getName().getString()),true);return changed?1:0;}))));
        e.getDispatcher().register(root.then(research));
    }
    @SubscribeEvent public static void tick(net.neoforged.neoforge.event.tick.ServerTickEvent.Post e){if(e.getServer().getTickCount()%100==0)ResearchData.syncAll(e.getServer());}
    private ResearchEvents(){}
}
