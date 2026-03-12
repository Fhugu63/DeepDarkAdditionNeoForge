package com.fhugu.deepdarkaddition.engine;

import com.fhugu.deepdarkaddition.MainScript;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Set;

public class MyPoiTypes {
    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(Registries.POINT_OF_INTEREST_TYPE, MainScript.MOD_ID);

    // Регистрируем POI для рамки.
    // 0 - радиус поиска (обычно для порталов), 1 - количество свободных мест (не важно для нас)
    public static final DeferredHolder<PoiType, PoiType> ANCIENT_PORTAL_POI =
            POI_TYPES.register("ancient_portal_frame",
                    () -> new PoiType(Set.copyOf(Blocks.REINFORCED_DEEPSLATE.getStateDefinition().getPossibleStates()), 0, 1));
}