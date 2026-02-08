package com.fhugu.deepdarkaddition.engine;

import com.fhugu.deepdarkaddition.MainScript;
import com.fhugu.deepdarkaddition.entitys.ModEntitys;
import com.fhugu.deepdarkaddition.entitys.entityclasses.HungrySoul;
import com.fhugu.deepdarkaddition.entitys.visual.HungrySoul.HungrySoulModel;
import com.fhugu.deepdarkaddition.entitys.visual.HungrySoul.HungrySoulRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.entity.EntityType.Builder;

import java.util.*;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

@EventBusSubscriber(modid = MainScript.MOD_ID, value = Dist.CLIENT)
public class RegisterEntity {
    public static final DeferredRegister.Entities ENTITY_TYPES =
            DeferredRegister.createEntities(MainScript.MOD_ID);

    public static Map<ModelLayerLocation, Supplier<LayerDefinition>> layerDefinitionsMap = new HashMap<>();
    public static Map<DeferredHolder<EntityType<?>, ?>, EntityRendererProvider<? extends LivingEntity>> entityRendererProviderMap = new HashMap<>();
    public static Map<Supplier<EntityType<? extends LivingEntity>>, Supplier<AttributeSupplier.Builder>> attributeSupplierMap = new HashMap<>();


    public static <T extends LivingEntity> DeferredHolder<EntityType<?>, EntityType<T>> registerEntity(
            String name, Builder<T> builder, ModelLayerLocation modelLayerLocation, Supplier<LayerDefinition> supplier,
            EntityRendererProvider<T> entityRendererProvider, Supplier<AttributeSupplier.Builder> attributeSupplier) {

        DeferredHolder<EntityType<?>, EntityType<T>> entity =
                ENTITY_TYPES.register(name, () -> builder.build(
                        ResourceKey.create(Registries.ENTITY_TYPE, modelLayerLocation.model())));

        layerDefinitionsMap.put(modelLayerLocation, supplier);
        entityRendererProviderMap.put(entity, entityRendererProvider);
        attributeSupplierMap.put(entity::get, attributeSupplier);

        return entity;
    }

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

}
