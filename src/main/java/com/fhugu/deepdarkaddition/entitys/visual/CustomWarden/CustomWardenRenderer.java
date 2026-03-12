package com.fhugu.deepdarkaddition.entitys.visual.CustomWarden;

import com.fhugu.deepdarkaddition.entitys.entityclasses.CustomWarden;
import com.fhugu.deepdarkaddition.entitys.entityclasses.HungrySoul;
import com.fhugu.deepdarkaddition.entitys.visual.HungrySoul.HungrySoulModel;
import com.fhugu.deepdarkaddition.entitys.visual.HungrySoul.HungrySoulRenderLayer;
import com.fhugu.deepdarkaddition.entitys.visual.HungrySoul.HungrySoulRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;

public class CustomWardenRenderer  extends LivingEntityRenderer<CustomWarden, CustomWardenRendererState, CustomWardenModel> {
    public CustomWardenRenderer(EntityRendererProvider.Context context) {
        super(context, new CustomWardenModel(context.bakeLayer(CustomWardenModel.LAYER_LOCATION)), 2f);

        this.addLayer(new CustomWardenRendererLayer(this, context.getModelSet()));
    }

    @Override
    public void extractRenderState(CustomWarden entity, CustomWardenRendererState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        // Extract your own stuff here, see the beginning of the article.
        state.deadAnimationState = entity.deadAnimState;
    }

    @Override
    public void render(CustomWardenRendererState state, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        // Calling super will automatically render the layer for you.
        super.render(state, poseStack, bufferSource, packedLight);
        // Then, do custom rendering here, if applicable.
    }

    @Override
    public ResourceLocation getTextureLocation(CustomWardenRendererState renderState) {
        return CustomWardenModel.LAYER_LOCATION.model();
    }

    @Override
    public CustomWardenRendererState createRenderState() {
        return new CustomWardenRendererState();
    }
}
