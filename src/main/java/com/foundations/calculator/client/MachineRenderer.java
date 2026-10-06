package com.foundations.calculator.client;

import java.util.*;
import com.foundations.calculator.core.ClientConfig;
import com.foundations.calculator.content.*;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.blockentity.*;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.AABB;

public final class MachineRenderer implements BlockEntityRenderer<MachineBlockEntity> {
    public static final Set<String> MODELED=MachineBlock.MODELED;
    private final Map<String,ModelPart> meshes=new HashMap<>();
    public MachineRenderer(BlockEntityRendererProvider.Context ctx){
        meshes.put("reinforced_chest",ctx.bakeLayer(net.minecraft.client.model.geom.ModelLayers.CHEST));
        meshes.put("chamber",LegacyModels.chamber());meshes.put("processing",LegacyModels.processing());meshes.put("split",LegacyModels.split());
        meshes.put("conductor_mast",LegacyModels.conductorMast());meshes.put("transmitter",LegacyModels.transmitter());meshes.put("scarecrow",LegacyModels.scarecrow());meshes.put("docking_station",LegacyModels.dockingStation());meshes.put("crank_handle",LegacyModels.crankHandle());meshes.put("weather_station",LegacyModels.weatherStationBase());meshes.put("dish",LegacyModels.weatherStationDish());meshes.put("magnetic_flux",LegacyModels.magneticFlux());meshes.put("arm",LegacyModels.fabricationArm());
    }
    private void mesh(String name,String texture,PoseStack pose,MultiBufferSource buffers,int light,int overlay){meshes.get(name).render(pose,buffers.getBuffer(RenderType.entityCutoutNoCull(Content.id("textures/legacy_calculator/model/"+texture+".png"))),light,overlay);}
    public void render(MachineBlockEntity m,float partial,PoseStack pose,MultiBufferSource buffers,int light,int overlay){
        String kind=m.kind();double clock=m.getLevel()==null?0:(m.getLevel().getGameTime()+partial)*ClientConfig.animationSpeed();
        if(kind.equals("reinforced_chest")){chest(m,partial,pose,buffers,light,overlay);return;}
        if(kind.equals("calculator_screen_block")){display(m,pose,buffers,light);return;}
        if(!MODELED.contains(kind)&&!kind.equals("fabrication_chamber")&&!kind.equals("analysing_chamber"))return;
        pose.pushPose();pose.translate(.5,1.5,.5);pose.mulPose(Axis.YP.rotationDegrees(-m.getBlockState().getValue(MachineBlock.FACING).toYRot()));pose.mulPose(Axis.XP.rotationDegrees(180));
        if(kind.endsWith("chamber")&&!kind.equals("fabrication_chamber")&&!kind.equals("analysing_chamber")){
            boolean dark=kind.equals("reassembly_chamber")||kind.equals("restoration_chamber");String tex=dark?"machine_frame_black":"machine_frame";mesh("chamber",tex,pose,buffers,light,overlay);
            pose.pushPose();if(ClientConfig.flag(ClientConfig.ANIMATIONS)&&m.progress>0)pose.translate(0,0,Math.sin(clock*.15)*.15);mesh(kind.equals("precision_chamber")||kind.equals("extraction_chamber")?"split":"processing",tex,pose,buffers,light,overlay);pose.popPose();
        }else switch(kind){
            case "conductor_mast"->mesh(kind,"conductormask",pose,buffers,light,overlay);
            case "transmitter"->mesh(kind,"transmitter",pose,buffers,light,overlay);
            case "scarecrow"->mesh(kind,"scarecrow",pose,buffers,light,overlay);
            case "docking_station"->mesh(kind,"dockingstation",pose,buffers,light,overlay);
            case "crank_handle"->{long tick=m.getLevel()==null?0:m.getLevel().getGameTime();if(ClientConfig.flag(ClientConfig.ANIMATIONS)&&m.lastCrankTick!=Long.MIN_VALUE&&tick-m.lastCrankTick<18)pose.mulPose(Axis.YP.rotationDegrees((float)((tick-m.lastCrankTick+partial)*20)));mesh(kind,"crank",pose,buffers,light,overlay);}
            case "magnetic_flux"->{pose.mulPose(Axis.YP.rotationDegrees((float)(clock*2)));mesh(kind,"magnetic_flux",pose,buffers,light,overlay);}
            case "weather_station"->{
                if(m.program.contains("Mast")){var target=net.minecraft.core.BlockPos.of(m.program.getLong("Mast"));pose.mulPose(Axis.YP.rotation((float)Math.atan2(target.getZ()-m.getBlockPos().getZ(),target.getX()-m.getBlockPos().getX())));}
                mesh(kind,"weatherstation_base",pose,buffers,light,overlay);pose.translate(0,-.9,-.84);pose.mulPose(Axis.XP.rotationDegrees(45));mesh("dish","weatherstation_dish",pose,buffers,light,overlay);
            }
            case "fabrication_chamber"->{pose.translate(.5,.375,.75);if(ClientConfig.flag(ClientConfig.ANIMATIONS)&&m.progress>0)pose.mulPose(Axis.YP.rotationDegrees((float)(Math.sin(clock*.1)*35)));mesh("arm","fabrication_arm_techne",pose,buffers,light,overlay);pose.translate(-1,0,0);mesh("arm","fabrication_arm_techne",pose,buffers,light,overlay);}
        }
        pose.popPose();
        if(ClientConfig.flag(ClientConfig.DISPLAY_ITEMS)&&kind.endsWith("chamber")&&!m.inventory.getStackInSlot(0).isEmpty()){
            pose.pushPose();pose.translate(.5,.4,.5);pose.scale(.4f,.4f,.4f);pose.mulPose(Axis.YP.rotationDegrees((float)(clock*2)));Minecraft.getInstance().getItemRenderer().renderStatic(m.inventory.getStackInSlot(0),ItemDisplayContext.GROUND,light,overlay,pose,buffers,m.getLevel(),0);pose.popPose();
        }
    }
    private final Map<String,AABB> itemBounds=new HashMap<>();
    public AABB itemBounds(MachineBlockEntity machine){
        return itemBounds.computeIfAbsent(machine.kind(),key->{
            var bounds=new ItemMeshBounds();
            render(machine,0,new PoseStack(),type->bounds,15728880,0);
            return bounds.bounds();
        });
    }
    private void chest(MachineBlockEntity m,float partial,PoseStack pose,MultiBufferSource buffers,int light,int overlay){
        pose.pushPose();pose.translate(.5,.5,.5);pose.mulPose(Axis.YP.rotationDegrees(-m.getBlockState().getValue(MachineBlock.FACING).toYRot()));pose.translate(-.5,-.5,-.5);
        var model=meshes.get("reinforced_chest");float open=net.minecraft.util.Mth.lerp(partial,m.previousLid,m.lid);open=1-(1-open)*(1-open)*(1-open);model.getChild("lid").xRot=-open*(float)Math.PI/2;model.getChild("lock").xRot=model.getChild("lid").xRot;
        model.render(pose,buffers.getBuffer(RenderType.entityCutout(Content.id("textures/legacy_calculator/model/reinforced_chest.png"))),light,overlay);pose.popPose();
    }
    public int getViewDistance(){return ClientConfig.SPEC.isLoaded()?ClientConfig.RENDER_DISTANCE.get():64;}
    private void display(MachineBlockEntity m,PoseStack pose,MultiBufferSource buffers,int light){
        pose.pushPose();pose.translate(.5,.7,.5);pose.mulPose(Axis.YP.rotationDegrees(-m.getBlockState().getValue(MachineBlock.FACING).toYRot()));pose.translate(0,0,.505);pose.scale(.008f,-.008f,.008f);
        Font font=Minecraft.getInstance().font;String text=com.foundations.calculator.core.EnergyDisplay.compact(m.program.getLong("ScreenEnergy"))+" FE";font.drawInBatch(text,-font.width(text)/2f,0,0xff7ee3ce,false,pose.last().pose(),buffers,Font.DisplayMode.NORMAL,0,light);
        String capacity="/ "+com.foundations.calculator.core.EnergyDisplay.compact(m.program.getLong("ScreenCapacity"));font.drawInBatch(capacity,-font.width(capacity)/2f,12,0xffc4d5df,false,pose.last().pose(),buffers,Font.DisplayMode.NORMAL,0,light);pose.popPose();
    }
    public boolean shouldRenderOffScreen(MachineBlockEntity m){return m.kind().equals("conductor_mast")||m.kind().equals("weather_station");}
    public AABB getRenderBoundingBox(MachineBlockEntity m){return new AABB(m.getBlockPos()).inflate(2,4,2);}
}
