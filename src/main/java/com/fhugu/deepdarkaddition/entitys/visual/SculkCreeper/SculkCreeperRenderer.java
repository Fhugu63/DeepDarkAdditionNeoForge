package com.fhugu.deepdarkaddition.entitys.visual.SculkCreeper;

import com.fhugu.deepdarkaddition.entitys.entityclasses.HungrySoul;
import com.fhugu.deepdarkaddition.entitys.entityclasses.SculkCreeper;
import com.fhugu.deepdarkaddition.entitys.visual.HungrySoul.HungrySoulModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;

// The generic type in the superclass should be set to what entity you want to render.
// If you wanted to enable rendering for any entity, you'd use Entity, like we do here.
// You'd also use an EntityRenderState that fits your use case. More on this below.
public class SculkCreeperRenderer extends LivingEntityRenderer<SculkCreeper, SculkCreeperRenderState, SculkCreeperModel> {
    // In our constructor, we just forward to super.
    public SculkCreeperRenderer(EntityRendererProvider.Context context) {

        super(context, new SculkCreeperModel(context.bakeLayer(SculkCreeperModel.SC_LAYER)), 0.5f);

        this.addLayer(new SculkCreeperRenderLayer(this, context.getModelSet()));
    }

    @Override
    public void extractRenderState(SculkCreeper entity, SculkCreeperRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        // Extract your own stuff here, see the beginning of the article.
    }

    @Override
    public void render(SculkCreeperRenderState state, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        // Calling super will automatically render the layer for you.
        super.render(state, poseStack, bufferSource, packedLight);
        // Then, do custom rendering here, if applicable.

    }

    @Override
    public SculkCreeperRenderState createRenderState() {
        return new SculkCreeperRenderState();
    }

    @Override
    public ResourceLocation getTextureLocation(SculkCreeperRenderState renderState) {
        return SculkCreeperModel.SC_LAYER.model();
    }
}

