package com.foundations.calculator.client;

import com.foundations.calculator.menu.CalculatorMenu;
import com.foundations.calculator.core.*;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import com.foundations.calculator.core.ClientConfig;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public final class CalculatorScreen extends AbstractContainerScreen<CalculatorMenu>{
    private boolean sidesOpen;
    private final java.util.Map<Integer,Button> controls=new java.util.HashMap<>();
    public CalculatorScreen(CalculatorMenu menu,Inventory inv,Component title){super(menu,inv,title);imageWidth=232;imageHeight=238;inventoryLabelX=35;inventoryLabelY=145;titleLabelX=12;titleLabelY=22;}
    protected void init(){super.init();controls.clear();
        if(menu.machine==null){if(menu.kind.equals("dynamic_module")){button(0,"Basic",12,126,66);button(1,"Scientific",82,126,66);button(2,"Atomic",152,126,68);}else button(0,"Calculate",142,97,76);return;}
        if(menu.machine.usesEnergy() && CalculatorConfig.flag("power.diagnostics.enabled",true)){
            int width=font.width("Power")+12;
            Button power=Button.builder(Component.literal("Power"),b->minecraft.gameMode.handleInventoryButtonClick(menu.containerId,27))
                    .bounds(leftPos+imageWidth-12-width,topPos+4,width,13).build();
            controls.put(27,power);addRenderableWidget(power);
        }
        button(26,"R: A",185,99,35);
        if(menu.kind.endsWith("greenhouse")){
            button(10,"Pause / Resume",12,126,84);
            if(!menu.kind.equals("flawless_greenhouse")){button(11,"Build",100,126,52);button(12,"Demolish",156,126,64);}
        }else if(menu.kind.equals("weather_controller")){button(10,"Mode",12,126,100);button(11,"Target",116,126,104);}
        else if(menu.kind.equals("magnetic_flux")){button(10,"Blacklist",12,126,100);button(11,"Components",116,126,104);}
        else if(menu.machine.usesEnergy()){
            for(int side=0;side<6;side++)button(20+side,"",12+side*29,126,27);
            Button toggle=Button.builder(Component.literal("Sides"),b->{sidesOpen=!sidesOpen;updateSides();}).bounds(leftPos+185,topPos+126,35,16).build();
            controls.put(100,toggle);addRenderableWidget(toggle);updateSides();
        }
    }
    private void updateSides(){for(int i=20;i<26;i++)if(controls.containsKey(i))controls.get(i).visible=sidesOpen;if(controls.containsKey(100))controls.get(100).setMessage(Component.literal(sidesOpen?"Back":"Sides"));}
    private void button(int id,String label,int x,int y,int w){Button b=Button.builder(Component.literal(label),v->minecraft.gameMode.handleInventoryButtonClick(menu.containerId,id)).bounds(leftPos+x,topPos+y,w,id==0?20:16).build();controls.put(id,b);addRenderableWidget(b);}
    protected void containerTick(){super.containerTick();
        if(menu.kind.equals("weather_controller")){controls.get(10).setMessage(Component.literal(new String[]{"Time","Rain","Thunder"}[Math.floorMod(menu.state(13),3)]));controls.get(11).setMessage(Component.literal(menu.state(13)==0?(menu.state(14)==0?"Day":"Night"):(menu.state(14)==0?"Off":"On")));}
        if(menu.kind.equals("magnetic_flux")){controls.get(10).setMessage(Component.literal(menu.state(16)==0?"Blacklist":"Whitelist"));controls.get(11).setMessage(Component.literal(menu.state(17)==0?"Components":"Item / tags"));}
        if(controls.containsKey(26))controls.get(26).setMessage(Component.literal(new String[]{"R: A","R: +","R: −","R: ×"}[Math.floorMod(menu.state(27),4)]));
        for(int side=0;side<6;side++)if(controls.containsKey(20+side))controls.get(20+side).setMessage(Component.literal(new String[]{"D","U","N","S","W","E"}[side]+new String[]{"A","←","→","×"}[Math.floorMod(menu.state(18+side),4)]));
        if(menu.kind.endsWith("greenhouse"))controls.get(10).setMessage(Component.literal(menu.state(15)==0?"Pause":"Resume"));
    }
    protected void renderBg(GuiGraphicsExtractor g,float partial,int x,int y){
        g.fill(leftPos-1,topPos-1,leftPos+imageWidth+1,topPos+imageHeight+1,ClientConfig.color(ClientConfig.ACCENT,0x47acb9));
        g.fill(leftPos,topPos,leftPos+imageWidth,topPos+imageHeight,ClientConfig.color(ClientConfig.BACKGROUND,0x18242d));
        g.fill(leftPos+4,topPos+4,leftPos+imageWidth-4,topPos+17,ClientConfig.color(ClientConfig.PANEL,0x30424e));
        g.drawString(font,"FOUNDATIONS CALCULATOR",leftPos+12,topPos+7,ClientConfig.color(ClientConfig.TEXT,0xa5e6ec),false);
        for(var slot:menu.slots)if(slot.isActive()){
            int sx=leftPos+slot.x,sy=topPos+slot.y;g.fill(sx-1,sy-1,sx+17,sy+17,0xff728896);g.fill(sx,sy,sx+16,sy+16,ClientConfig.color(ClientConfig.SLOT,0x101820));
        }
        String inputLabel=menu.kind.equals("module_workstation")?"Modules":MachineProfiles.storage(menu.kind)?"Discharge":"Inputs";
        if(menu.inputs>0)g.drawString(font,inputLabel,leftPos+16,topPos+34,ClientConfig.color(ClientConfig.MUTED,0xa9bbc8),false);
        if(menu.machine==null||menu.machine.hasOutputs())g.drawString(font,"Outputs",leftPos+160,topPos+34,ClientConfig.color(ClientConfig.MUTED,0xa9bbc8),false);
        if(menu.machine!=null&&menu.machine.hasBatterySlot()&&!menu.kind.endsWith("greenhouse")){
            String label=menu.kind.equals("module_workstation")||menu.kind.equals("docking_station")?"Calculator":menu.kind.endsWith("_processor")||menu.kind.equals("stone_assimilator")?"Module":menu.machine.chargesItems()?"Charge":"Power";
            g.drawString(font,label,leftPos+12,topPos+87,ClientConfig.color(ClientConfig.MUTED,0xa9bbc8),false);
            if(menu.machine.supportsUpgrades())g.drawString(font,"Upgrades",leftPos+88,topPos+87,ClientConfig.color(ClientConfig.MUTED,0xa9bbc8),false);
        }
        g.fill(leftPos+16,topPos+82,leftPos+214,topPos+84,ClientConfig.color(ClientConfig.SLOT,0x101820));
        g.fill(leftPos+16,topPos+82,leftPos+16+(int)(198*Math.min(1,menu.progress())),topPos+84,ClientConfig.color(ClientConfig.PROGRESS,0x45cbd3));
        if(menu.machine==null||menu.machine.usesEnergy()||menu.kind.endsWith("_processor"))g.drawString(font,menu.kind.endsWith("_processor")?String.format("Stored: %,d points",menu.points()):EnergyDisplay.formatPreferred(menu.energyLong(),menu.capacityLong()),leftPos+16,topPos+116,ClientConfig.color(ClientConfig.TEXT,0x7ee3ce),false);
        if(!menu.kind.endsWith("greenhouse")&&!sidesOpen){
            int statusY=statusY();
            String status=statusText();
            int maxWidth=controls.containsKey(100)?168:208;
            g.drawString(font,font.plainSubstrByWidth(status,maxWidth),leftPos+12,topPos+statusY,statusColor(),false);
        }
    }
    private int statusY(){return menu.kind.endsWith("greenhouse")?85:menu.kind.equals("weather_controller")?64:menu.kind.equals("dynamic_module")||menu.kind.equals("magnetic_flux")?88:130;}
    private String statusText(){
        if(menu.status()==MachineStatus.NEED_POWER&&menu.requiredEnergy()>0)return String.format("Needs %,d FE",menu.requiredEnergy());
        if(menu.status()==MachineStatus.RUNNING&&menu.progress()>0)return "Processing · "+(int)(Math.clamp(menu.progress(),0,1)*100)+"%";
        return menu.status().label;
    }
    private int statusColor(){return switch(menu.status()){case NEED_POWER,NO_RECIPE,OUTPUT_BLOCKED,INCOMPLETE,NEED_FUEL,NEED_INPUT,NEED_FEED,NEED_REDSTONE->0xffffc66b;default->ClientConfig.color(ClientConfig.MUTED,0xa9bbc8);};}
    protected void renderLabels(GuiGraphicsExtractor g,int x,int y){
        g.drawString(font,title,titleLabelX,titleLabelY,ClientConfig.color(ClientConfig.TEXT,0xedf4f8),false);
        if(menu.kind.endsWith("greenhouse")){
            String text=statusText()+" · CO₂ "+(((menu.state(11)&65535)|((menu.state(12)&65535)<<16))/1000)+"%";
            g.drawString(font,font.plainSubstrByWidth(text,208),12,85,statusColor(),false);
        }
        g.drawString(font,playerInventoryTitle,inventoryLabelX,inventoryLabelY,ClientConfig.color(ClientConfig.MUTED,0xb4c6cf),false);
    }
    public void render(GuiGraphicsExtractor g,int x,int y,float partial){
        super.render(g,x,y,partial);renderTooltip(g,x,y);
        if(!sidesOpen&&x>=leftPos+12&&x<=leftPos+220&&y>=topPos+statusY()&&y<=topPos+statusY()+10){
            String detail=menu.status().label+(menu.requiredEnergy()>0?String.format(" — requires %,d FE",menu.requiredEnergy()):"");
            if(!isOverControl(x,y))g.renderTooltip(font,font.split(Component.literal(detail),210),x,y);
        }
        if(controls.containsKey(27)&&controls.get(27).isMouseOver(x,y))g.renderTooltip(font,font.split(Component.literal("Server power report in chat: sides, limits and blocked transfers. Closes this screen."),210),x,y);
        if(controls.containsKey(26)&&controls.get(26).isMouseOver(x,y))g.renderTooltip(font,Component.literal(new String[]{"Redstone: always active","Redstone: signal required","Redstone: signal disables","Redstone: paused"}[Math.floorMod(menu.state(27),4)]),x,y);
        if(sidesOpen&&x>=leftPos+12&&x<leftPos+186&&y>=topPos+126&&y<topPos+142)g.renderTooltip(font,font.split(Component.literal("A: automatic machine role; ←: input; →: output; ×: disabled"),210),x,y);
        if(ClientConfig.flag(ClientConfig.SLOT_HINTS)&&hoveredSlot!=null&&hoveredSlot.getItem().isEmpty()&&hoveredSlot.index<25){
            String help=slotHint(hoveredSlot.index);if(!help.isEmpty())g.renderTooltip(font,font.split(Component.literal(help),210),x,y);
        }
    }
    private boolean isOverControl(int x,int y){for(Button b:controls.values())if(b.visible&&b.isMouseOver(x,y))return true;return false;}
    private String slotHint(int slot){
        if(slot==0){String circuit=switch(menu.kind){
            case "processing_chamber"->"Dirty or damaged circuits. Default cycle: 1,000 FE, 25 seconds.";
            case "restoration_chamber"->"Dirty circuits. Default cycle: 1,000 FE, 50 seconds.";
            case "reassembly_chamber"->"Damaged circuits. Default cycle: 1,000 FE, 50 seconds.";
            case "analysing_chamber"->"Clean circuit boards that have not been analysed yet.";
            case "extraction_chamber"->"Dirt or cobblestone; this chamber produces raw circuits.";
            default->"";
        };if(!circuit.isEmpty())return circuit;}
        if(slot==20&&menu.machine!=null){
            if(menu.kind.equals("module_workstation"))return "Insert a Flawless Calculator; shift-click also works.";
            if(menu.kind.equals("docking_station"))return "Install a calculator to select its recipes. Use its stored FE or connect an external power source.";
            if(menu.kind.endsWith("_processor")||menu.kind.equals("stone_assimilator"))return "Insert a health, hunger or nutrition module.";
            return menu.machine.chargesItems()?"Charge a calculator or energy item here.":"Power: redstone (1,000 FE), coal/charcoal (500 FE), their blocks, or a charged energy item.";
        }
        if(MachineProfiles.storage(menu.kind)&&slot==0)return "Discharge: redstone (1,000 FE), coal/charcoal (500 FE), their blocks, or a charged energy item.";
        if(slot>=21&&slot<25&&menu.machine!=null&&menu.machine.supportsUpgrades())return "Upgrade slot: Speed shortens processing time; Energy lowers FE per cycle; Transfer exports output items; Void discards output only when normal output remains blocked.";
        if(menu.kind.endsWith("greenhouse")&&slot<14){if(!menu.kind.equals("flawless_greenhouse")&&slot<4)return new String[]{"Building material: logs","Building material: wooden stairs","Building material: glass","Building material: planks"}[slot];return "Seeds supported by this greenhouse tier.";}
        if(menu.kind.endsWith("extractor")&&slot<2)return slot==0?"Furnace fuel, such as coal.":"Extractor ingredient: starch food, redstone, or glowstone for this machine.";
        return "";
    }
}
