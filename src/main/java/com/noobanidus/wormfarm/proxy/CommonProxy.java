package com.noobanidus.wormfarm.proxy;

import com.noobanidus.wormfarm.WormFarm;
import com.noobanidus.wormfarm.config.CreativeTabWF;
import com.noobanidus.wormfarm.config.Registrar;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy implements ISidedProxy {
    public void preInit(FMLPreInitializationEvent event) {
        WormFarm.TAB = new CreativeTabWF(CreativeTabs.getNextID(), WormFarm.MODID);
        Registrar.preInit();
    }

    public void init(FMLInitializationEvent e) {
    }

    public void postInit(FMLPostInitializationEvent e) {
    }

    public void loadComplete(FMLLoadCompleteEvent event) {
    }
}