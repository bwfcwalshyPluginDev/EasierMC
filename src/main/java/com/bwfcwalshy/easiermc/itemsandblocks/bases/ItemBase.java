package com.bwfcwalshy.easiermc.itemsandblocks.bases;

import org.bukkit.event.player.PlayerInteractEvent;

import com.bwfcwalshy.easiermc.itemsandblocks.EasierMCBase;

public interface ItemBase extends EasierMCBase {

    @Override
    default ItemBase copy() {
        return this;
    }

    default void onInteract(PlayerInteractEvent e) {
        return;
    }
}
