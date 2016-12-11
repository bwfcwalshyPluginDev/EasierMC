package com.bwfcwalshy.easiermc.itemsandblocks.items;

import org.bukkit.event.player.PlayerInteractEvent;

import com.bwfcwalshy.easiermc.itemsandblocks.EasierMCBase;

public interface ItemBase extends EasierMCBase {

    @Override
    ItemBase copy();

    default void onInteract(PlayerInteractEvent e){
        return;
    }
}
