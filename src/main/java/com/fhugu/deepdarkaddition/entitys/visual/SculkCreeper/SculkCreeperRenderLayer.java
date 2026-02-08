package com.fhugu.deepdarkaddition.entitys.visual.SculkCreeper;

import com.fhugu.deepdarkaddition.entitys.visual.HungrySoul.HungrySoulModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

public class SculkCreeperRenderLayer extends RenderLayer<SculkCreeperRenderState, SculkCreeperModel> {
    private final SculkCreeperModel model;

    public SculkCreeperRenderLayer(SculkCreeperRenderer renderer, EntityModelSet entityModelSet) {
        super(renderer);
        this.model = new SculkCreeperModel(entityModelSet.bakeLayer(SculkCreeperModel.SC_LAYER));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, SculkCreeperRenderState renderState, float yRot, float xRot) {
        // ...
    }
}
