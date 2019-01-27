package com.noobanidus.wormfarm.common;

import com.noobanidus.wormfarm.recipes.OrganicRegistry;
import com.noobanidus.wormfarm.recipes.SoilRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class WFInputSlotHandler extends ItemStackHandler {
    private static int SOIL_SLOT = 0;
    private static int ORGANIC_SLOT = 1;
    private static int SLOT_MULTIPLIER = 4;

    public WFInputSlotHandler() {
        super(2);
    }

    @Override
    public int getSlotLimit (int slot) {
        return 64 * SLOT_MULTIPLIER;
    }

    @Override
    protected int getStackLimit (int slot, @Nonnull ItemStack stack) {
        if (stack.getMaxStackSize() == 1) return 1;

        return getSlotLimit(slot);
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        if (slot == SOIL_SLOT) {
            return SoilRegistry.getInstance().exists(stack);
        } else if (slot == ORGANIC_SLOT) {
            return OrganicRegistry.getInstance().exists(stack);
        } else {
            return false;
        }
    }

    public ItemStack consumeSoil () {
        return extractItem(SOIL_SLOT, 1, false);
    }

    public ItemStack consumeOrganic () {
        return extractItem(ORGANIC_SLOT, 1, false);
    }
}
