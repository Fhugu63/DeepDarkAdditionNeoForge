package com.fhugu.deepdarkaddition.entitys;

import com.fhugu.deepdarkaddition.MainScript;
import com.fhugu.deepdarkaddition.engine.RegisterEntity;
import com.fhugu.deepdarkaddition.entitys.entityclasses.HungrySoul;
import com.fhugu.deepdarkaddition.entitys.entityclasses.SculkCreeper;
import com.fhugu.deepdarkaddition.entitys.visual.HungrySoul.HungrySoulModel;
import com.fhugu.deepdarkaddition.entitys.visual.HungrySoul.HungrySoulRenderer;
import com.fhugu.deepdarkaddition.entitys.visual.SculkCreeper.SculkCreeperModel;
import com.fhugu.deepdarkaddition.entitys.visual.SculkCreeper.SculkCreeperRenderer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntitys {
    /*public static final DeferredRegister.Entities ENTITY_TYPES =
            DeferredRegister.createEntities(MainScript.MOD_ID);*/


    public static final DeferredHolder<EntityType<?>, EntityType<HungrySoul>> HUNGRY_SOUL =
            RegisterEntity.registerEntity("hungry_soul", EntityType.Builder.of(HungrySoul::new, MobCategory.MONSTER).sized(0.5f,0.5f),
                    HungrySoulModel.HS_LAYER, HungrySoulModel::createBodyLayer, HungrySoulRenderer::new, HungrySoul::createLivingAttributes);

    public static final DeferredHolder<EntityType<?>, EntityType<SculkCreeper>> SCULK_CREEPER =
            RegisterEntity.registerEntity("sculk_creeper", EntityType.Builder.of(SculkCreeper::new, MobCategory.MONSTER).sized(0.5f,1.5f),
                    SculkCreeperModel.SC_LAYER, SculkCreeperModel::createBodyLayer, SculkCreeperRenderer::new, SculkCreeper::createAttributes);

    public static void register(IEventBus eventBus) {
        RegisterEntity.ENTITY_TYPES.register(eventBus);
    }
}
