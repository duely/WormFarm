package com.noobanidus.wormfarm.recipes;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class Registry<T extends RegistryEntry> {
    private List<T> registry = new ArrayList<>();

    public List<T> getRegistry() {
        return registry;
    }

    public T findEntry(ItemStack stack) {
        if (stack.isEmpty()) return null;

        return registry.stream().filter((e) -> e.compareStack(stack)).findFirst().orElse(getEmpty());
    }

    public T findEntry(String string) {
        return registry.stream().filter((s) -> s.compareString(string)).findFirst().orElse(getEmpty());
    }

    public boolean removeEntry(ItemStack stack) {
        T entry = findEntry(stack);
        if (entry == null) return false;

        return registry.remove(entry);
    }

    public boolean removeEntry(T entry) {
        return registry.remove(entry);
    }

    public void addEntry(T entry) {
        registry.add(entry);
    }

    public abstract T getEmpty();
}
