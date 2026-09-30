package com.foundations.calculator.client;
import java.util.*;
import com.foundations.calculator.content.Content;
import com.foundations.calculator.core.*;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.RecipeHolder;
public final class ResearchScreen extends Screen {
    private final Screen parent;private int page;private boolean mastery;
    public ResearchScreen(Screen parent){super(Component.literal("Foundations · Research"));this.parent=parent;}
    protected void init(){addRenderableWidget(Button.builder(Component.literal("Recipes / Mastery"),b->mastery=!mastery).bounds(width-200,8,126,20).build());addRenderableWidget(Button.builder(Component.literal("Back"),b->onClose()).bounds(width-68,8,56,20).build());addRenderableWidget(Button.builder(Component.literal("Previous"),b->page=Math.max(0,page-1)).bounds(width/2-100,height-30,94,20).build());addRenderableWidget(Button.builder(Component.literal("Next"),b->page++).bounds(width/2+6,height-30,94,20).build());}
    public void render(GuiGraphics g,int x,int y,float delta){
        super.render(g,x,y,delta);g.fill(8,34,width-8,height-36,0xee18242d);g.drawString(font,title,14,14,0xffa5e6ec,false);
        g.drawWordWrap(font,Component.literal("Study one of the shown samples in a Research Chamber to unlock its calculator recipes. Unlocks are saved on the server."),18,44,width-36,0xffc4d5df);
        if(minecraft.level==null)return;
        if(mastery){int i=0;for(String family:ResearchData.FAMILIES){long count=ResearchData.clientMastery.getOrDefault(family,0L);int target=ResearchData.target(family);int top=90+i++*34;g.drawString(font,family+": "+count+" / "+target+" ("+(int)Math.min(100,100.0*count/target)+"%)",20,top,0xff7ee3ce,false);g.fill(20,top+14,width-20,top+18,0xff30424e);g.fill(20,top+14,20+(int)((width-40)*Math.min(1,(double)count/target)),top+18,0xff45cbd3);}return;}
        var recipes=RecipeIndex.allForMachine(minecraft.level,"research");int rows=Math.max(1,(height-135)/30);page=Math.clamp(page,0,Math.max(0,(recipes.size()-1)/rows));
        for(int i=0;i<rows&&page*rows+i<recipes.size();i++){
            var recipe=recipes.get(page*rows+i).value();int top=86+i*30;boolean known=ResearchData.clientGroups.contains(recipe.researchGroup());
            g.drawString(font,(known?"✓ ":"○ ")+recipe.researchGroup().replace('_',' '),20,top+4,known?0xff7ee3ce:0xffc4d5df,false);
            var samples=recipe.inputs().getFirst().ingredient().getItems();if(samples.length>0){var stack=samples[(int)(System.currentTimeMillis()/1000%samples.length)];g.renderItem(stack,width-52,top);if(x>=width-52&&x<width-34&&y>=top&&y<top+18)g.renderTooltip(font,stack,x,y);}
        }
    }
    public void onClose(){minecraft.setScreen(parent);}public boolean isPauseScreen(){return false;}
}
