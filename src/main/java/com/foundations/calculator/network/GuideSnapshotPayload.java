package com.foundations.calculator.network;
import java.util.*;
import com.foundations.calculator.content.Content;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record GuideSnapshotPayload(long token,String key,boolean available,List<String> lines) implements CustomPacketPayload {
    public GuideSnapshotPayload{if(key.length()>240||lines.size()>96)throw new IllegalArgumentException("Guide snapshot limits");lines=List.copyOf(lines);int total=0;for(String line:lines){if(line.length()>512)throw new IllegalArgumentException("Guide line limit");total+=line.length();}if(total>24576)throw new IllegalArgumentException("Guide snapshot size");}
    public static final Type<GuideSnapshotPayload> TYPE=new Type<>(Content.id("guide_snapshot"));
    public static final StreamCodec<RegistryFriendlyByteBuf,GuideSnapshotPayload> CODEC=StreamCodec.of((b,p)->{
        b.writeLong(p.token);b.writeUtf(p.key,240);b.writeBoolean(p.available);b.writeVarInt(p.lines.size());for(String s:p.lines)b.writeUtf(s,512);
    },b->{long token=b.readLong();String key=b.readUtf(240);boolean available=b.readBoolean();int n=b.readVarInt();if(n<0||n>96)throw new IllegalArgumentException("Guide line count");List<String> lines=new ArrayList<>();int total=0;for(int i=0;i<n;i++){String s=b.readUtf(512);total+=s.length();if(total>24576)throw new IllegalArgumentException("Guide response size");lines.add(s);}return new GuideSnapshotPayload(token,key,available,lines);});
    public Type<GuideSnapshotPayload> type(){return TYPE;}
    public static void handle(GuideSnapshotPayload p,IPayloadContext context){context.enqueueWork(()->com.foundations.calculator.client.guide.GuideValues.receive(p));}
}
