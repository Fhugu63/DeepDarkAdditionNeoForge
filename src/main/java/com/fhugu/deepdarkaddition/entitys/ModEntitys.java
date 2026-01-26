package com.fhugu.deepdarkaddition.entitys;

import com.fhugu.deepdarkaddition.MainScript;
import com.fhugu.deepdarkaddition.entitys.entityclasses.HungrySoul;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntitys {
    public static final DeferredRegister.Entities ENTITY_TYPES =
            DeferredRegister.createEntities(MainScript.MOD_ID);

    public static final Supplier<EntityType<HungrySoul>> HUNGRY_SOUL = ENTITY_TYPES.register(
            "hungry_soul",
            // The entity type, created using a builder.
            () -> EntityType.Builder.of(
                            // An EntityType.EntityFactory<T>, where T is the entity class used - MyEntity in this case.
                            // You can think of it as a BiFunction<EntityType<T>, Level, T>.
                            // This is commonly a reference to the entity constructor.
                            HungrySoul::new,
                            // The MobCategory our entity uses. This is mainly relevant for spawning.
                            // See below for more information.
                            MobCategory.MONSTER
                    )
                    // The width and height, in blocks. The width is used in both horizontal directions.
                    // This also means that non-square footprints are not supported. Default is 0.6f and 1.8f.
                    .sized(1.0f, 1.0f)
                    // A multiplicative factor (scalar) used by mobs that spawn in varying sizes.
                    // In vanilla, these are only slimes and magma cubes, both of which use 4.0f.
                    .spawnDimensionsScale(4.0f)
                    // The eye height, in blocks from the bottom of the size. Defaults to height * 0.85.
                    // This must be called after #sized to have an effect.
                    .eyeHeight(0.5f)
                    // Disables the entity being summonable via /summon.
                    //.noSummon()
                    // Prevents the entity from being saved to disk.
                    //.noSave()
                    // Makes the entity fire immune.
                    //.fireImmune()
                    // Makes the entity immune to damage from a certain block. Vanilla uses this to make
                    // foxes immune to sweet berry bushes, withers and wither skeletons immune to wither roses,
                    // and polar bears, snow golems and strays immune to powder snow.
                    // Disables a rule in the spawn handler that limits the distance at which entities can spawn.
                    // This means that no matter the distance to the player, this entity can spawn.
                    // Vanilla enables this for pillagers and shulkers.
                    .canSpawnFarFromPlayer()
                    // The range in which the entity is kept loaded by the client, in chunks.
                    // Vanilla values for this vary, but it's often something around 8 or 10. Defaults to 5.
                    // Be aware that if this is greater than the client's chunk view distance,
                    // then that chunk view distance is effectively used here instead.
                    .clientTrackingRange(8)
                    // How often update packets are sent for this entity, in once every x ticks. This is set to higher values
                    // for entities that have predictable movement patterns, for example projectiles. Defaults to 3.
                    .updateInterval(10)
                    // Build the entity type using a resource key. The second parameter should be the same as the entity id.
                    .build(ResourceKey.create(
                            Registries.ENTITY_TYPE,
                            ResourceLocation.fromNamespaceAndPath(MainScript.MOD_ID, "hungrysoul")
                    ))
    );
    /*
    // Shorthand version to avoid boilerplate. The following call is the same as
    ENTITY_TYPES.register("my_entity", () -> EntityType.Builder.of(MyEntity::new, MobCategory.MISC).build(
        ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("examplemod", "my_entity"))
            );

    public static final Supplier<EntityType<HungrySoul>> HUNGRY_SOUL = ENTITY_TYPES.registerEntityType(
            "hungry_soul", HungrySoul::new, MobCategory.MONSTER,
            builder -> builder.sized(2.0f, 1.0f).eyeHeight(1.5f).updateInterval(5));*/
}
