package com.bwfcwalshy.easiermc.itemsandblocks.items;

import com.bwfcwalshy.easiermc.itemsandblocks.EasierMCBase;
import org.bukkit.event.player.PlayerInteractEvent;

public interface ItemBase extends EasierMCBase {

    void onInteract(PlayerInteractEvent e);
}
