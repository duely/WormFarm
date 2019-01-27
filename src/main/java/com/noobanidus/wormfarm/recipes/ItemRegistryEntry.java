package com.noobanidus.wormfarm.recipes;

import com.noobanidus.wormfarm.util.NBTComparisonUtil;
import net.minecraft.item.ItemStack;

public abstract class ItemRegistryEntry extends RegistryEntry {
    private ItemStack stack;
    private String humidity;

    public ItemRegistryEntry(ItemStack stack, String humidity, int burnTime, float matchModifier, float conflictModifier, String type) {
        super(burnTime, matchModifier, conflictModifier, type);
        this.stack = stack;
        this.humidity = humidity;
    }

    public boolean compareStack(ItemStack otherStack) {
        return NBTComparisonUtil.StackMatch(otherStack, stack, true, false);
    }

    public boolean compareString(String string) {
        return humidity.equalsIgnoreCase(string);
    }
}
