package com.noobanidus.wormfarm;

import com.noobanidus.wormfarm.config.CreativeTabWF;
import com.noobanidus.wormfarm.proxy.ISidedProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber
@Mod(modid = WormFarm.MODID, name = WormFarm.MODNAME, version = WormFarm.VERSION)
@SuppressWarnings("WeakerAccess")
public class WormFarm {
    public static final String MODID = "wormfarm";
    public static final String MODNAME = "Dude! Where's my Horse?";
    public static final String VERSION = "GRADLE:VERSION";

    public final static Logger LOG = LogManager.getLogger(MODID);

    public static CreativeTabWF TAB;

    @SidedProxy(clientSide = "com.noobanidus.wormfarm.proxy.ClientProxy", serverSide = "com.noobanidus.wormfarm.proxy.CommonProxy")
    public static ISidedProxy proxy;

    @SuppressWarnings("unused")
    @Mod.Instance(WormFarm.MODID)
    public static WormFarm instance;

    @SuppressWarnings("unused")

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @Mod.EventHandler
    public void loadComplete(FMLLoadCompleteEvent event) {
        proxy.loadComplete(event);
    }
}
