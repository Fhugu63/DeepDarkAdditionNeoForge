package com.fhugu.deepdarkaddition.portallogical;

import com.fhugu.deepdarkaddition.blocks.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Portal;
import net.minecraft.world.level.portal.TeleportTransition;
import org.jetbrains.annotations.Nullable;

public class PortalToSculkDimensionLogical implements Portal {
    static int x = 0;
    static int y = 0;
    static int z = 0;

    public static void tryOpenPortal(BlockPos blockPos, ServerLevel level) {
        boolean canCreatePortal = false;


            canCreatePortal = checkPortalShape(blockPos, "x", level);

        if (canCreatePortal) {
            //y 6, x 20
            int x1 = x-20;
            int y1 = y-6;
            BlockPos corner1 = new BlockPos(x-1, y-1, z);
            BlockPos corner2 = new BlockPos(x1, y1, z);

            for (BlockPos targetPos : BlockPos.betweenClosed(corner1, corner2)) {
                level.setBlock(targetPos, ModBlocks.SCULK_DIM_PORTAL_BLOCK.get().defaultBlockState(), 3);
            }

        }

        System.out.println(canCreatePortal);
    }

    private static boolean checkPortalShape(BlockPos blockPos, String front, ServerLevel level) {
        boolean down_front = false;
        boolean right_front = false;
        boolean up_front = false;
        boolean left_front = false;

        if (front == "x") {
            //check portal frame with facing portal +x
            x = blockPos.getX();
            y = blockPos.getY();
            z = blockPos.getZ();

            for (int i = 0; i!=30;i++) {
                BlockPos nowBlockPos = new BlockPos(x,y,z);
                if (level.getBlockState(nowBlockPos).getBlock() == Blocks.REINFORCED_DEEPSLATE) {
                    x++;
                } else {
                    down_front=true;
                    x--;
                    break;
                }
            }
            for (int i = 0; i!=30;i++) {
                BlockPos nowBlockPos = new BlockPos(x,y,z);
                if (level.getBlockState(nowBlockPos).getBlock() == Blocks.REINFORCED_DEEPSLATE) {
                    y++;
                } else {
                    right_front=true;
                    y--;
                    break;
                }
            }

            if (right_front && down_front) {
                return true;
            }
        }
        return false;
    }

    @Override
    public @Nullable TeleportTransition getPortalDestination(ServerLevel level, Entity entity, BlockPos pos) {
        return null;
    }
}
