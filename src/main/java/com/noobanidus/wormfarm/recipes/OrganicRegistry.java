package com.noobanidus.wormfarm.recipes;

import net.minecraft.item.ItemStack;

public class OrganicRegistry extends Registry<ItemRegistryEntry> {
    public static OrganicRegistry instance = new OrganicRegistry();

    public OrganicEntry getEmpty() {
        return OrganicEntry.EMPTY;
    }

    public static class OrganicEntry extends ItemRegistryEntry {
        public static final OrganicEntry EMPTY = new OrganicEntry(ItemStack.EMPTY, null, -1, -1, -1);

        OrganicEntry(ItemStack stack, String humidity, int burnTime, float matchModifier, float conflictModifier) {
            super(stack, humidity, burnTime, matchModifier, conflictModifier, "organic");
        }
    }
}
