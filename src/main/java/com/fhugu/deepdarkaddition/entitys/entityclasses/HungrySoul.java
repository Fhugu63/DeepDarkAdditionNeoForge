package com.fhugu.deepdarkaddition.entitys.entityclasses;

import com.fhugu.deepdarkaddition.engine.MyPoiTypes;
import com.fhugu.deepdarkaddition.portallogical.PortalToSculkDimensionLogical;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.border.WorldBorder;

import java.util.Comparator;

public class HungrySoul extends PathfinderMob {
    private BlockPos portalPos = null;
    private boolean openPortal = false;
    private int waitTicks = 100;

    public HungrySoul(EntityType<HungrySoul> entityType, Level level) {
        super(entityType, level);
    }


    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return null;
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(EquipmentSlot slot, ItemStack stack) {

    }

    @Override
    public HumanoidArm getMainArm() {
        return null;
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {

    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {

    }

    @Override
    public void tick() {
        if (this.level() instanceof ServerLevel serverLevel) {
            if (openPortal && this.blockPosition() == portalPos.offset(2, 3, 0) && waitTicks >= 0) {
                waitTicks--;
            }

            if (waitTicks == 30) {
                BlockPos portalPosToAcivateHim = portalPos.offset(0, 3, 0);
                this.navigation.moveTo(portalPosToAcivateHim.getX(), portalPosToAcivateHim.getY(), portalPosToAcivateHim.getZ(), 0.55);
            }

            if (waitTicks == 0) {
                this.remove(RemovalReason.DISCARDED);
                PortalToSculkDimensionLogical.tryOpenPortal(portalPos, serverLevel);
                openPortal = false;
                waitTicks = 100;
            }
        }
    }

    public void startOpeningPortal() {
        int i = 300;
        if (this.level() instanceof ServerLevel serverLevel) {
            BlockPos blockPos = this.blockPosition();
            WorldBorder worldborder = serverLevel.getWorldBorder();

            PoiManager poimanager = serverLevel.getPoiManager();
            BlockPos nearestPortalFramePos = null;

            poimanager.ensureLoadedAndValid(serverLevel, blockPos, i);
            nearestPortalFramePos = poimanager.getInSquare(p_230634_ -> p_230634_.is(MyPoiTypes.ANCIENT_PORTAL_POI.getKey()), blockPos, i, PoiManager.Occupancy.ANY)
                    .map(PoiRecord::getPos)
                    .filter(worldborder::isWithinBounds)
                    .min(Comparator.<BlockPos>comparingDouble(p_352046_ -> p_352046_.distSqr(blockPos)).thenComparingInt(Vec3i::getY)).get();

            this.remove(RemovalReason.DISCARDED);
            PortalToSculkDimensionLogical.tryOpenPortal(nearestPortalFramePos, serverLevel);
            /*
            portalPos = nearestPortalFramePos;

            //if (serverLevel.getBlockState(nearestPortalFramePos.offset(2,0,0)).getBlock() == Blocks.REINFORCED_DEEPSLATE) {
                BlockPos moveToPos = nearestPortalFramePos.offset(2,3,0);

                this.moveTo(moveToPos.getX(),moveToPos.getY(),moveToPos.getZ());
                waitTicks = 50;
                openPortal = true;*/
            //}
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 50.0)
                .add(Attributes.MOVEMENT_SPEED, 0.5)
                .add(Attributes.ARMOR_TOUGHNESS, 1.0)
                .add(Attributes.ATTACK_KNOCKBACK, 1.0)
                .add(Attributes.FOLLOW_RANGE, 3.0)
                .add(Attributes.ATTACK_DAMAGE, 3.5);
    }

}
