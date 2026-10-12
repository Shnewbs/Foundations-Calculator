package com.foundations.calculator.client;
import com.foundations.calculator.menu.SmeltingModuleMenu;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import com.foundations.calculator.core.ClientConfig;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
public final class SmeltingModuleScreen extends AbstractContainerScreen<SmeltingModuleMenu>{
    public SmeltingModuleScreen(SmeltingModuleMenu menu,Inventory inv,Component title){super(menu,inv,title,176,178);inventoryLabelY=84;}
    public void extractBackground(GuiGraphicsExtractor g,int mx,int my,float partial){
        g.fill(leftPos-1,topPos-1,leftPos+177,topPos+179,ClientConfig.color(ClientConfig.ACCENT,0x47acb9));g.fill(leftPos,topPos,leftPos+176,topPos+178,ClientConfig.color(ClientConfig.BACKGROUND,0x18242d));
        for(var slot:menu.slots){int x=leftPos+slot.x,y=topPos+slot.y;g.fill(x-1,y-1,x+17,y+17,0xff728896);g.fill(x,y,x+16,y+16,ClientConfig.color(ClientConfig.SLOT,0x101820));}
        g.text(font,"Input",leftPos+26,topPos+24,ClientConfig.color(ClientConfig.MUTED,0xb4c6cf),false);g.text(font,"Output",leftPos+126,topPos+24,ClientConfig.color(ClientConfig.MUTED,0xb4c6cf),false);
        g.fill(leftPos+56,topPos+41,leftPos+124,topPos+46,ClientConfig.color(ClientConfig.SLOT,0x101820));g.fill(leftPos+56,topPos+41,leftPos+56+(int)(68*menu.progress()),topPos+46,ClientConfig.color(ClientConfig.PROGRESS,0x45cbd3));
        g.text(font,com.foundations.calculator.core.EnergyDisplay.formatPreferred(menu.energyLong(), menu.carrier.getItem() instanceof com.foundations.calculator.content.CalculatorItem item ? item.maxEnergyLong(menu.carrier) : menu.energyLong()),leftPos+8,topPos+73,ClientConfig.color(ClientConfig.TEXT,0x7ee3ce),false);g.text(font,"Returns",leftPos+72,topPos+73,ClientConfig.color(ClientConfig.MUTED,0xb4c6cf),false);
    }
    protected void extractLabels(GuiGraphicsExtractor g,int x,int y){g.text(font,title,8,7,ClientConfig.color(ClientConfig.TEXT,0xedf4f8),false);g.text(font,playerInventoryTitle,8,84,ClientConfig.color(ClientConfig.MUTED,0xb4c6cf),false);}
    public void extractRenderState(GuiGraphicsExtractor g,int x,int y,float p){super.extractRenderState(g,x,y,p);extractTooltip(g,x,y);}
}
