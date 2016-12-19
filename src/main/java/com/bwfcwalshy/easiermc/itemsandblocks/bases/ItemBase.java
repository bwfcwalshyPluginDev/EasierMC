package com.bwfcwalshy.easiermc.itemsandblocks.bases;

import com.bwfcwalshy.easiermc.itemsandblocks.EasierMCBase;

import org.bukkit.event.player.PlayerInteractEvent;

public interface ItemBase extends EasierMCBase {

    @Override
    default ItemBase copy() {
        return this;
    }

    default void onInteract(PlayerInteractEvent e) {
        return;
    }
}
