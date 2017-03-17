package com.bwfcwalshy.easiermc.itemsandblocks.blocks.machines;

import com.bwfcwalshy.easiermc.itemsandblocks.Category;
import com.bwfcwalshy.easiermc.itemsandblocks.bases.MachineBase;
import com.bwfcwalshy.easiermc.utils.ItemStackBuilder;
import com.bwfcwalshy.easiermc.utils.StringUtil;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Furnace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.Arrays;
import java.util.Collections;

public class ElectricFurnace implements MachineBase {

    private int currentEU;
    private boolean burning;

    private FurnaceInventory inventory;
    private Furnace furnace;

    @Override
    public String getName() {
        return ChatColor.GRAY + "Electric Furnace";
    }

    @Override
    public String getSimpleName() {
        return "ElectricFurnace";
    }

    @Override
    public Category getCategory() {
        return Category.MACHINE;
    }

    @Override
    public ItemStack getItem() {
        //"http://textures.minecraft.net/texture/3b81998d8cb0257bd569a220304dd3862462f8e5a23e7b1d126fb98826f1ac9"
        return new ItemStackBuilder(Material.FURNACE, getName()).setLore(
                Collections.singletonList(ChatColor.GRAY + "Max EU Input: 32 EU/t")).autoUpdate().build();
    }

    @Override
    public Recipe getRecipe() {
        return null;
    }

    @Override
    public int getEuCapacity() {
        return 416;
    }

    @Override
    public int getEuOutput() {
        return 0;
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
    public void onInteract(PlayerInteractEvent e){
        if(inventory == null) inventory = getInventory(e.getClickedBlock().getLocation());
        e.setCancelled(true);
        e.getPlayer().openInventory(inventory);
    }

    @Override
    public void tick(Location location, int tick){
        if(inventory == null) inventory = getInventory(location);
        if(furnace.getCookTime() < 50) furnace.setCookTime((short) 50);
        if(furnace.getBurnTime() < 100) furnace.setBurnTime(Short.MAX_VALUE);

        if(furnace.getCookTime() > 55){
            if(currentEU < 3)
                furnace.setCookTime((short) (furnace.getCookTime()-1));
            else
                currentEU -= 3;
        }

        updateInventory(location);
    }

    private void updateInventory(Location location) {
        if(inventory == null) inventory = getInventory(location);

        inventory.setFuel(new ItemStackBuilder(Material.STAINED_GLASS_PANE, ChatColor.AQUA + "Status")
                .setLore(Collections.singletonList(ChatColor.GRAY + "Storage: " + StringUtil.getColorFromEnergy(currentEU, getEuCapacity())
                        + currentEU + ChatColor.GRAY + "/" + ChatColor.AQUA + getEuCapacity() + " EU")).setData(StringUtil.getGlassDataFromChatColor(
                        StringUtil.getColorFromEnergy(currentEU, getEuCapacity()))).build());
    }

    private FurnaceInventory getInventory(Location location){
        if(inventory == null){
            furnace = (Furnace) location.getBlock().getState();
            furnace.setCookTime((short) 100);
            furnace.setBurnTime(Short.MAX_VALUE);
            inventory = furnace.getInventory();

            inventory.setFuel(new ItemStackBuilder(Material.STAINED_GLASS_PANE, ChatColor.AQUA + "Status")
                    .setLore(Collections.singletonList(ChatColor.GRAY + "Storage: " + StringUtil.getColorFromEnergy(currentEU, getEuCapacity())
                            + currentEU + ChatColor.GRAY + "/" + ChatColor.AQUA + getEuCapacity() + " EU")).setData(StringUtil.getGlassDataFromChatColor(
                                    StringUtil.getColorFromEnergy(currentEU, getEuCapacity()))).build());
        }
        return inventory;
    }

    @Override
    public void saveData(FileConfiguration data, String path){
        data.set(path + ".EU", currentEU);
    }

    @Override
    public void loadData(FileConfiguration data, String path){
        currentEU = data.getInt(path + ".EU", currentEU);
    }

    @Override
    public ElectricFurnace copy(){
        ElectricFurnace clone = new ElectricFurnace();
        clone.currentEU = 0;
        return clone;
    }
}
