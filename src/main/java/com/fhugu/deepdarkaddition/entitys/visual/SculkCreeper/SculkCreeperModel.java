package com.fhugu.deepdarkaddition.entitys.visual.SculkCreeper;// Made with Blockbench 5.0.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.fhugu.deepdarkaddition.MainScript;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class SculkCreeperModel extends EntityModel<SculkCreeperRenderState> {
    public static final ModelLayerLocation SC_LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(MainScript.MOD_ID, "textures/entity/sculkcreeper.png"),
            "main"
    );

    private final ModelPart body;
    public final ModelPart sound_wave;

    protected SculkCreeperModel(ModelPart root) {
        super(root);

        this.body = root.getChild("body");
        this.sound_wave = root.getChild("sound_wave");
        sound_wave.visible = false;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();

        PartDefinition partdefinition = mesh.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, -18.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-4.0F, -26.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition front_leg = body.addOrReplaceChild("front_leg", CubeListBuilder.create().texOffs(6, 54).addBox(0.0F, -6.0F, -6.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(6, 54).mirror().addBox(-4.0F, -6.0F, -6.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition back_leg = body.addOrReplaceChild("back_leg", CubeListBuilder.create().texOffs(48, 6).addBox(0.0F, -6.0F, -6.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(48, 6).mirror().addBox(-4.0F, -6.0F, -6.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 8.0F));

        PartDefinition idk_right = body.addOrReplaceChild("idk_right", CubeListBuilder.create().texOffs(8, 22).addBox(0.0F, -3.0F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(12, 22).addBox(2.0F, -4.0F, 0.0F, 2.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(28, 26).addBox(4.0F, -5.0F, 0.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(24, 0).addBox(5.0F, -5.0F, 0.0F, 4.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(24, 4).addBox(7.0F, -7.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(28, 4).addBox(6.0F, -8.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(8, 28).addBox(5.0F, -8.0F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(16, 26).addBox(6.0F, -3.0F, 0.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(14, 28).addBox(8.0F, -3.0F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -23.0F, 0.0F));

        PartDefinition idk_left = body.addOrReplaceChild("idk_left", CubeListBuilder.create().texOffs(5, 22).addBox(0.1667F, -3.0F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(10, 22).addBox(3.1667F, -4.0F, 0.0F, 2.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(28, 26).addBox(4.1667F, -5.0F, 0.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(24, 0).addBox(5.1667F, -5.0F, 0.0F, 4.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(24, 4).addBox(7.1667F, -7.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(28, 4).addBox(6.1667F, -8.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(8, 29).addBox(5.1667F, -8.0F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(16, 26).addBox(6.1667F, -3.0F, 0.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(14, 28).addBox(8.1667F, -3.0F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.8333F, -23.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition sound_wave = partdefinition.addOrReplaceChild("sound_wave", CubeListBuilder.create().texOffs(13, 46).addBox(-9.0F, 0.0F, -9.0F, 17.0F, 0.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(mesh, 64, 64);
    }


}