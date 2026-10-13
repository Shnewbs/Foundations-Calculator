package com.foundations.calculator.client;
import com.foundations.calculator.FoundationsCalculator;
import com.foundations.calculator.content.Content;
import com.foundations.calculator.core.ClientConfig;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
@EventBusSubscriber(modid=FoundationsCalculator.ID,value=Dist.CLIENT)
public final class ToolModels {
    public record Enabled() implements ConditionalItemModelProperty {
        public static final MapCodec<Enabled> CODEC=MapCodec.unit(new Enabled());
        public boolean get(ItemStack stack,ClientLevel level,LivingEntity entity,int seed,ItemDisplayContext context){return ClientConfig.flag(ClientConfig.TOOL_MODELS);}
        public MapCodec<Enabled> type(){return CODEC;}
    }
    @SubscribeEvent public static void property(net.neoforged.neoforge.client.event.RegisterConditionalItemModelPropertyEvent event){event.register(Content.id("tool_models"),Enabled.CODEC);}
    private ToolModels() {}
}
