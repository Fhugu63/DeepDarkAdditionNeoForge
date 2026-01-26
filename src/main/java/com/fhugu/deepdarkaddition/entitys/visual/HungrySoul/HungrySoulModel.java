package com.fhugu.deepdarkaddition.entitys.visual.HungrySoul;// Exported for Minecraft version 1.17 or later with Mojang mappings
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

public class HungrySoulModel extends EntityModel<HungrySoulRenderState> {
    public static final ModelLayerLocation HS_LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(MainScript.MOD_ID, "hungrysoul"),
            "main"
    );

    private final ModelPart head;

    protected HungrySoulModel(ModelPart root) {
        super(root);

        this.head = root.getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();

        PartDefinition root = mesh.getRoot();

        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0,24,0));

        PartDefinition a = head.addOrReplaceChild("1", CubeListBuilder.create().texOffs(16, 8).addBox(1.0F, 0.0F, -2.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 4).addBox(0.0F, 0.0F, -1.0F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(8, 14).addBox(-1.0F, 0.0F, 1.0F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(8, 15).addBox(-2.0F, 0.0F, 1.0F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(16, 9).addBox(-2.0F, 0.0F, -1.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(16, 10).addBox(-1.0F, 0.0F, -2.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition b = head.addOrReplaceChild("2", CubeListBuilder.create().texOffs(0, 10).addBox(0.0F, 0.0F, -2.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 7).addBox(0.0F, 0.0F, -1.0F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 16).addBox(-1.0F, 0.0F, 1.0F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(16, 0).addBox(-2.0F, 0.0F, 1.0F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(16, 11).addBox(-2.0F, 0.0F, -1.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(16, 12).addBox(-1.0F, 0.0F, -2.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -2.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition c = head.addOrReplaceChild("3", CubeListBuilder.create().texOffs(16, 13).addBox(1.0F, 0.0F, -2.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(8, 0).addBox(0.0F, 0.0F, -1.0F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(16, 1).addBox(-1.0F, 0.0F, 1.0F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(16, 2).addBox(-2.0F, 0.0F, 1.0F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(16, 14).addBox(-2.0F, 0.0F, -1.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(16, 15).addBox(-1.0F, 0.0F, -2.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, 0.0F));

        PartDefinition d = head.addOrReplaceChild("4", CubeListBuilder.create().texOffs(0, 12).addBox(0.0F, 0.0F, -2.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(8, 3).addBox(0.0F, 0.0F, -1.0F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(16, 3).addBox(-1.0F, 0.0F, 1.0F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(16, 4).addBox(-2.0F, 0.0F, 1.0F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(16, 16).addBox(-2.0F, 0.0F, -1.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 17).addBox(-1.0F, 0.0F, -2.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, -2.0F, -1.5708F, 0.0F, 1.5708F));

        PartDefinition e = head.addOrReplaceChild("5", CubeListBuilder.create().texOffs(8, 12).addBox(0.0F, 0.0F, -2.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(8, 6).addBox(0.0F, 0.0F, -1.0F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(16, 5).addBox(-1.0F, 0.0F, 1.0F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(16, 6).addBox(-2.0F, 0.0F, 1.0F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 17).addBox(-2.0F, 0.0F, -1.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(8, 17).addBox(-1.0F, 0.0F, -2.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 2.0F, -1.5708F, 0.0F, 1.5708F));

        PartDefinition f = head.addOrReplaceChild("6", CubeListBuilder.create().texOffs(0, 14).addBox(0.0F, 0.0F, -2.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(8, 9).addBox(0.0F, 0.0F, -1.0F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(16, 7).addBox(-1.0F, 0.0F, 1.0F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(8, 16).addBox(-2.0F, 0.0F, 1.0F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(12, 17).addBox(-2.0F, 0.0F, -1.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(16, 17).addBox(-1.0F, 0.0F, -2.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -2.0F, 0.0F, 0.0F, -1.5708F, 1.5708F));

        return LayerDefinition.create(mesh, 32, 32);
    }

    /*@Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {

    }*/
}