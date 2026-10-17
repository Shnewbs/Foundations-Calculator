package com.foundations.calculator.client;

import java.util.*;
import com.foundations.calculator.core.*;
import com.foundations.calculator.content.*;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.blockentity.*;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.*;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.core.*;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.*;

/** Extract live data once; queued geometry reads only the frame's render state. */
public final class MachineRenderer implements BlockEntityRenderer<MachineBlockEntity,MachineRenderer.State> {
    public static final Set<String> MODELED=MachineBlock.MODELED;
    public static final class State extends BlockEntityRenderState {
        String kind="";Direction facing=Direction.NORTH;double clock;long tick,lastCrank=Long.MIN_VALUE,screenEnergy,screenCapacity;float open,partial;int progress;BlockPos mast;
        final ItemStackRenderState input=new ItemStackRenderState();
    }
    @FunctionalInterface private interface MeshSink {void draw(ModelPart part,PoseStack pose,String texture,int light,int overlay);}
    private final Map<String,net.minecraft.resources.Identifier> textures=new HashMap<>();
    private net.minecraft.resources.Identifier texture(String name){return textures.computeIfAbsent(name,key->Content.id("textures/legacy_calculator/model/"+key+".png"));}
    private final Map<String,ModelPart> meshes=new HashMap<>();private final Map<String,AABB> itemBounds=new HashMap<>();
    private final ItemModelResolver resolver;private final ChestModel chest;
    private static final class ChestModel extends Model<State> {
        final ModelPart parts;
        ChestModel(ModelPart parts){super(parts,RenderTypes::entityCutout);this.parts=parts;}
        @Override public void setupAnim(State state){parts.getChild("lid").xRot=-state.open*(float)Math.PI/2;parts.getChild("lock").xRot=parts.getChild("lid").xRot;}
    }
    public MachineRenderer(BlockEntityRendererProvider.Context context){this(context.entityModelSet(),context.itemModelResolver());}
    public MachineRenderer(EntityModelSet models){this(models,null);}
    private MachineRenderer(EntityModelSet models,ItemModelResolver resolver){
        this.resolver=resolver;chest=new ChestModel(models.bakeLayer(ModelLayers.CHEST));
        meshes.put("chamber",LegacyModels.chamber());meshes.put("processing",LegacyModels.processing());meshes.put("split",LegacyModels.split());
        meshes.put("conductor_mast",LegacyModels.conductorMast());meshes.put("transmitter",LegacyModels.transmitter());meshes.put("scarecrow",LegacyModels.scarecrow());meshes.put("docking_station",LegacyModels.dockingStation());meshes.put("crank_handle",LegacyModels.crankHandle());meshes.put("weather_station",LegacyModels.weatherStationBase());meshes.put("dish",LegacyModels.weatherStationDish());meshes.put("magnetic_flux",LegacyModels.magneticFlux());meshes.put("arm",LegacyModels.fabricationArm());
    }
    @Override public State createRenderState(){return new State();}
    @Override public void extractRenderState(MachineBlockEntity machine,State state,float partial,Vec3 camera,ModelFeatureRenderer.CrumblingOverlay breaking){
        BlockEntityRenderer.super.extractRenderState(machine,state,partial,camera,breaking);
        state.kind=machine.kind();state.facing=machine.getBlockState().getValue(MachineBlock.FACING);state.tick=machine.getLevel().getGameTime();state.partial=partial;state.clock=(state.tick+partial)*ClientConfig.animationSpeed();state.progress=machine.progress;state.lastCrank=machine.lastCrankTick;
        float open=net.minecraft.util.Mth.lerp(partial,machine.previousLid,machine.lid);state.open=1-(1-open)*(1-open)*(1-open);
        state.mast=machine.program.contains("Mast")?BlockPos.of(machine.program.getLongOr("Mast",0L)):null;state.screenEnergy=machine.program.getLongOr("ScreenEnergy",0L);state.screenCapacity=machine.program.getLongOr("ScreenCapacity",0L);
        state.input.clear();if(resolver!=null&&ClientConfig.flag(ClientConfig.DISPLAY_ITEMS)&&state.kind.endsWith("chamber"))resolver.updateForTopItem(state.input,machine.inventory.getStackInSlot(0),ItemDisplayContext.GROUND,machine.getLevel(),null,0);
    }
    private void mesh(String name,String texture,PoseStack pose,MeshSink sink,int light,int overlay){sink.draw(meshes.get(name),pose,texture,light,overlay);}
    private void geometry(State state,PoseStack pose,MeshSink sink,int light,int overlay){
        String kind=state.kind;if(!MODELED.contains(kind)&&!kind.equals("fabrication_chamber")&&!kind.equals("analysing_chamber"))return;
        pose.pushPose();pose.translate(.5,1.5,.5);pose.mulPose(Axis.YP.rotationDegrees(-state.facing.toYRot()));pose.mulPose(Axis.XP.rotationDegrees(180));
        if(kind.endsWith("chamber")&&!kind.equals("fabrication_chamber")&&!kind.equals("analysing_chamber")){
            String texture=kind.equals("reassembly_chamber")||kind.equals("restoration_chamber")?"machine_frame_black":"machine_frame";mesh("chamber",texture,pose,sink,light,overlay);
            pose.pushPose();if(ClientConfig.flag(ClientConfig.ANIMATIONS)&&state.progress>0)pose.translate(0,0,Math.sin(state.clock*.15)*.15);mesh(kind.equals("precision_chamber")||kind.equals("extraction_chamber")?"split":"processing",texture,pose,sink,light,overlay);pose.popPose();
        }else switch(kind){
            case "conductor_mast"->mesh(kind,"conductormask",pose,sink,light,overlay);
            case "transmitter"->mesh(kind,"transmitter",pose,sink,light,overlay);
            case "scarecrow"->mesh(kind,"scarecrow",pose,sink,light,overlay);
            case "docking_station"->mesh(kind,"dockingstation",pose,sink,light,overlay);
            case "crank_handle"->{if(ClientConfig.flag(ClientConfig.ANIMATIONS)&&state.lastCrank!=Long.MIN_VALUE&&state.tick-state.lastCrank<18)pose.mulPose(Axis.YP.rotationDegrees((state.tick-state.lastCrank+state.partial)*20));mesh(kind,"crank",pose,sink,light,overlay);}
            case "magnetic_flux"->{pose.mulPose(Axis.YP.rotationDegrees((float)(state.clock*2)));mesh(kind,"magnetic_flux",pose,sink,light,overlay);}
            case "weather_station"->{if(state.mast!=null)pose.mulPose(Axis.YP.rotation((float)Math.atan2(state.mast.getZ()-state.blockPos.getZ(),state.mast.getX()-state.blockPos.getX())));mesh(kind,"weatherstation_base",pose,sink,light,overlay);pose.translate(0,-.9,-.84);pose.mulPose(Axis.XP.rotationDegrees(45));mesh("dish","weatherstation_dish",pose,sink,light,overlay);}
            case "fabrication_chamber"->{pose.translate(.5,.375,.75);if(ClientConfig.flag(ClientConfig.ANIMATIONS)&&state.progress>0)pose.mulPose(Axis.YP.rotationDegrees((float)(Math.sin(state.clock*.1)*35)));mesh("arm","fabrication_arm_techne",pose,sink,light,overlay);pose.translate(-1,0,0);mesh("arm","fabrication_arm_techne",pose,sink,light,overlay);}
        }
        pose.popPose();
    }
    private void chestPose(State state,PoseStack pose){pose.translate(.5,.5,.5);pose.mulPose(Axis.YP.rotationDegrees(-state.facing.toYRot()));pose.translate(-.5,-.5,-.5);}
    @Override public void submit(State state,PoseStack pose,SubmitNodeCollector collector,CameraRenderState camera){submitGeometry(state,pose,collector,state.lightCoords,net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY);}
    public void submitGeometry(State state,PoseStack pose,SubmitNodeCollector collector,int light,int overlay){
        if(state.kind.equals("reinforced_chest")){pose.pushPose();chestPose(state,pose);collector.submitModel(chest,state,pose,RenderTypes.entityCutout(texture("reinforced_chest")),light,overlay,-1,state.breakProgress);pose.popPose();return;}
        if(state.kind.equals("calculator_screen_block")){display(state,pose,collector,light);return;}
        geometry(state,pose,(part,p,texture,l,o)->collector.submitModelPart(part,p,RenderTypes.entityCutout(texture(texture)),l,o,null,-1,state.breakProgress),light,overlay);
        if(!state.input.isEmpty()){pose.pushPose();pose.translate(.5,.4,.5);pose.scale(.4f,.4f,.4f);pose.mulPose(Axis.YP.rotationDegrees((float)(state.clock*2)));state.input.submit(pose,collector,light,overlay,0);pose.popPose();}
    }
    public AABB itemBounds(String kind){return itemBounds.computeIfAbsent(kind,key->{State state=new State();state.kind=key;state.blockPos=BlockPos.ZERO;var bounds=new ItemMeshBounds();var pose=new PoseStack();if(key.equals("reinforced_chest")){chestPose(state,pose);chest.setupAnim(state);chest.parts.render(pose,bounds,15728880,0);}else geometry(state,pose,(part,p,texture,l,o)->part.render(p,bounds,l,o),15728880,0);return bounds.bounds();});}
    private void display(State state,PoseStack pose,SubmitNodeCollector collector,int light){
        pose.pushPose();pose.translate(.5,.7,.5);pose.mulPose(Axis.YP.rotationDegrees(-state.facing.toYRot()));pose.translate(0,0,.505);pose.scale(.008f,-.008f,.008f);Font font=Minecraft.getInstance().font;
        String text=EnergyDisplay.compact(state.screenEnergy)+" FE",capacity="/ "+EnergyDisplay.compact(state.screenCapacity);
        collector.submitText(pose,-font.width(text)/2f,0,net.minecraft.network.chat.Component.literal(text).getVisualOrderText(),false,Font.DisplayMode.NORMAL,light,0xff7ee3ce,0,0);
        collector.submitText(pose,-font.width(capacity)/2f,12,net.minecraft.network.chat.Component.literal(capacity).getVisualOrderText(),false,Font.DisplayMode.NORMAL,light,0xffc4d5df,0,0);pose.popPose();
    }
    public int getViewDistance(){return ClientConfig.SPEC.isLoaded()?ClientConfig.RENDER_DISTANCE.get():64;}
    public boolean shouldRenderOffScreen(){return false;}
    public AABB getRenderBoundingBox(MachineBlockEntity machine){return new AABB(machine.getBlockPos()).inflate(2,4,2);}
}
