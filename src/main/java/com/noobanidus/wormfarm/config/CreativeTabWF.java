package com.noobanidus.wormfarm.config;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CreativeTabWF extends CreativeTabs {
    public CreativeTabWF(int id, String id2) {
        super(id, id2);
    }

    @SideOnly(Side.CLIENT)
    public ItemStack createIcon() {
        //return new ItemStack(Registrar.ocarina);
        return new ItemStack(Items.APPLE, 1, 0);
    }
}
