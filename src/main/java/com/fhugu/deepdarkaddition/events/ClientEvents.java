package com.fhugu.deepdarkaddition.events;

import com.fhugu.deepdarkaddition.MainScript;
import com.fhugu.deepdarkaddition.blocks.ModBlocks;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;

@EventBusSubscriber(value = Dist.CLIENT, modid = MainScript.MOD_ID)
public class ClientEvents {
    @SubscribeEvent
    public static void onRenderFog(ViewportEvent.RenderFog event) {
        if (event.getCamera().getEntity().level().dimension().location().toString().equals("deepdarkaddition:sculk_dimension")) {
            // Устанавливаем тип тумана (обычно FOG_TERRAIN)
            if (event.getMode() == FogRenderer.FogMode.FOG_TERRAIN) {
                // Дистанция, где туман начинает появляться (0.0 - прямо перед глазами)
                event.setNearPlaneDistance(0.0f);
                // Дистанция полной непрозрачности (чем меньше, тем гуще туман)
                event.setFarPlaneDistance(33.4f);

                // Обязательно отменяем событие, чтобы применить свои параметры
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onComputeFogColor(ViewportEvent.ComputeFogColor event) {
        // Проверяем, что игрок находится в вашем измерении
        // Замените "mod_id:my_dimension" на ваш ID
        if (event.getCamera().getEntity().level().dimension().location().toString().equals("deepdarkaddition:sculk_dimension")) {

            // Цвет задается в диапазоне от 0.0f до 1.0f
            event.setRed(0.01f);   // Мало красного
            event.setGreen(0.05f); // Много зеленого
            event.setBlue(0.125f);  // Немного синего

            // Получится ярко-бирюзовый/зеленоватый туман
        }
    }

    @SubscribeEvent
    public static void forAltColors(FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.SCULK_DIM_PORTAL_BLOCK.get(), RenderType.translucent());
    }
}
