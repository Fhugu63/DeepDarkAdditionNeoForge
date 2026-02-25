package com.fhugu.deepdarkaddition.items;

import com.fhugu.deepdarkaddition.MainScript;
import com.fhugu.deepdarkaddition.items.ResearhDiarys.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MainScript.MOD_ID);

    //val mythycalRarity = Rarity.create("MYTHICAL", ChatFormatting.DARK_RED)

    public static final DeferredItem<Item> EXAMPLE_ITEM = ITEMS.registerItem(
            "example_item",
            Item::new, // The factory that the properties will be passed into.
            new Item.Properties() // The properties to use.
    );

    public static DeferredItem<Item> RESEARHDIARYPARTONE = ITEMS.registerItem("rdp_one",
            ResearhDiaryPartOneItem::new,
            new Item.Properties().rarity(Rarity.RARE)
    );

    public static DeferredItem<Item> RESEARHDIARYPARTTWO = ITEMS.registerItem("rdp_two",
            ResearhDiaryPartTwoItem::new,
            new Item.Properties().rarity(Rarity.RARE)
    );

    public static DeferredItem<Item> RESEARHDIARYPARTTHREE = ITEMS.registerItem("rdp_three",
            ResearhDiaryPartThreeItem::new,
            new Item.Properties().rarity(Rarity.RARE)
    );

    public static DeferredItem<Item> RESEARHDIARYPARTFOUR = ITEMS.registerItem("rdp_four",
            ResearhDiaryPartFourItem::new,
            new Item.Properties().rarity(Rarity.RARE)
    );

    public static DeferredItem<Item> RESEARHDIARYPARTFIVE = ITEMS.registerItem("rdp_five",
            ResearhDiaryPartFiveItem::new,
            new Item.Properties().rarity(Rarity.RARE)
    );

    public static DeferredItem<Item> RESEARHDIARYPARTSIX = ITEMS.registerItem("rdp_six",
            ResearhDiaryPartSixItem::new,
            new Item.Properties().rarity(Rarity.RARE)
    );

    public static DeferredItem<Item> RESEARHDIARYPARTSEVEN = ITEMS.registerItem("rdp_seven",
            ResearhDiaryPartSevenItem::new,
            new Item.Properties().rarity(Rarity.RARE)
    );
}
