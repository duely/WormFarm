package com.noobanidus.wormfarm.items;

import com.noobanidus.wormfarm.config.Registrar;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemWorm extends ItemFood {
    public ItemWorm() {
        super(1, 1, false);
        setAlwaysEdible();

        Registrar.addItem(this);
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
        if (!worldIn.isRemote) {
            player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 320, 0));
        }
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 8;
    }
}
