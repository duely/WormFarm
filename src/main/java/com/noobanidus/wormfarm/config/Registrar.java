package com.noobanidus.wormfarm.config;

import com.noobanidus.wormfarm.WormFarm;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class Registrar {
    private static List<Item> ITEMS = new ArrayList<>();
    private static List<Block> BLOCKS = new ArrayList<>();

    public static void addItem (@Nonnull Item item) {
        ITEMS.add(item);
    }

    public static void addBlock (@Nonnull Block block) {
        assert block.getRegistryName() != null;

        addItem(new ItemBlock(block).setRegistryName(block.getRegistryName()));
        BLOCKS.add(block);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        ITEMS.forEach(event.getRegistry()::register);
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        BLOCKS.forEach(event.getRegistry()::register);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(Blocks.wormFarm), 0, new ModelResourceLocation(new ResourceLocation(WormFarm.MODID, "wormfarm"), "inventory"));
    }
}
