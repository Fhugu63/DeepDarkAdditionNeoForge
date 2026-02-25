package com.fhugu.deepdarkaddition.items.ResearhDiarys;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.network.chat.Component;
import net.minecraft.client.Minecraft;

public class ResearhDiaryPartSixItem extends Item {
    public boolean flag = false;

    public ResearhDiaryPartSixItem(Properties properties) {
        super(properties);
    }
    //val diaryWindow: DiaryWindow = DiaryWindow(Component.translatable("test"), 1)

    @Override
    public InteractionResult use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        pPlayer.displayClientMessage(Component.translatable("diary.part6"), false);

        return super.use(pLevel, pPlayer, pUsedHand);
    }
}