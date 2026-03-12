package com.fhugu.deepdarkaddition.events;

import com.fhugu.deepdarkaddition.MainScript;
import com.fhugu.deepdarkaddition.entitys.ModEntitys;
import com.fhugu.deepdarkaddition.entitys.entityclasses.HungrySoul;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

@EventBusSubscriber(modid = MainScript.MOD_ID)
public class ModEvents {
    @SubscribeEvent
    public static void onWardenDied(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.getType() == EntityType.WARDEN) {
            Level level = entity.level();
            if (level instanceof ServerLevel serverLevel) {
                HungrySoul hungrySoul = ModEntitys.HUNGRY_SOUL.get().spawn(serverLevel, entity.getOnPos().offset(0,1,0), EntitySpawnReason.EVENT);
                if (hungrySoul != null) {
                    hungrySoul.setNoGravity(true);
                    hungrySoul.startOpeningPortal();
                    serverLevel.addFreshEntity(hungrySoul);
                }
            }
        }
    }
}
