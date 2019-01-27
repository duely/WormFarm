package com.noobanidus.wormfarm.recipes;

import net.minecraft.item.ItemStack;

public class SoilRegistry extends Registry<ItemRegistryEntry> {
    public static SoilRegistry instance = new SoilRegistry();

    public SoilEntry getEmpty() {
        return SoilEntry.EMPTY;
    }

    public static class SoilEntry extends ItemRegistryEntry {
        public static final SoilEntry EMPTY = new SoilEntry(ItemStack.EMPTY, null, -1, -1, -1);

        SoilEntry(ItemStack stack, String humidity, int burnTime, float matchModifier, float conflictModifier) {
            super(stack, humidity, burnTime, matchModifier, conflictModifier, "soil");
        }
    }
}
