package com.fhugu.deepdarkaddition.events;

import com.fhugu.deepdarkaddition.MainScript;
import com.fhugu.deepdarkaddition.entitys.ModEntitys;
import com.fhugu.deepdarkaddition.entitys.entityclasses.CustomWarden;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySpawnReason;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

//@EventBusSubscriber(value = Dist.DEDICATED_SERVER, modid = MainScript.MOD_ID)
public class RegisterCommands {
    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("/startAnim")
                .executes(context -> {
                    context.getSource().sendSuccess(() -> Component.literal("Команда работает!"), true);

                    ServerLevel level = context.getSource().getLevel();

                    CustomWarden myEntity = ModEntitys.CUSTOM_WARDEN.get().create(level, EntitySpawnReason.COMMAND);
                    myEntity.moveTo(context.getSource().getPosition());
                    context.getSource().getLevel().addFreshEntity(myEntity);
                    myEntity.StartDeadAnim();
                    return 1;
                }));
    }
}
