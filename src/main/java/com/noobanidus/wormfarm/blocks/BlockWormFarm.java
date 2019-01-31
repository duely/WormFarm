package com.noobanidus.wormfarm.blocks;

import com.noobanidus.wormfarm.WormFarm;
import com.noobanidus.wormfarm.client.GuiHandler;
import com.noobanidus.wormfarm.config.Registrar;
import com.noobanidus.wormfarm.tiles.TileEntityWormFarm;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class BlockWormFarm extends Block implements ITileEntityProvider  {

    public BlockWormFarm () {
        super(Material.WOOD);
        setCreativeTab(WormFarm.TAB);
        setTranslationKey("wormfarm");
        setRegistryName(new ResourceLocation(WormFarm.MODID, "wormfarm"));
        setHardness(2.0F);
        setSoundType(SoundType.WOOD);

        Registrar.addBlock(this);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)   {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileEntityWormFarm) {
            player.openGui(WormFarm.instance, GuiHandler.WORM_FARM, world, pos.getX(), pos.getY(), pos.getZ());

            return true;
        }

        return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
    }

    // TODO: Bounding box

    @Override
    public TileEntity createNewTileEntity (@Nonnull World world, int meta) {
        return new TileEntityWormFarm();
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isBlockNormalCube (IBlockState state) {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isOpaqueCube (IBlockState state) {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isFullCube (IBlockState state) {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    @Nonnull
    public BlockRenderLayer getRenderLayer () {
        return BlockRenderLayer.CUTOUT;
    }
}
