package com.noobanidus.wormfarm.events;

import com.noobanidus.wormfarm.WormFarm;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class EventHandler {
    @SubscribeEvent
    public static void onDismount(EntityMountEvent event) {
        WormFarm.steedProxy.onDismount(event);
    }

    @SubscribeEvent
    public static void onInteractCarrot(PlayerInteractEvent.EntityInteract event) {
        ItemEnchantedCarrot.onInteractCarrot(event);
    }

    @SubscribeEvent
    public static void onInteractOcarina(PlayerInteractEvent.EntityInteract event) {
        ItemOcarina.onInteractOcarina(event);
    }

    @SubscribeEvent
    public static void onAttack(AttackEntityEvent event) {
        EntityPlayer player = event.getEntityPlayer();
        ItemStack is = player.getHeldItemMainhand();
        if (is.isEmpty()) return;

        Item item = is.getItem();

        if (item instanceof ItemOcarina || item instanceof ItemEnchantedCarrot) {
            event.setCanceled(true);
        }
    }
}
