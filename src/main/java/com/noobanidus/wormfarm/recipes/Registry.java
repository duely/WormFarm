package com.noobanidus.wormfarm.recipes;

import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class Registry<T extends Registry.RegistryEntry> {
    private List<T> registry = new ArrayList<>();

    public List<T> getRegistryCopy() {
        return ImmutableList.copyOf(registry);
    }

    public boolean exists (ItemStack stack) {
        if (findEntry(stack) == getEmpty()) return false;

        return true;
    }

    public boolean exists (String string) {
        if (findEntry(string) == getEmpty()) return false;

        return true;
    }

    public T findEntry(ItemStack stack) {
        if (stack.isEmpty()) return null;

        return registry.stream().filter((e) -> e.compareStack(stack)).findFirst().orElse(getEmpty());
    }

    public T findEntry(String string) {
        return registry.stream().filter((s) -> s.compareString(string)).findFirst().orElse(getEmpty());
    }

    public abstract void replaceEntry (T entry);

    public boolean removeEntry(ItemStack stack) {
        T entry = findEntry(stack);
        if (entry == getEmpty()) return false;

        return registry.remove(entry);
    }

    public boolean removeEntry(String string) {
        T entry = findEntry(string);
        if (entry == getEmpty()) return false;

        return registry.remove(entry);
    }

    public boolean removeEntry(T entry) {
        return registry.remove(entry);
    }

    public void addEntry(T entry) {
        registry.add(entry);
    }

    public abstract T getEmpty();

    public static abstract class RegistryEntry {
        private final String type;
        private float matchModifier;
        private float conflictModifier;
        private int burnTime;

        public RegistryEntry(int burnTime, float matchModifier, float conflictModifier, String type) {
            this.matchModifier = matchModifier;
            this.conflictModifier = conflictModifier;
            this.burnTime = burnTime;
            this.type = type;
        }

        public int getBurnTime() {
            return burnTime;
        }

        public void setBurnTime(int burnTime) {
            this.burnTime = burnTime;
        }

        public float getMatchModifier() {
            return matchModifier;
        }

        public void setMatchModifier(float matchModifier) {
            this.matchModifier = matchModifier;
        }

        public float getConflictModifier() {
            return conflictModifier;
        }

        public void setConflictModifier(float conflictModifier) {
            this.conflictModifier = conflictModifier;
        }

        public String getType() {
            return this.type;
        }

        public abstract boolean compareStack(ItemStack stack);

        public abstract boolean isEmpty ();

        public abstract boolean compareString(String string);
    }
}
