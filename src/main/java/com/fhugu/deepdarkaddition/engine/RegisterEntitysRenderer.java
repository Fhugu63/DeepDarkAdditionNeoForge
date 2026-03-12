package com.fhugu.deepdarkaddition.engine;

import com.fhugu.deepdarkaddition.MainScript;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RenderNameTagEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

import static com.fhugu.deepdarkaddition.engine.RegisterEntity.*;

@EventBusSubscriber(modid = MainScript.MOD_ID, value = Dist.CLIENT)
public class RegisterEntitysRenderer {
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        layerDefinitionsMap.forEach(event::registerLayerDefinition);
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        entityRendererProviderMap.forEach((type, provider) ->
                event.registerEntityRenderer((EntityType) type.get(), provider)
        );
        //event.registerEntityRenderer(ModEntitys.HUNGRY_SOUL.get(), HungrySoulRenderer::new);
    }

    @SubscribeEvent
    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        attributeSupplierMap.forEach(((entityType, supplier) ->
                event.put(entityType.get(), supplier.get().build())
        ));
        //event.put(ModEntitys.HUNGRY_SOUL.get(), HungrySoul.createLivingAttributes().build());
    }

    @SubscribeEvent // on the game event bus
    public static void renderNameTag(RenderNameTagEvent.CanRender event) {
        // Uses TriState to set the return state
        event.setCanRender(TriState.FALSE);
    }
}
