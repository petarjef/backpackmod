package backpack.modid.client;

import backpack.modid.BackpackItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;


public class BackpackLayer<S extends HumanoidRenderState, M extends HumanoidModel<S>> extends RenderLayer<S, M> {


    private static final float SCALE = 0.8F;
    private static final float CENTER_Y = 6.0F;
    private static final float OFFSET_Z = 2.6F;
    public BackpackLayer(RenderLayerParent<S, M> renderer) {
        super(renderer);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector,
                       int lightCoords, S state, float yRot, float xRot) {
        System.out.println("BackpackLayer submit, chest = " + state.chestEquipment);
        ItemStack chest = state.chestEquipment;
        if (!(chest.getItem() instanceof BackpackItem)) {
            return;
        }

        ItemStackRenderState itemState = new ItemStackRenderState();
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getItemModelResolver().updateForTopItem(
                itemState, chest, ItemDisplayContext.NONE, minecraft.level, null, 0);
        if (itemState.isEmpty()) {
            return;
        }

        poseStack.pushPose();
        this.getParentModel().body.translateAndRotate(poseStack);
        poseStack.translate(0.0F, CENTER_Y / 16.0F, OFFSET_Z / 16.0F);
        poseStack.rotateDegrees(Axis.ZP, 180.0F);
        poseStack.scale(SCALE, SCALE, SCALE);
        itemState.submit(poseStack, submitNodeCollector, lightCoords,
                OverlayTexture.NO_OVERLAY, state.outlineColor);
        poseStack.popPose();
    }
}