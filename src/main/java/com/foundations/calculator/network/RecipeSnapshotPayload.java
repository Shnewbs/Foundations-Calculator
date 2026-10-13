package com.foundations.calculator.network;
import java.util.*;
import com.foundations.calculator.content.Content;
import com.foundations.calculator.core.*;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.neoforge.network.handling.IPayloadContext;
/** Bounded recipe chunks become visible together when their snapshot is complete. */
public record RecipeSnapshotPayload(long epoch,int index,int chunks,List<RecipeHolder<?>> recipes) implements CustomPacketPayload {
    public static final Type<RecipeSnapshotPayload> TYPE=new Type<>(Content.id("recipe_snapshot"));
    private static final SnapshotAssembly<RecipeHolder<?>> CLIENT=new SnapshotAssembly<>();
    public static final StreamCodec<RegistryFriendlyByteBuf,RecipeSnapshotPayload> CODEC=StreamCodec.of((buf,p)->{
        SnapshotAssembly.validate(p.epoch,p.index,p.chunks,p.recipes.size());buf.writeVarLong(p.epoch);buf.writeVarInt(p.index);buf.writeVarInt(p.chunks);buf.writeVarInt(p.recipes.size());for(var recipe:p.recipes)RecipeHolder.STREAM_CODEC.encode(buf,recipe);
    },buf->{long epoch=buf.readVarLong();int index=buf.readVarInt(),chunks=buf.readVarInt(),size=buf.readVarInt();SnapshotAssembly.validate(epoch,index,chunks,size);List<RecipeHolder<?>> recipes=new ArrayList<>(size);for(int i=0;i<size;i++)recipes.add(RecipeHolder.STREAM_CODEC.decode(buf));return new RecipeSnapshotPayload(epoch,index,chunks,List.copyOf(recipes));});
    public Type<RecipeSnapshotPayload> type(){return TYPE;}
    public static void clear(){CLIENT.clear();NativeRecipes.clearClient();}
    public static void handle(RecipeSnapshotPayload payload,IPayloadContext context){context.enqueueWork(()->CLIENT.accept(payload.epoch,payload.index,payload.chunks,payload.recipes).ifPresent(NativeRecipes::replaceClient));}
}
