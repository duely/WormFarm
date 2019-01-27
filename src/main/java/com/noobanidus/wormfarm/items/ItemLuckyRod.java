package com.noobanidus.wormfarm.items;

import com.noobanidus.wormfarm.WormFarm;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.util.ResourceLocation;

public class ItemLuckyRod extends ItemFishingRod {
    public ItemLuckyRod () {
        setCreativeTab(WormFarm.TAB);
        setRegistryName(new ResourceLocation(WormFarm.MODID, "lucky_rod"));
        setTranslationKey("lucky_rod");
    }
}
