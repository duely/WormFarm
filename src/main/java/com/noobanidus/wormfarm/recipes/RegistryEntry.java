package com.noobanidus.wormfarm.recipes;

import net.minecraft.item.ItemStack;

public abstract class RegistryEntry {
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

    public abstract boolean compareString(String string);
}
