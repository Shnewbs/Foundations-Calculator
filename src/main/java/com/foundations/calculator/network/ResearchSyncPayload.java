package com.foundations.calculator.network;
import java.util.*;
import com.foundations.calculator.content.Content;
import com.foundations.calculator.core.ResearchData;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;
public record ResearchSyncPayload(List<String> groups,Map<String,Long> mastery) implements CustomPacketPayload {
    public static final Type<ResearchSyncPayload> TYPE=new Type<>(Content.id("research_sync"));
    public static final StreamCodec<RegistryFriendlyByteBuf,ResearchSyncPayload> CODEC=StreamCodec.of((b,p)->{b.writeVarInt(p.groups.size());for(String group:p.groups)b.writeUtf(group,256);for(String family:ResearchData.FAMILIES)b.writeVarLong(p.mastery.getOrDefault(family,0L));},b->{int n=b.readVarInt();if(n<0||n>4096)throw new IllegalArgumentException("Research group count");List<String> groups=new ArrayList<>();for(int i=0;i<n;i++)groups.add(b.readUtf(256));Map<String,Long> mastery=new HashMap<>();for(String family:ResearchData.FAMILIES)mastery.put(family,Math.max(0,b.readVarLong()));return new ResearchSyncPayload(List.copyOf(groups),Map.copyOf(mastery));});
    public Type<ResearchSyncPayload> type(){return TYPE;}
    public static void handle(ResearchSyncPayload payload,IPayloadContext context){context.enqueueWork(()->{ResearchData.clientGroups=Set.copyOf(payload.groups);ResearchData.clientMastery=Map.copyOf(payload.mastery);});}
}
