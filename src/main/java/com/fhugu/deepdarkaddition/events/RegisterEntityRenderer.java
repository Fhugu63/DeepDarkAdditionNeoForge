package com.fhugu.deepdarkaddition.events;

import com.fhugu.deepdarkaddition.MainScript;
import com.fhugu.deepdarkaddition.entitys.ModEntitys;
import com.fhugu.deepdarkaddition.entitys.visual.HungrySoul.HungrySoulModel;
import com.fhugu.deepdarkaddition.entitys.visual.HungrySoul.HungrySoulRenderer;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = MainScript.MOD_ID, value = Dist.CLIENT)
public class RegisterEntityRenderer {
    @SubscribeEvent // on the mod event bus only on the physical client
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(HungrySoulModel.HS_LAYER, HungrySoulModel::createBodyLayer);
    }

    @SubscribeEvent // on the mod event bus only on the physical client
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        //event.registerEntityRenderer(ModEntitys.HUNGRY_SOUL.get(), HungrySoulRenderer::new);
    }


}
