package com.bwfcwalshy.easiermcnewinv.itemsandblocks.items;

import com.bwfcwalshy.easiermcnewinv.itemsandblocks.EasierMCBase;
import org.bukkit.event.player.PlayerInteractEvent;

public interface ItemBase extends EasierMCBase {

    default void onInteract(PlayerInteractEvent e){
        return;
    }
}
