package com.fhugu.deepdarkaddition.items.ResearhDiarys;

import com.fhugu.deepdarkaddition.MainScript;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.network.chat.Component;

public class ResearhDiaryPartOneItem extends Item {
    public boolean flag = false;
    //val diaryWindow: DiaryWindow = DiaryWindow(Component.translatable("test"), 1)

    @Override
    public InteractionResult use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        pPlayer.displayClientMessage(Component.translatable("diary.part1"), false);

        return super.use(pLevel, pPlayer, pUsedHand);
    }

    public ResearhDiaryPartOneItem(Properties properties) {
        super(properties);
        properties.effectiveModel().compareNamespaced(ResourceLocation.fromNamespaceAndPath(MainScript.MOD_ID, "items/rdp_one"));
    }
}