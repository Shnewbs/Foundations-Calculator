package com.foundations.calculator.network;

import com.foundations.calculator.content.Content;
import com.foundations.calculator.core.*;
import com.foundations.calculator.menu.CalculatorMenu;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.*;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record TransferPayload(int menuId,ResourceLocation recipe,int amount) implements CustomPacketPayload {
    public static final Type<TransferPayload> TYPE=new Type<>(Content.id("recipe_transfer"));
    public static final StreamCodec<RegistryFriendlyByteBuf,TransferPayload> CODEC=StreamCodec.composite(ByteBufCodecs.VAR_INT,TransferPayload::menuId,ResourceLocation.STREAM_CODEC,TransferPayload::recipe,ByteBufCodecs.VAR_INT,TransferPayload::amount,TransferPayload::new);
    public Type<TransferPayload> type(){return TYPE;}
    public static void handle(TransferPayload payload,IPayloadContext context){
        context.enqueueWork(()->{
            var player=context.player();if(!(player.containerMenu instanceof CalculatorMenu menu)||menu.containerId!=payload.menuId()||!menu.stillValid(player))return;
            var r=player.level().getRecipeManager().byKey(payload.recipe());
            if(r.isEmpty()||!(r.get().value() instanceof ProcessRecipe process))return;
            var holder=RecipePolicies.apply(new net.minecraft.world.item.crafting.RecipeHolder<>(payload.recipe(),process));
            if(!RecipeTransfer.transfer(menu,holder,player,payload.amount()))player.displayClientMessage(Component.literal("Cannot fill recipe: check research, ingredients, and free inventory space."),true);
        });
    }
}
