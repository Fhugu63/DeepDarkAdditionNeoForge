package com.fhugu.deepdarkaddition.entitys.visual.SculkCreeper;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.item.ItemStack;

public class SculkCreeperRenderState extends LivingEntityRenderState {
    public ItemStack stackInHand;

    public AnimationState reactToSoundState = new AnimationState();
    public boolean animationActive = false;
    public boolean showSoundWave = false;
}