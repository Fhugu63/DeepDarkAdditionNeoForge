package com.fhugu.deepdarkaddition.blocks;

import com.fhugu.deepdarkaddition.MainScript;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.ToIntFunction;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MainScript.MOD_ID);

    public static final DeferredBlock<Block> SCULK_DIM_PORTAL_BLOCK = BLOCKS.register(
            "sculk_dim_portal_block",
            registryName -> new SculkDimPortalBlock(BlockBehaviour.Properties.of()
                    .setId(ResourceKey.create(Registries.BLOCK, registryName))
                    .noCollission()
                    .lightLevel(state -> 9)
                    .dynamicShape()
                    .noOcclusion()
                    .noLootTable()
                    .destroyTime(-1)
            ));

}
