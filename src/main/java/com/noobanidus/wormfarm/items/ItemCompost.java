package com.noobanidus.wormfarm.items;

import com.noobanidus.wormfarm.WormFarm;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class ItemCompost extends Item {
    public ItemCompost() {
        setCreativeTab(WormFarm.TAB);
        setRegistryName(new ResourceLocation(WormFarm.MODID, "compost"));
        setTranslationKey("compost");
    }

    @Override
    @Nonnull
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack itemstack = player.getHeldItem(hand);

        if (!player.canPlayerEdit(pos.offset(facing), facing, itemstack)) {
            return EnumActionResult.FAIL;
        } else if (ItemDye.applyBonemeal(itemstack, worldIn, pos)) {
            if (!worldIn.isRemote) {
                worldIn.playEvent(2005, pos, 0);
            }

            return EnumActionResult.SUCCESS;
        }

        return EnumActionResult.PASS;
    }

}
