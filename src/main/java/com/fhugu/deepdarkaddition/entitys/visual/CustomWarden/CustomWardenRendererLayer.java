package com.fhugu.deepdarkaddition.entitys.visual.CustomWarden;

import com.fhugu.deepdarkaddition.entitys.visual.HungrySoul.HungrySoulModel;
import com.fhugu.deepdarkaddition.entitys.visual.HungrySoul.HungrySoulRenderState;
import com.fhugu.deepdarkaddition.entitys.visual.HungrySoul.HungrySoulRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

public class CustomWardenRendererLayer extends RenderLayer<CustomWardenRendererState, CustomWardenModel> {
    private final CustomWardenModel model;

    public CustomWardenRendererLayer(CustomWardenRenderer renderer, EntityModelSet entityModelSet) {
        super(renderer);
        this.model = new CustomWardenModel(entityModelSet.bakeLayer(CustomWardenModel.LAYER_LOCATION));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, CustomWardenRendererState renderState, float yRot, float xRot) {

    }
}
