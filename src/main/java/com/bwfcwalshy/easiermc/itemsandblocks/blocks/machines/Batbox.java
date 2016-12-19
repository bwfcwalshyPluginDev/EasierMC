package com.bwfcwalshy.easiermc.itemsandblocks.blocks.machines;

import com.bwfcwalshy.easiermc.itemsandblocks.Category;
import com.bwfcwalshy.easiermc.itemsandblocks.bases.MachineBase;
import com.bwfcwalshy.easiermc.utils.ItemStackBuilder;
import com.bwfcwalshy.easiermc.utils.StringUtil;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collections;

public class Batbox implements MachineBase {

    private final int STORAGE = 40000;
    private int currentEU = 0;
    private Inventory inventory;

    @Override
    public String getName() {
        return ChatColor.DARK_GRAY + "Batbox";
    }

    @Override
    public String getSimpleName() {
        return "Batbox";
    }

    @Override
    public Category getCategory() {
        return Category.MACHINE;
    }

    @Override
    public ItemStack getItem() {
        return new ItemStackBuilder("http://textures.minecraft.net/texture/6e6366e47831f107ac8a781df1bb3aa54f777612c9157d8be8e84803361e16f", getName())
                .setLore(Arrays.asList(ChatColor.GRAY + "Max I/O 32 EU/t", ChatColor.GRAY + "Capacity: 40,000 EU")).build();
    }

    @Override
    public int getEuCapacity() {
        return STORAGE;
    }

    @Override
    public int getEuOutput() {
        return 32;
    }

    @Override
    public int getEuInput() {
        return 32;
    }

    @Override
    public int getCurrentEu() {
        return currentEU;
    }

    @Override
    public void setCurrentEu(int currentEU) {
        this.currentEU = currentEU;
    }

    @Override
    public void onInteract(PlayerInteractEvent e) {
        e.setCancelled(true);
        e.getPlayer().openInventory(getInventory());
    }

    private Inventory getInventory() {
        if (inventory == null) {
            inventory = Bukkit.createInventory(null, 27, getName());

            for (int i = 0; i < 27; i++) {
                inventory.setItem(i, new ItemStackBuilder(Material.STAINED_GLASS_PANE, " ").setData(7).build());
            }

            for (int i = 10; i < 17; i++) {
                inventory.setItem(i, new ItemStackBuilder(Material.STAINED_GLASS_PANE, ChatColor.AQUA + "Status", Collections.singletonList(ChatColor.GRAY + "Storage: "
                        + StringUtil.getColorFromEnergy(currentEU, STORAGE) + currentEU + ChatColor.GRAY + "/" + ChatColor.AQUA + STORAGE + " EU")).setData(14).build());
            }
        }

        return inventory;
    }

    private void updateInventory() {
        if (inventory == null) inventory = getInventory();
        for (int i = 10; i < 17; i++) {
            inventory.setItem(i, new ItemStackBuilder(Material.STAINED_GLASS_PANE, ChatColor.AQUA + "Status", Collections.singletonList(ChatColor.GRAY + "Storage: "
                    + StringUtil.getColorFromEnergy(currentEU, STORAGE) + currentEU + ChatColor.GRAY + "/" + ChatColor.AQUA + STORAGE + " EU")).setData(14).build());
        }
    }

    @Override
    public void tick(Location location, int tick) {
        handleOutput(location);
        updateInventory();
    }

    @Override
    public void saveData(FileConfiguration data, String path) {
        data.set(path + ".EU", currentEU);
    }

    @Override
    public void loadData(FileConfiguration data, String path) {
        this.currentEU = data.getInt(path + ".EU");
    }
}
