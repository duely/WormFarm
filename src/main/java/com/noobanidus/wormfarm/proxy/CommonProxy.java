package com.noobanidus.wormfarm.proxy;

import com.noobanidus.wormfarm.WormFarm;
import com.noobanidus.wormfarm.client.GuiHandler;
import com.noobanidus.wormfarm.config.CreativeTabWF;
import com.noobanidus.wormfarm.config.Registrar;
import com.noobanidus.wormfarm.tiles.TileEntityWormFarm;
import mcjty.theoneprobe.network.PacketHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy implements ISidedProxy {
    public void preInit(FMLPreInitializationEvent event) {
        WormFarm.TAB = new CreativeTabWF(CreativeTabs.getNextID(), WormFarm.MODID);
        PacketHandler.registerMessages(WormFarm.MODID);
        NetworkRegistry.INSTANCE.registerGuiHandler(WormFarm.instance, WormFarm.GUI);
    }

    public void init(FMLInitializationEvent e) {
        GameRegistry.registerTileEntity(TileEntityWormFarm.class, new ResourceLocation(WormFarm.MODID, "wormfarm"));
    }

    public void postInit(FMLPostInitializationEvent e) {
    }

    public void loadComplete(FMLLoadCompleteEvent event) {
    }
}