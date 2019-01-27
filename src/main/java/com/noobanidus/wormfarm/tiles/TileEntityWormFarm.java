package com.noobanidus.wormfarm.tiles;

import com.noobanidus.wormfarm.common.WFInputSlotHandler;
import com.noobanidus.wormfarm.recipes.HumidityRegistry;
import com.noobanidus.wormfarm.recipes.OrganicRegistry;
import com.noobanidus.wormfarm.recipes.SoilRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import static com.noobanidus.wormfarm.recipes.HumidityRegistry.MatchType;
import static com.noobanidus.wormfarm.recipes.SoilRegistry.SoilEntry;
import static com.noobanidus.wormfarm.recipes.OrganicRegistry.OrganicEntry;

import javax.annotation.Nonnull;

public class TileEntityWormFarm extends TileEntity implements ITickable {
    private static final Capability<IItemHandler> ITEM_CAPABILITY = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
    private static final Capability<IFluidHandler> FLUID_CAPABILITY = CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;

    private final ItemStackHandler outputSlots = new ItemStackHandler(9);
    private final WFInputSlotHandler inputSlots = new WFInputSlotHandler();
    private final FluidTank tank = new FluidTank(Fluid.BUCKET_VOLUME * 4);

    private int tickingSoil = 0;
    private int tickingOrganic = 0;
    private float currentModifier = 0f;

    private MatchType lastMatch = MatchType.INVALID;
    private SoilEntry lastSoil = SoilEntry.EMPTY;
    private OrganicEntry lastOrganic = OrganicEntry.EMPTY;

    private int baseWormChance = 15;
    private int baseCompostChance = 30;
    private int specialChance = 25;

    public TileEntityWormFarm () {
    }

    public boolean canTick () {
        return Math.min(tickingSoil, tickingOrganic) > 0;
    }

    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing facing)
    {
        if (capability == ITEM_CAPABILITY) {
            if (facing == EnumFacing.UP) {
                return ITEM_CAPABILITY.cast(inputSlots);
            } else {
                return ITEM_CAPABILITY.cast(outputSlots);
            }
        } else if (capability == FLUID_CAPABILITY) {
            return FLUID_CAPABILITY.cast(tank);
        }

        return super.getCapability(capability, facing);
    }

    @Override
    public boolean hasCapability (@Nonnull Capability<?> capability, EnumFacing facing) {
        if (capability == ITEM_CAPABILITY || capability == FLUID_CAPABILITY) {
            return true;
        }

        return super.hasCapability(capability, facing);
    }

    @Override
    public void readFromNBT (NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        outputSlots.deserializeNBT(nbt.getCompoundTag("outputSlots"));
        inputSlots.deserializeNBT(nbt.getCompoundTag("inputSlots"));
        tank.readFromNBT(nbt.getCompoundTag("tank"));
        tickingSoil = nbt.getInteger("tickingSoil");
        tickingOrganic = nbt.getInteger("tickingOrganic");
        currentModifier = nbt.getFloat("currentModifier");
    }

    @Override
    @Nonnull
    public NBTTagCompound writeToNBT (NBTTagCompound nbt) {
        nbt = super.writeToNBT(nbt);
        nbt.setTag("outputSlots", outputSlots.serializeNBT());
        nbt.setTag("inputSlots", inputSlots.serializeNBT());
        nbt.setTag("tank", tank.writeToNBT(new NBTTagCompound()));
        nbt.setInteger("tickingSoil", tickingSoil);
        nbt.setInteger("tickingOrganic", tickingOrganic);
        nbt.setFloat("currentModifier", currentModifier);
        return nbt;
    }

    @Override
    public void onDataPacket (NetworkManager manager, SPacketUpdateTileEntity packet) {
        readFromNBT(packet.getNbtCompound());
        world.markBlockRangeForRenderUpdate(pos, pos);
    }

    @Override
    @Nonnull
    public NBTTagCompound getUpdateTag () {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket () {
        return super.getUpdatePacket();
    }

    @Override
    public void update() {
        if (!canTick()) {
            SoilEntry entrySoil;
            OrganicEntry entryOrganic;

            ItemStack stack;
            if (tickingSoil == 0) {
                stack = inputSlots.consumeSoil();
                if (stack.isEmpty()) return;
                if (!lastSoil.isEmpty() && lastSoil.compareStack(stack)) {
                    entrySoil = lastSoil;
                } else {
                    entrySoil = SoilRegistry.getInstance().findEntry(stack);
                }
                // TODO: Is this actually necessary or does the GC handle it?
                stack.shrink(1);
            } else {
                entrySoil = lastSoil;
            }

            if (tickingOrganic == 0) {
                stack = inputSlots.consumeOrganic();
                if (stack.isEmpty()) return;
                if (!lastOrganic.isEmpty() && lastOrganic.compareStack(stack)) {
                    entryOrganic = lastOrganic;
                } else {
                    entryOrganic = OrganicRegistry.getInstance().findEntry(stack);
                }
                // TODO: See above
                stack.shrink(1);
            } else {
                entryOrganic = lastOrganic;
            }

            if (entrySoil.isEmpty() || entryOrganic.isEmpty()) return; // TODO: At this point this should never happen

            MatchType match = HumidityRegistry.getInstance().matchType(entrySoil, entryOrganic);

            float newModifier;

            if (match == MatchType.NEUTRAL) {
                newModifier = 1;
            } else if (match == MatchType.CONFLICT) {
                newModifier = entrySoil.getConflictModifier() + entryOrganic.getConflictModifier();
                if (newModifier >= 1) newModifier = 0.5f;
            } else if (match == MatchType.MATCH) {
                newModifier = entrySoil.getMatchModifier() + entryOrganic.getMatchModifier();
                if (newModifier < 1) newModifier = 1;
            } else { // if (match == MatchType.INVALID) {
                return; // TODO: Error messages here
            }

            lastMatch = match;
            currentModifier = newModifier;
            lastOrganic = entryOrganic;
            lastSoil = entrySoil;

            // Items are already consumed
            if (tickingOrganic == 0) {
                tickingOrganic = (int) (currentModifier * entryOrganic.getBurnTime());
            } else if (tickingSoil == 0) {
                tickingSoil = (int) (currentModifier * entrySoil.getBurnTime());
            }
        } else {
            tickingSoil--;
            tickingOrganic--;

            int chance = world.rand.nextInt(100);

            if ((int) (currentModifier * baseWormChance) >= chance) {
                // TODO: Spawn a worm!
            }

            if ((int) (currentModifier * baseCompostChance) >= chance) {
                // TODO: Spawn some compost!
            }

            if (lastMatch == MatchType.MATCH && specialChance >= chance) {
                // TODO: Spawn a special item!
            }
        }
    }
}
