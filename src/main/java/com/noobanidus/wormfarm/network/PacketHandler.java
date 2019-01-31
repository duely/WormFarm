package com.noobanidus.wormfarm.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

// This code is probably familiar with... I don't know, everyone?
public class PacketHandler {
    public static SimpleNetworkWrapper INSTANCE;
    private static int id = 0;

    public static int nextID() {
        return id++;
    }

    public static void registerMessages(String channelName) {
        INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(channelName);
    }
}
