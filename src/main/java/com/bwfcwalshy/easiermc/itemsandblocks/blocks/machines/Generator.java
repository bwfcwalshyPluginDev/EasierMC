package com.bwfcwalshy.easiermc.itemsandblocks.blocks.machines;

import com.bwfcwalshy.easiermc.itemsandblocks.Category;
import com.bwfcwalshy.easiermc.itemsandblocks.bases.MachineBase;
import com.bwfcwalshy.easiermc.recipe.AdvancedRecipe;
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
import org.bukkit.inventory.Recipe;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Generator implements MachineBase {

    private final int STORAGE = 4000;
    // How long the fuel will burn for
    private final int BURN_TIME = 100;
    private Inventory inventory;
    private int currentEU = 0;
    private int currentBurnTime = 0;
    private ItemStack currentFuelItem = null;
    private Fuel currentFuel;
    private boolean burning = false;
    private String id = "12616a70e8f74ccb6f7f44e4aee5dbcbbcb80c9f23e1dccb07a5f156521215";

    private int instance = -1;

    @Override
    public String getSimpleName() {
        return "Generator";
    }

    @Override
    public String getName() {
        return ChatColor.GRAY + "Generator";
    }

    @Override
    public Category getCategory() {
        return Category.MACHINE;
    }

    @Override
    public ItemStack getItem() {
        return new ItemStackBuilder("http://textures.minecraft.net/texture/12616a70e8f74ccb6f7f44e4aee5dbcbbcb80c9f23e1dccb07a5f156521215", getName())
                .setLore(Collections.singletonList(ChatColor.GRAY + "Generate electricity by burning fuel sources.")).autoUpdate().build();
    }

    @Override
    public Recipe getRecipe() {
        return new AdvancedRecipe(getItem(), " i ", "rrr", "   ").setIngredient('i', Material.IRON_BLOCK).setIngredient('r', Material.IRON_INGOT);
    }

    @Override
    public Generator copy() {
        instance++;
        Generator clone = new Generator();
        clone.inventory = cloneInventory(inventory);
        clone.instance = instance;
        return clone;
    }

    @Override
    public void onInteract(PlayerInteractEvent e) {
        e.setCancelled(true);
        e.getPlayer().openInventory(getInventory());
    }

    @Override
    public int getEuCapacity() {
        return STORAGE;
    }

    @Override
    public int getEuOutput() {
        return 10;
    }

    @Override
    public int getEuInput() {
        return 0;
    }

    @Override
    public int getCurrentEu() {
        return this.currentEU;
    }

    @Override
    public void setCurrentEu(int currentEU) {
        this.currentEU = currentEU;
    }

    @Override
    public void saveData(FileConfiguration data, String path) {
        data.set(path + ".EU", currentEU);
        data.set(path + ".Fuel", currentFuel.toString());
        data.set(path + ".Current-Fuel-Item", currentFuelItem);
        data.set(path + ".Current-Burn-Time", currentBurnTime);
    }

    @Override
    public void loadData(FileConfiguration data, String path) {
        this.currentEU = data.getInt(path + ".EU");
        this.currentFuel = Fuel.valueOf(data.getString(path + ".Fuel"));
        this.currentFuelItem = data.getItemStack(data + ".Current-Fuel-Item");
        this.currentBurnTime = data.getInt(path + ".Current-Burn-Time");
        this.burning = currentBurnTime > 0;
    }

    @Override
    public void tick(Location location, int tick) {
        if (getInventory().getItem(10) != null && getInventory().getItem(10).getType() != Material.AIR && currentEU < STORAGE && currentFuel == Fuel.NO_FUEL) {
            for (ItemStack is : Fuel.getAllFuels()) {
                if (handler.itemStackEquals(getInventory().getItem(10), is, false)) {
                    ItemStack fuel = getInventory().getItem(10);
                    if (fuel.getAmount() > 1)
                        fuel.setAmount(fuel.getAmount() - 1);
                    else
                        getInventory().setItem(10, null);

                    Fuel f = Fuel.getFuel(is);
                    if (f != Fuel.NO_FUEL) {
                        currentFuel = f;
                        currentFuelItem = is;
                        currentBurnTime = 0;
                        if (!burning)
                            burning = true;
                    }
                    break;
                }
            }
        }

        if (currentFuel == null) currentFuel = Fuel.NO_FUEL;

        if (currentFuel == Fuel.NO_FUEL && currentEU == 0) return;

        if (currentBurnTime == BURN_TIME) {
            System.out.println("Stopping");
            currentBurnTime = 0;
            burning = false;
            currentFuel = Fuel.NO_FUEL;
            currentFuelItem = null;

            updateInventory();
            return;
        }

        if(burning) {
            if (currentEU < STORAGE) {
                if ((currentEU + (currentFuel.getEuValue() / BURN_TIME)) >= STORAGE)
                    currentEU = STORAGE;
                else
                    currentEU += currentFuel.getEuValue() / BURN_TIME;
                currentBurnTime++;
            }
        }

        handleOutput(location);

        updateInventory();
    }

    private Inventory getInventory() {
        if (inventory == null) {
            inventory = Bukkit.createInventory(null, 27, getName());

            for (int i = 0; i < 27; i++) {
                inventory.setItem(i, new ItemStackBuilder(Material.STAINED_GLASS_PANE, " ").setData(7).build());
            }
            inventory.setItem(10, currentFuelItem);

            for (int i = 12; i < 15; i++) {
                inventory.setItem(i, new ItemStackBuilder(Material.STAINED_GLASS_PANE, ChatColor.WHITE + "Idle").build());
            }

            inventory.setItem(16, new ItemStackBuilder(Material.BLAZE_POWDER, ChatColor.AQUA + "Status",
                    Collections.singletonList(ChatColor.GRAY + "Storage: " + StringUtil.getColorFromEnergy(currentEU, STORAGE)
                            + currentEU + ChatColor.GRAY + "/" + ChatColor.AQUA + STORAGE + " EU")).build());
        }

        return inventory;
    }

    private void updateInventory() {
        for (int i = 12; i < 15; i++) {
            if (!burning)
                inventory.setItem(i, new ItemStackBuilder(Material.STAINED_GLASS_PANE, ChatColor.WHITE + "Idle").build());
            else {
                inventory.setItem(i, new ItemStackBuilder(Material.STAINED_GLASS_PANE, ChatColor.RED + "Burning").setData(14).build());
            }
        }

        inventory.setItem(16, new ItemStackBuilder(Material.BLAZE_POWDER, ChatColor.AQUA + "Status", Collections.singletonList(ChatColor.GRAY + "Storage: "
                + StringUtil.getColorFromEnergy(currentEU, STORAGE) + currentEU + ChatColor.GRAY + "/" + ChatColor.AQUA + STORAGE + " EU")).build());
    }

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
}
