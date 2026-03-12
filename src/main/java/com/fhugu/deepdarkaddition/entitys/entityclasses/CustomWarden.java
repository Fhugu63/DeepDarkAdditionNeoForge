package com.fhugu.deepdarkaddition.entitys.entityclasses;

import com.fhugu.deepdarkaddition.entitys.visual.CustomWarden.CustomWardenRendererState;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CustomWarden extends LivingEntity {
    //public CustomWardenRendererState rendererState = new CustomWardenRendererState();
    public AnimationState deadAnimState = new AnimationState();

    public CustomWarden(EntityType<CustomWarden> entityType, Level level) {
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

    public void StartDeadAnim() {
        this.deadAnimState.start(1000);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 50.0)
                .add(Attributes.MOVEMENT_SPEED, 0.0)
                .add(Attributes.ARMOR_TOUGHNESS, 10.0)
                .add(Attributes.ATTACK_KNOCKBACK, 1.0)
                .add(Attributes.FOLLOW_RANGE, 3.0)
                .add(Attributes.ATTACK_DAMAGE, 3.5);
    } //CustomWardenDeadAnimation.dead_animation.;
}
