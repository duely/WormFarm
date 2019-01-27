package com.noobanidus.wormfarm.recipes;

import com.noobanidus.wormfarm.WormFarm;
import net.minecraft.item.ItemStack;

public class OrganicRegistry extends Registry<OrganicRegistry.OrganicEntry> {
    private static OrganicRegistry instance = new OrganicRegistry();

    public static OrganicRegistry getInstance() {
        return instance;
    }

    public OrganicEntry getEmpty() {
        return OrganicEntry.EMPTY;
    }

    public void replaceEntry (OrganicEntry entry) {
        if (removeEntry(entry.getStack())) {
            addEntry(entry);
        } else {
            WormFarm.LOG.warn(String.format("Tried to modify a humidity entry `%s` but it didn't previously exist.", entry.getStack().getDisplayName()));
        }
    }

    @Override
    public void addEntry (OrganicEntry entry) {
        if (findEntry(entry.getStack()) != OrganicEntry.EMPTY) {
            WormFarm.LOG.warn(String.format("Attempted to register a duplicate organic item registry for item: %s", entry.getStack().getDisplayName()));
        } else {
            super.addEntry(entry);
        }
    }

    public OrganicEntry addEntry(ItemStack stack, String humidity, int burnTime, float matchModifier, float conflictModifier) {
        OrganicEntry entry = new OrganicEntry(stack, humidity, burnTime, matchModifier, conflictModifier);
        addEntry(entry);
        return entry;
    }

    public static class OrganicEntry extends ItemRegistryEntry {
        public static final OrganicEntry EMPTY = new OrganicEntry(ItemStack.EMPTY, null, -1, -1, -1);

        OrganicEntry(ItemStack stack, String humidity, int burnTime, float matchModifier, float conflictModifier) {
            super(stack, humidity, burnTime, matchModifier, conflictModifier, "organic");
        }

        public HumidityRegistry.MatchType matchType (SoilRegistry.SoilEntry entry) {
            return HumidityRegistry.getInstance().matchType(entry, this);
        }

        public boolean isEmpty () {
            return this == EMPTY;
        }
    }
}
