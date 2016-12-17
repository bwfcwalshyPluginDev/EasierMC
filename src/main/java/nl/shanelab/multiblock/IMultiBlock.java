package nl.shanelab.multiblock;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * The multiblock structure holder, used to describe the pattern and
 * what do to on activation
 *
 * @author ShaneCraft
 */
public interface IMultiBlock {

    void onActivate(Plugin plugin, Location location, Player player, MultiBlockActivation activation);

    MultiBlockPattern getMultiBlockPattern();
}
