package com.fhugu.deepdarkaddition.entitys.visual.CustomWarden;// Made with Blockbench 5.0.7
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.fhugu.deepdarkaddition.MainScript;
import com.fhugu.deepdarkaddition.entitys.animations.CustomWarden.CustomWardenDeadAnimation;
import com.fhugu.deepdarkaddition.entitys.visual.SculkCreeper.SculkCreeperRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.client.entity.animation.json.AnimationHolder;
import org.jetbrains.annotations.NotNull;

public class CustomWardenModel extends EntityModel<CustomWardenRendererState> {
    public static final AnimationHolder DEAD_ANIMATION =
            Model.getAnimation(ResourceLocation.fromNamespaceAndPath(MainScript.MOD_ID, "warden_dead_animation"));

	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MainScript.MOD_ID, "textures/entity/warden_texture.png"), "main");
	private final ModelPart warden;
	private final ModelPart head;
	private final ModelPart left_tendril;
	private final ModelPart right_tendril;
	private final ModelPart torso;
	private final ModelPart left_ribcage;
	private final ModelPart right_ribcage;
	private final ModelPart left_arm;
	private final ModelPart right_arm;
	private final ModelPart left_leg;
	private final ModelPart right_leg;

	public CustomWardenModel(ModelPart root) {
        super(root);
        this.warden = root.getChild("warden");
		this.head = this.warden.getChild("head");
		this.left_tendril = this.head.getChild("left_tendril");
		this.right_tendril = this.head.getChild("right_tendril");
		this.torso = this.warden.getChild("torso");
		this.left_ribcage = this.torso.getChild("left_ribcage");
		this.right_ribcage = this.torso.getChild("right_ribcage");
		this.left_arm = this.warden.getChild("left_arm");
		this.right_arm = this.warden.getChild("right_arm");
		this.left_leg = this.warden.getChild("left_leg");
		this.right_leg = this.warden.getChild("right_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition warden = partdefinition.addOrReplaceChild("warden", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition head = warden.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 32).addBox(-8.0F, -16.0F, -5.0F, 16.0F, 16.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -34.0F, -1.0F));

		PartDefinition left_tendril = head.addOrReplaceChild("left_tendril", CubeListBuilder.create().texOffs(58, 0).addBox(0.0F, -13.0F, 0.0F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, -12.0F, 1.0F));

		PartDefinition right_tendril = head.addOrReplaceChild("right_tendril", CubeListBuilder.create().texOffs(52, 32).addBox(-16.0F, -13.0F, 0.0F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, -12.0F, 1.0F));

		PartDefinition torso = warden.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-9.0F, -13.0F, -4.0F, 18.0F, 21.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -21.0F, -1.0F));

		PartDefinition left_ribcage = torso.addOrReplaceChild("left_ribcage", CubeListBuilder.create().texOffs(90, 11).mirror().addBox(-7.0F, -11.0F, -0.1F, 9.0F, 21.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(7.0F, -2.0F, -4.0F));

		PartDefinition right_ribcage = torso.addOrReplaceChild("right_ribcage", CubeListBuilder.create().texOffs(90, 11).addBox(-2.0F, -11.0F, -0.1F, 9.0F, 21.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-7.0F, -2.0F, -4.0F));

		PartDefinition left_arm = warden.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 58).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 28.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(13.0F, -34.0F, 0.0F));

		PartDefinition right_arm = warden.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(44, 50).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 28.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-13.0F, -34.0F, 0.0F));

		PartDefinition left_leg = warden.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(76, 76).addBox(-2.9F, 0.0F, -3.0F, 6.0F, 13.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, -13.0F, -1.0F));

		PartDefinition right_leg = warden.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(76, 48).addBox(-3.1F, 0.0F, -3.0F, 6.0F, 13.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, -13.0F, -1.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

    @Override
    public void setupAnim(CustomWardenRendererState state) {
        super.setupAnim(state);
        // Other stuff here.

        animate(
                // Get the animation state to use from your EntityRenderState.
                state.deadAnimationState,
                // Your animation holder.
                DEAD_ANIMATION,
                // Your entity age, in ticks.
                state.ageInTicks
        );
        // A specialized version of animate(), designed for walking animations.
        //animateWalk(DEAD_ANIMATION, state.walkAnimationPos, state.walkAnimationSpeed, 1, 1);
        // A version of animate() that only applies the first frame of animation.
        //applyStatic(DEAD_ANIMATION);
    }
}