package com.foundations.calculator.core;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import com.foundations.calculator.FoundationsCalculator;
import com.foundations.calculator.network.RecipeSnapshotPayload;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.network.PacketDistributor;
@EventBusSubscriber(modid=FoundationsCalculator.ID)
public final class RecipeSnapshots {
    private static final AtomicLong EPOCH=new AtomicLong();
    @SubscribeEvent(priority=net.neoforged.bus.api.EventPriority.LOW) public static void sync(OnDatapackSyncEvent event){
        List<RecipeHolder<?>> recipes=List.copyOf(event.getPlayerList().getServer().getRecipeManager().getRecipes());int chunks=Math.max(1,(recipes.size()+SnapshotAssembly.CHUNK_SIZE-1)/SnapshotAssembly.CHUNK_SIZE);
        if(chunks>SnapshotAssembly.MAX_CHUNKS)throw new IllegalStateException("Recipe snapshot exceeds supported recipe count");
        long epoch=EPOCH.incrementAndGet();var players=event.getPlayer()==null?event.getPlayerList().getPlayers():List.of(event.getPlayer());
        for(var player:players)for(int i=0;i<chunks;i++){int from=i*SnapshotAssembly.CHUNK_SIZE,to=Math.min(recipes.size(),from+SnapshotAssembly.CHUNK_SIZE);PacketDistributor.sendToPlayer(player,new RecipeSnapshotPayload(epoch,i,chunks,recipes.subList(from,to)));}
    }
    private RecipeSnapshots() {}
}
