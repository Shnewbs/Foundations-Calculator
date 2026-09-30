package com.foundations.calculator.client;
import com.foundations.calculator.content.*;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.*;
public final class MachineItemRenderer extends BlockEntityWithoutLevelRenderer {
    public MachineItemRenderer(){super(Minecraft.getInstance().getBlockEntityRenderDispatcher(),Minecraft.getInstance().getEntityModels());}
    public void renderByItem(ItemStack stack,ItemDisplayContext context,PoseStack pose,MultiBufferSource buffers,int light,int overlay){
        if(stack.getItem() instanceof BlockItem item&&item.getBlock() instanceof MachineBlock){
            MachineBlockEntity m=new MachineBlockEntity(BlockPos.ZERO,item.getBlock().defaultBlockState());
            var dispatcher=Minecraft.getInstance().getBlockEntityRenderDispatcher();
            pose.pushPose();
            if(dispatcher.getRenderer(m) instanceof MachineRenderer renderer){
                // The world model can span several blocks. Fit its actual mesh into one
                // centered item cube before Minecraft applies each display-context transform.
                var bounds=renderer.itemBounds(m);var center=bounds.getCenter();
                float scale=(float)(1/Math.max(1,Math.max(bounds.getXsize(),Math.max(bounds.getYsize(),bounds.getZsize()))));
                pose.translate(.5,.5,.5);pose.scale(scale,scale,scale);pose.translate(-center.x,-center.y,-center.z);
            }
            dispatcher.renderItem(m,pose,buffers,light,overlay);pose.popPose();
        }else super.renderByItem(stack,context,pose,buffers,light,overlay);
    }
}
