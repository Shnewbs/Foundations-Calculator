package com.foundations.calculator.network;
import com.foundations.calculator.content.Content;
import com.foundations.calculator.guide.GuideSnapshots;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/** Only a bounded allow-listed public snapshot key, never a command/config path or coordinates. */
public record GuideQueryPayload(long token,String key) implements CustomPacketPayload {
    public GuideQueryPayload{java.util.Objects.requireNonNull(key);if(key.length()>240)throw new IllegalArgumentException("Guide query too long");}
    public static final Type<GuideQueryPayload> TYPE=new Type<>(Content.id("guide_query"));
    public static final StreamCodec<RegistryFriendlyByteBuf,GuideQueryPayload> CODEC=StreamCodec.of((b,p)->{b.writeLong(p.token);b.writeUtf(p.key,240);},b->new GuideQueryPayload(b.readLong(),b.readUtf(240)));
    public Type<GuideQueryPayload> type(){return TYPE;}
    public static void handle(GuideQueryPayload p,IPayloadContext context){context.enqueueWork(()->{if(context.player() instanceof net.minecraft.server.level.ServerPlayer player)GuideSnapshots.reply(player,p);});}
}
