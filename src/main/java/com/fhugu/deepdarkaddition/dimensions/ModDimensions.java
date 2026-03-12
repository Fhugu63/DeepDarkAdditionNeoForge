package com.fhugu.deepdarkaddition.dimensions;

import com.fhugu.deepdarkaddition.MainScript;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class ModDimensions {
    public static final ResourceKey<Level> DEEP_DARK_LEVEL =
            ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(MainScript.MOD_ID, "sculk_dimension"));
}
