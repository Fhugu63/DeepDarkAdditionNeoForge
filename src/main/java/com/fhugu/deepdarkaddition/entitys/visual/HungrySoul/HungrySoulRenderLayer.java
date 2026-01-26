package com.fhugu.deepdarkaddition.entitys.visual.HungrySoul;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

public class HungrySoulRenderLayer extends RenderLayer<HungrySoulRenderState, HungrySoulModel> {
    private final HungrySoulModel model;

    public HungrySoulRenderLayer(HungrySoulRenderer renderer, EntityModelSet entityModelSet) {
        super(renderer);
        this.model = new HungrySoulModel(entityModelSet.bakeLayer(HungrySoulModel.HS_LAYER));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, HungrySoulRenderState renderState, float yRot, float xRot) {
        // ...
    }
}
