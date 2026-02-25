package com.fhugu.deepdarkaddition.entitys.EntitysAI;

import com.fhugu.deepdarkaddition.entitys.entityclasses.SculkCreeper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.warden.Warden;

import java.util.UUID;

public class SculkCreeperAI {
    public int numOfAngry = 0;
    public int cooldownToAbility = 0;
    public final int maxCooldownToAbility = 60;
    public double speedModifier = 0;
    public double basicSpeed = 0.7;
    public double nowSpeed = basicSpeed;
    public UUID targetUUID = null;

    public LivingEntity target = null;

    public BlockPos walkTo = null;

    public StepOfAngry stepOfAngry = StepOfAngry.NEUTRAL;

    public Purposes hasAPurposeNow = Purposes.NONE;

    public enum StepOfAngry {
        NEUTRAL,
        MEDIUMANGRY,
        ANGRY
    }

    public enum Purposes {
        NONE,
        LIVE,
        WALKTO,
        FINDTARGET,
        ATTACK
    }

    public void tick(SculkCreeper sculkCreeper,ServerLevel serverLevel) {
        target = sculkCreeper.getTarget();
        basicSpeed = basicSpeed + speedModifier;
        if (hasAPurposeNow != Purposes.NONE) {
            switch (hasAPurposeNow) {
                case WALKTO -> {
                    sculkCreeper.walkTo(walkTo.getX(), walkTo.getY(), walkTo.getZ(), nowSpeed);
                    if (sculkCreeper.tickCount % 20 == 0) {
                        numOfAngry++;
                    }
                }
                case ATTACK -> {
                    sculkCreeper.attackTarget();
                    numOfAngry+=2;
                }
            }

        }

        if (sculkCreeper.getTarget() != null) {
            if (numOfAngry >= 60) {
                this.speedModifier = 0.5;
            } else {
                this.speedModifier = 0;
            }
        } else if (sculkCreeper.tickCount % 20 == 0) {
            numOfAngry--;
        }

        if (sculkCreeper.tickCount % 40 == 0) {
            Warden.applyDarknessAround(serverLevel, sculkCreeper.position(), sculkCreeper, 16);

        }

        if (numOfAngry <= 30) {
            stepOfAngry = StepOfAngry.NEUTRAL;
        } else if (numOfAngry <= 80) {
            stepOfAngry = StepOfAngry.MEDIUMANGRY;
        } else {
            stepOfAngry = StepOfAngry.ANGRY;
        }

        if (cooldownToAbility > 0) {
            cooldownToAbility--;
        }
    }

    public void resetCooldownToAbility() {
        cooldownToAbility = maxCooldownToAbility;
    }
}
