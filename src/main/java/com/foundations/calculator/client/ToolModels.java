package com.foundations.calculator.client;
import com.foundations.calculator.FoundationsCalculator;
import com.foundations.calculator.content.Content;
import com.foundations.calculator.core.ClientConfig;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ModelEvent;
@EventBusSubscriber(modid=FoundationsCalculator.ID,bus=EventBusSubscriber.Bus.MOD,value=Dist.CLIENT)
public final class ToolModels {
    private static boolean tool(String id){return id.matches(".+_(sword|pickaxe|axe|shovel|hoe)");}
    @SubscribeEvent public static void additional(ModelEvent.RegisterAdditional e){for(String id:Content.ITEMS_BY_ID.keySet())if(tool(id))e.register(ModelResourceLocation.standalone(Content.id("flat_tools/"+id)));}
    @SubscribeEvent public static void baked(ModelEvent.ModifyBakingResult e){if(ClientConfig.flag(ClientConfig.TOOL_MODELS))return;for(String id:Content.ITEMS_BY_ID.keySet())if(tool(id)){var flat=e.getModels().get(ModelResourceLocation.standalone(Content.id("flat_tools/"+id)));if(flat!=null)e.getModels().put(ModelResourceLocation.inventory(Content.id(id)),flat);}}
    private ToolModels(){}
}
