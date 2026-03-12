package com.fhugu.deepdarkaddition.blocks;

import com.fhugu.deepdarkaddition.dimensions.ModDimensions;
import com.fhugu.deepdarkaddition.engine.MyPoiTypes;
import com.fhugu.deepdarkaddition.portallogical.PortalToSculkDimensionLogical;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Portal;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.Optional;

public class SculkDimPortalBlock extends Block implements Portal {
    public SculkDimPortalBlock(Properties properties) {
        super(properties);
    }

    /* @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!level.isClientSide) {
            ServerLevel sLevel = (ServerLevel) level;
            ServerLevel deep_dark_level = sLevel.getServer().getLevel(ModDimensions.DEEP_DARK_LEVEL);
            assert deep_dark_level != null;
            entity.teleport(new TeleportTransition(deep_dark_level, entity, entity1 -> {

            }));
        }

    } */

    @Override
    public int getPortalTransitionTime(ServerLevel level, Entity entity) {
        return 60;
    }

    /*@Override
    public void destroy(LevelAccessor levelAccessor, BlockPos pos, BlockState state) {
        if (levelAccessor instanceof ServerLevel level) {
            PortalToSculkDimensionLogical.tryOpenPortal(new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 1), level);
        }
    }*/

    @Override
    public @Nullable TeleportTransition getPortalDestination(ServerLevel level, Entity entity, BlockPos pos) {
        ResourceKey<Level> resourcekey = level.dimension() == ModDimensions.DEEP_DARK_LEVEL ? Level.OVERWORLD : ModDimensions.DEEP_DARK_LEVEL;
        ServerLevel serverlevel = level.getServer().getLevel(resourcekey);
        if (serverlevel == null) {
            return null;
        } else {
            boolean flag = serverlevel.dimension() == ModDimensions.DEEP_DARK_LEVEL;
            if (flag) {
                level = level.getServer().getLevel(ModDimensions.DEEP_DARK_LEVEL);
            } else {
                level = level.getServer().getLevel(Level.OVERWORLD);
            }
            WorldBorder worldborder = serverlevel.getWorldBorder();
            double d0 = DimensionType.getTeleportationScale(level.dimensionType(), serverlevel.dimensionType());
            BlockPos blockpos = worldborder.clampToBounds(entity.getX(), entity.getY(), entity.getZ());
            Optional<BlockPos> exitOptionalPos = findNearestPortal(serverlevel, true, blockpos, entity, worldborder);
            if (exitOptionalPos.isPresent()) {
                BlockPos exitPos = exitOptionalPos.get();
                ServerLevel finalLevel = level;
                return new TeleportTransition(level, new Vec3(exitPos.getX(), exitPos.getY(), exitPos.getZ()+2), Vec3.ZERO, 0f, 0f,
                        new TeleportTransition.PostTeleportTransition() {
                            @Override
                            public void onTransition(Entity entity) {
                                PortalToSculkDimensionLogical.tryOpenPortal(exitPos, finalLevel);
                            }
                        });
            }
        }

        return null;
    }

    protected Optional<BlockPos> findNearestPortal(ServerLevel level, boolean flag, BlockPos blockPos, Entity entity, WorldBorder worldBorder) {
        if (flag) {
            /*int radius = 2500;
            for (BlockPos pos : BlockPos.betweenClosed(blockPos.offset(-radius, -radius, -radius),
                    blockPos.offset(radius, radius, radius))) {
                BlockState state = level.getBlockState(pos);
                if (state.is(Blocks.REINFORCED_DEEPSLATE)) {
                    return new TeleportTransition(level, entity, new TeleportTransition.PostTeleportTransition() {
                        @Override public void onTransition(Entity entity) {}});
                }
            }*/
            //WardenModel
            level = level.getServer().getLevel(ModDimensions.DEEP_DARK_LEVEL);
            PoiManager poimanager = level.getPoiManager();
            Optional<BlockPos> xz = Optional.empty();
            for (int i = 250; xz.isEmpty(); i+=1500) {
                poimanager.ensureLoadedAndValid(level, blockPos, i);
                xz = poimanager.getInSquare(p_230634_ -> p_230634_.is(MyPoiTypes.ANCIENT_PORTAL_POI.getKey()), blockPos, i, PoiManager.Occupancy.ANY)
                        .map(PoiRecord::getPos)
                        .filter(worldBorder::isWithinBounds)
                        .min(Comparator.<BlockPos>comparingDouble(p_352046_ -> p_352046_.distSqr(blockPos)).thenComparingInt(Vec3i::getY));
            }

            return xz;
        } else {
            return Optional.empty();
        }
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity.canUsePortal(false)) {
            entity.setAsInsidePortal(this, pos);
            if (entity instanceof Player player) {
                //player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 60, 5));
                player.displayClientMessage(Component.translatable("Происходит телепортация, пожалуйста подождите!"), true);
            }
        }
    }
}
