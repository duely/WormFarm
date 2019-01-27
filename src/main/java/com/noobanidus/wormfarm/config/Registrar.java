package com.noobanidus.wormfarm.config;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class Registrar {
    private static List<Item> ITEMS = new ArrayList<>();
    private static List<Block> BLOCKS = new ArrayList<>();

    public static void addItem (Item item) {
        ITEMS.add(item);
    }

    public static void addBlock (Block block) {
        addItem(new ItemBlock(block));
        BLOCKS.add(block);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {

    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
    }
}
