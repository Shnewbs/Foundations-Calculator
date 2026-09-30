package com.foundations.calculator.core;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class ClientConfig {
    public enum Theme {FOUNDATIONS,CLASSIC}
    public enum EnergyUnit {AUTO,FE,GT_EU,MEKANISM_J,MI_EU}
    public static final ModConfigSpec SPEC;
    public static final ModConfigSpec.EnumValue<Theme> THEME;
    public static final ModConfigSpec.EnumValue<EnergyUnit> ENERGY_UNIT;
    public static final ModConfigSpec.BooleanValue ANIMATIONS,CHEST_ANIMATION,TOOL_MODELS,DISPLAY_ITEMS,SLOT_HINTS,ENERGY_TOOLTIPS,INSTRUCTIONS,GUIDE_REDIRECT;
    public static final ModConfigSpec.DoubleValue ANIMATION_SPEED,CHEST_SPEED;
    public static final ModConfigSpec.IntValue RENDER_DISTANCE,ACCENT,BACKGROUND,PANEL,TEXT,MUTED,SLOT,PROGRESS;
    static {
        var b=new ModConfigSpec.Builder();b.push("appearance");
        THEME=b.comment("FOUNDATIONS uses the palette below. CLASSIC uses the original Minecraft-style grey palette.").defineEnum("theme",Theme.FOUNDATIONS);
        ANIMATIONS=b.define("machineAnimations",true);CHEST_ANIMATION=b.define("chestLidAnimation",true);
        TOOL_MODELS=b.comment("Use the original 3D tools. Reload resources with F3+T after changing.").define("threeDimensionalTools",true);
        DISPLAY_ITEMS=b.define("displayItemsInMachines",true);
        ANIMATION_SPEED=b.defineInRange("animationSpeed",1.0,0,10);CHEST_SPEED=b.defineInRange("chestLidSpeed",.1,.01,1);
        RENDER_DISTANCE=b.defineInRange("machineRenderDistance",64,16,256);
        ACCENT=b.comment("Palette colors are decimal RGB integers, 0–16777215.").defineInRange("accent",0x47acb9,0,0xffffff);
        BACKGROUND=b.defineInRange("background",0x18242d,0,0xffffff);PANEL=b.defineInRange("panel",0x30424e,0,0xffffff);TEXT=b.defineInRange("text",0xedf4f8,0,0xffffff);MUTED=b.defineInRange("mutedText",0xa9bbc8,0,0xffffff);SLOT=b.defineInRange("slotBackground",0x101820,0,0xffffff);PROGRESS=b.defineInRange("progress",0x45cbd3,0,0xffffff);b.pop();
        b.push("information");SLOT_HINTS=b.define("emptySlotHints",true);ENERGY_TOOLTIPS=b.define("energyTooltips",true);INSTRUCTIONS=b.define("itemInstructions",true);ENERGY_UNIT=b.comment("Energy display: AUTO/FE or an explicit native unit. Inherited ratios fall back to FE rather than guessing.").defineEnum("energyUnit",EnergyUnit.AUTO);b.pop();b.push("guide");GUIDE_REDIRECT=b.comment("Use a compatible master reader only after it accepts the guide; otherwise keep the standalone book.").define("preferMasterReader",true);b.pop();SPEC=b.build();
    }
    public static boolean flag(ModConfigSpec.BooleanValue value){return !SPEC.isLoaded()||value.get();}
    public static int color(ModConfigSpec.IntValue value,int fallback){int rgb=SPEC.isLoaded()?value.get():fallback;if(SPEC.isLoaded()&&THEME.get()==Theme.CLASSIC){rgb=value==BACKGROUND?0xc6c6c6:value==PANEL?0x8b8b8b:value==SLOT?0x8b8b8b:value==TEXT||value==MUTED?0x404040:value==PROGRESS?0x6cb542:0x555555;}return 0xff000000|rgb;}
    public static double animationSpeed(){return flag(ANIMATIONS)?SPEC.isLoaded()?ANIMATION_SPEED.get():1:0;}
    public static double chestSpeed(){return flag(CHEST_ANIMATION)?SPEC.isLoaded()?CHEST_SPEED.get():.1:1;}
    private ClientConfig(){}
}
