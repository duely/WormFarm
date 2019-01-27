package com.noobanidus.wormfarm.recipes;

import com.noobanidus.wormfarm.WormFarm;
import net.minecraft.item.ItemStack;

public class SoilRegistry extends Registry<SoilRegistry.SoilEntry> {
    private static SoilRegistry instance = new SoilRegistry();

    public static SoilRegistry getInstance() {
        return instance;
    }

    public SoilEntry getEmpty() {
        return SoilEntry.EMPTY;
    }

    @Override
    public void addEntry (SoilEntry entry) {
        if (findEntry(entry.getStack()) != SoilEntry.EMPTY) {
            WormFarm.LOG.warn(String.format("Attempted to register a duplicate soil item registry for item: %s", entry.getStack().getDisplayName()));
        } else {
            super.addEntry(entry);
        }
    }

    public SoilEntry addEntry(ItemStack stack, String humidity, int burnTime, float matchModifier, float conflictModifier) {
        SoilEntry entry = new SoilEntry(stack, humidity, burnTime, matchModifier, conflictModifier);
        addEntry(entry);
        return entry;
    }

    public static class SoilEntry extends ItemRegistryEntry {
        public static final SoilEntry EMPTY = new SoilEntry(ItemStack.EMPTY, null, -1, -1, -1);

        SoilEntry(ItemStack stack, String humidity, int burnTime, float matchModifier, float conflictModifier) {
            super(stack, humidity, burnTime, matchModifier, conflictModifier, "soil");
        }

        public HumidityRegistry.MatchType matchType (OrganicRegistry.OrganicEntry entry) {
            return HumidityRegistry.getInstance().matchType(this, entry);
        }
    }
}
