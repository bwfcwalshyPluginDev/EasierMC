package com.bwfcwalshy.easiermc.itemsandblocks.blocks.machines;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.bwfcwalshy.easiermc.itemsandblocks.EasierMCBase.handler;

public enum Fuel {

    COAL_CHARCOAL(4000, new ItemStack(Material.COAL), new ItemStack(Material.COAL, (byte) 1)),
    SCRAP(870, handler.getItem("Scrap").getItem()),
    WOOD(750, new ItemStack(Material.WOOD), new ItemStack(Material.WOOD, (byte) 1), new ItemStack(Material.WOOD, (byte) 2), new ItemStack(Material.WOOD, (byte) 3)
            , new ItemStack(Material.WOOD, (byte) 4), new ItemStack(Material.WOOD, (byte) 5)),
    LOG(750, new ItemStack(Material.LOG), new ItemStack(Material.LOG, (byte) 1), new ItemStack(Material.LOG, (byte) 2), new ItemStack(Material.LOG, (byte) 3)
            , new ItemStack(Material.LOG_2), new ItemStack(Material.LOG_2, (byte) 1)),
    WOODEN_TOOLS(500, new ItemStack(Material.WOOD_PICKAXE), new ItemStack(Material.WOOD_SPADE), new ItemStack(Material.WOOD_AXE), new ItemStack(Material.WOOD_SWORD)
            , new ItemStack(Material.WOOD_HOE)),
    STICK(250, new ItemStack(Material.STICK)),
    NO_FUEL(0);

    private static List<ItemStack> allFuels;

    private int euValue;
    private ItemStack[] fuels;

    Fuel(int euValue, ItemStack... items) {
        this.euValue = euValue;
        this.fuels = items;
    }

    public static List<ItemStack> getAllFuels() {
        if (allFuels == null)
            allFuels = Arrays.stream(values()).flatMap(fuel -> Arrays.stream(fuel.getFuels())).collect(Collectors.toList());
        return allFuels;
    }

    public static Fuel getFuel(ItemStack is) {
        for (Fuel fuel : values()) {
            for (ItemStack fuelIs : fuel.getFuels()) {
                if (is.equals(fuelIs)) {
                    return fuel;
                }
            }
        }
        return Fuel.NO_FUEL;
    }

    public ItemStack[] getFuels() {
        return this.fuels;
    }

    public int getEuValue() {
        return this.euValue;
    }
}