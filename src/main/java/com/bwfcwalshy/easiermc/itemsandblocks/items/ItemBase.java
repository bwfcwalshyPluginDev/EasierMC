package com.bwfcwalshy.easiermc.itemsandblocks.items;

import com.bwfcwalshy.easiermc.itemsandblocks.EasierMCBase;
import org.bukkit.event.player.PlayerInteractEvent;

public interface ItemBase extends EasierMCBase {

    default void onInteract(PlayerInteractEvent e){
        return;
    }
}
