package com.bwfcwalshy.easiermc.itemsandblocks.blocks;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import com.bwfcwalshy.easiermc.itemsandblocks.Category;
import com.bwfcwalshy.easiermc.recipe.AdvancedRecipe;
import com.bwfcwalshy.easiermc.utils.ItemStackBuilder;

public class Generator implements MachineBase {

    private Inventory inventory;

    private final int STORAGE = 4000;

    private int currentEU = 0;
    private ItemStack currentFuel = null;
    private String id = "12616a70e8f74ccb6f7f44e4aee5dbcbbcb80c9f23e1dccb07a5f156521215";

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
                .setLore(Collections.singletonList(ChatColor.GRAY + "Generate electricity by burning fuel sources.")).build();
    }

    @Override
    public Recipe getRecipe() {
        return new AdvancedRecipe(getItem(), " i ", "rrr", "   ").setIngredient('i', Material.IRON_BLOCK).setIngredient('r', Material.IRON_INGOT);
    }

    @Override
    public Generator copy() {
        Generator clone = new Generator();
        clone.inventory = cloneInventory(inventory);
        return clone;
    }

    @Override
    public void onInteract(PlayerInteractEvent e){
        e.setCancelled(true);
        e.getPlayer().openInventory(getInventory());
    }

    @Override
    public void tick(Location location, int tick){
        if(getInventory().getItem(10) != null && getInventory().getItem(10).getType() != Material.AIR){
            for(ItemStack is : Fuel.getAllFuels()){
                if(handler.itemStackEquals(getInventory().getItem(10), is, false)){
                    ItemStack fuel = getInventory().getItem(10);
                    if(fuel.getAmount() > 1)
                        fuel.setAmount(fuel.getAmount()-1);
                    else
                        getInventory().setItem(10, null);
                    currentEU += Fuel.getFuel(is).getEuValue();
                    break;
                }
            }
        }
    }

    private Inventory getInventory(){
        if(inventory == null){
            inventory = Bukkit.createInventory(null, 27, getName());

            for(int i = 0; i < 27; i++){
                inventory.setItem(i, new ItemStackBuilder(Material.STAINED_GLASS_PANE, " ").setData(7).build());
            }
            inventory.setItem(10, currentFuel);

            for(int i = 12; i < 15; i++){
                inventory.setItem(i, new ItemStackBuilder(Material.STAINED_GLASS_PANE, ChatColor.WHITE + "Idle").setData(14).build());
            }

            inventory.setItem(16, new ItemStackBuilder(Material.BLAZE_POWDER, ChatColor.AQUA + "Status",
                    Collections.singletonList(ChatColor.GRAY + "Storage: " + ChatColor.RED + currentEU + ChatColor.GRAY + "/" + ChatColor.AQUA + STORAGE + " EU")).build());
        }

        return inventory;
    }

    enum Fuel {
        COAL_CHARCOAL(4000, new ItemStack(Material.COAL), new ItemStack(Material.COAL, (byte) 1)),
        SCRAP(870, handler.getItem("Scrap").getItem()),
        WOOD(750, new ItemStack(Material.WOOD), new ItemStack(Material.WOOD, (byte) 1), new ItemStack(Material.WOOD, (byte) 2), new ItemStack(Material.WOOD, (byte) 3)
                , new ItemStack(Material.WOOD, (byte) 4), new ItemStack(Material.WOOD, (byte) 5)),
        LOG(750, new ItemStack(Material.LOG), new ItemStack(Material.LOG, (byte) 1), new ItemStack(Material.LOG, (byte) 2), new ItemStack(Material.LOG, (byte) 3)
                , new ItemStack(Material.LOG_2), new ItemStack(Material.LOG_2, (byte) 1)),
        WOODEN_TOOLS(500, new ItemStack(Material.WOOD_PICKAXE), new ItemStack(Material.WOOD_SPADE), new ItemStack(Material.WOOD_AXE), new ItemStack(Material.WOOD_SWORD)
                , new ItemStack(Material.WOOD_HOE)),
        STICK(250,new ItemStack(Material.STICK));

        private static List<ItemStack> allFuels;

        private int euValue;
        private ItemStack[] fuels;
        Fuel(int euValue, ItemStack... items){
            this.euValue = euValue;
            this.fuels = items;
        }

        public ItemStack[] getFuels(){
            return this.fuels;
        }

        public int getEuValue(){
            return this.euValue;
        }

        public static List<ItemStack> getAllFuels(){
            if(allFuels == null)
                allFuels = Arrays.stream(values()).flatMap(fuel -> Arrays.stream(fuel.getFuels())).collect(Collectors.toList());
            return allFuels;
        }

        public static Fuel getFuel(ItemStack is) {
            for(Fuel fuel : values()){
                for(ItemStack fuelIs : fuel.getFuels()){
                    if(is.equals(fuelIs)){
                        return fuel;
                    }
                }
            }
            return null;
        }
    }

    @Override
    public int euCapacity() {
        return STORAGE;
    }

    @Override
    public int euInputOutput() {
        return 10;
    }
}
