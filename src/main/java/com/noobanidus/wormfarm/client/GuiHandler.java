package com.noobanidus.wormfarm.client;

import com.noobanidus.wormfarm.common.ContainerWormFarm;
import com.noobanidus.wormfarm.tiles.TileEntityWormFarm;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {
    public static final int WORM_FARM = 0;

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);

        if (!(te instanceof TileEntityWormFarm)) return null;

        switch (ID) {
            case WORM_FARM:
                return new ContainerWormFarm((TileEntityWormFarm) te, player.inventory);
        }

        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);

        if (!(te instanceof TileEntityWormFarm)) return null;

        switch (ID) {
            case WORM_FARM:
                return new GuiWormFarm(new ContainerWormFarm((TileEntityWormFarm) te, player.inventory));
        }

        return null;
    }
}
