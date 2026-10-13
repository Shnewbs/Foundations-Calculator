package com.foundations.calculator.client;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import com.foundations.calculator.content.Content;
import java.util.function.Consumer;
import org.joml.Vector3fc;
/** Native special item models fit the same world geometry into a centered item cube. */
public final class MachineItemRenderer implements SpecialModelRenderer<String> {
    private final MachineRenderer renderer;private final String kind;
    MachineItemRenderer(MachineRenderer renderer,String kind){this.renderer=renderer;this.kind=kind;}
    public record Unbaked(String kind) implements SpecialModelRenderer.Unbaked<String> {
        public static final MapCodec<Unbaked> CODEC=com.mojang.serialization.codecs.RecordCodecBuilder.mapCodec(i->i.group(com.mojang.serialization.Codec.STRING.fieldOf("kind").forGetter(Unbaked::kind)).apply(i,Unbaked::new));
        public SpecialModelRenderer<String> bake(SpecialModelRenderer.BakingContext context){return new MachineItemRenderer(new MachineRenderer(context.entityModelSet()),kind);}
        public MapCodec<Unbaked> type(){return CODEC;}
    }
    public String extractArgument(ItemStack stack){return kind;}
    public void submit(String argument,PoseStack pose,SubmitNodeCollector collector,int light,int overlay,boolean foil,int outline){
        var bounds=renderer.itemBounds(kind);var center=bounds.getCenter();float scale=(float)(1/Math.max(1,Math.max(bounds.getXsize(),Math.max(bounds.getYsize(),bounds.getZsize()))));
        var state=new MachineRenderer.State();state.kind=kind;state.blockPos=BlockPos.ZERO;pose.pushPose();pose.translate(.5,.5,.5);pose.scale(scale,scale,scale);pose.translate(-center.x,-center.y,-center.z);renderer.submitGeometry(state,pose,collector,light,overlay);pose.popPose();
    }
    public void getExtents(Consumer<Vector3fc> consumer){var b=renderer.itemBounds(kind);var c=b.getCenter();float scale=(float)(1/Math.max(1,Math.max(b.getXsize(),Math.max(b.getYsize(),b.getZsize()))));for(double x:new double[]{b.minX,b.maxX})for(double y:new double[]{b.minY,b.maxY})for(double z:new double[]{b.minZ,b.maxZ})consumer.accept(new org.joml.Vector3f((float)(.5+(x-c.x)*scale),(float)(.5+(y-c.y)*scale),(float)(.5+(z-c.z)*scale)));}
}
