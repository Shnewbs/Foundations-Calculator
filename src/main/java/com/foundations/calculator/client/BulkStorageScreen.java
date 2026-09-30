package com.foundations.calculator.client;
import com.foundations.calculator.menu.BulkStorageMenu;
import net.minecraft.client.gui.GuiGraphics;
import com.foundations.calculator.core.ClientConfig;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
public final class BulkStorageScreen extends AbstractContainerScreen<BulkStorageMenu>{
    public BulkStorageScreen(BulkStorageMenu menu,Inventory inv,Component title){super(menu,inv,title);imageWidth=248;imageHeight=236;titleLabelX=12;titleLabelY=22;inventoryLabelX=35;inventoryLabelY=141;}
    protected void renderBg(GuiGraphics g,float partial,int x,int y){
        g.fill(leftPos-1,topPos-1,leftPos+249,topPos+237,ClientConfig.color(ClientConfig.ACCENT,0x47acb9));g.fill(leftPos,topPos,leftPos+248,topPos+236,ClientConfig.color(ClientConfig.BACKGROUND,0x18242d));g.drawString(font,"FOUNDATIONS CALCULATOR",leftPos+12,topPos+7,ClientConfig.color(ClientConfig.TEXT,0xa5e6ec),false);
        for(var slot:menu.slots){int sx=leftPos+slot.x,sy=topPos+slot.y;g.fill(sx-1,sy-1,sx+17,sy+17,0xff728896);g.fill(sx,sy,sx+16,sy+16,ClientConfig.color(ClientConfig.SLOT,0x101820));}
        for(int i=0;i<menu.size;i++)if(menu.count(i)>0)g.drawString(font,Integer.toString(menu.count(i)),leftPos+menu.slots.get(i).x,topPos+menu.slots.get(i).y+18,ClientConfig.color(ClientConfig.TEXT,0x7ee3ce),false);
        g.drawString(font,menu.size==14?"14 circuit bins · 1,024 each":"27 storage bins · "+menu.machine.bulk.getSlotLimit(0)+" each",leftPos+16,topPos+130,ClientConfig.color(ClientConfig.MUTED,0xa9bbc8),false);
    }
    protected void renderLabels(GuiGraphics g,int x,int y){g.drawString(font,title,titleLabelX,titleLabelY,ClientConfig.color(ClientConfig.TEXT,0xedf4f8),false);g.drawString(font,playerInventoryTitle,inventoryLabelX,inventoryLabelY,ClientConfig.color(ClientConfig.MUTED,0xb4c6cf),false);}
    public void render(GuiGraphics g,int x,int y,float partial){super.render(g,x,y,partial);renderTooltip(g,x,y);}
}
