package com.noobanidus.wormfarm.tiles;

import com.noobanidus.wormfarm.common.WFInputSlotHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class TileEntityWormFarm extends TileEntity {
    private static final Capability<IItemHandler> CAP = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;

    private final IItemHandler outputSlots = new ItemStackHandler(16);
    private final WFInputSlotHandler inputSlots = new WFInputSlotHandler();

    private int tickingSoil = 0;
    private int tickingOrganic = 0;
    private float currentModifier = 0f;

    public TileEntityWormFarm () {
    }

    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing facing)
    {
        if (capability == CAP) {
            if (facing == EnumFacing.UP) {
                return CAP.cast(inputSlots);
            } else if (facing == EnumFacing.DOWN || facing == null) {
                return CAP.cast(outputSlots);
            }
        }

        return super.getCapability(capability, facing);
    }

    @Override
    public boolean hasCapability (@Nonnull Capability<?> capability, EnumFacing facing) {
        if (capability == CAP) {
            return true;
        }

        return super.hasCapability(capability, facing);
    }

    @Override
    public void onDataPacket (NetworkManager manager, SPacketUpdateTileEntity packet) {
        NBTTagCompound nbt = packet.getNbtCompound();
        tickingSoil = nbt.getInteger("tickingSoil");
        tickingOrganic = nbt.getInteger("tickingOrganic");
        currentModifier = nbt.getFloat("currentModifier");
        super.onDataPacket(manager, packet);
    }

    @Override
    @Nonnull
    public NBTTagCompound getUpdateTag () {
        NBTTagCompound nbt = super.getUpdateTag();
        nbt.setInteger("tickingSoil", tickingSoil);
        nbt.setInteger("tickingOrganic", tickingOrganic);
        nbt.setFloat("currentModifier", currentModifier);
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket () {
        return super.getUpdatePacket();
    }
}
