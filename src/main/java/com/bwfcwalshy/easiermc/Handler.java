package com.bwfcwalshy.easiermc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.bwfcwalshy.easiermc.itemsandblocks.EasierMCBase;
import com.bwfcwalshy.easiermc.itemsandblocks.blocks.AutoShear;
import com.bwfcwalshy.easiermc.itemsandblocks.blocks.Batbox;
import com.bwfcwalshy.easiermc.itemsandblocks.blocks.BlockBase;
import com.bwfcwalshy.easiermc.itemsandblocks.blocks.BlockBreaker;
import com.bwfcwalshy.easiermc.itemsandblocks.blocks.Generator;
import com.bwfcwalshy.easiermc.itemsandblocks.blocks.TrashBin;
import com.bwfcwalshy.easiermc.itemsandblocks.blocks.WellMiner;
import com.bwfcwalshy.easiermc.itemsandblocks.blocks.cable.GlassFibreCable;
import com.bwfcwalshy.easiermc.itemsandblocks.blocks.cable.GoldCable;
import com.bwfcwalshy.easiermc.itemsandblocks.blocks.cable.IronCable;
import com.bwfcwalshy.easiermc.itemsandblocks.items.EnderSword;
import com.bwfcwalshy.easiermc.itemsandblocks.items.ItemBase;
import com.bwfcwalshy.easiermc.itemsandblocks.items.LongFallBoots;
import com.bwfcwalshy.easiermc.itemsandblocks.items.MasterStar;
import com.bwfcwalshy.easiermc.itemsandblocks.items.ReinforcedStick;
import com.bwfcwalshy.easiermc.itemsandblocks.items.Rubber;
import com.bwfcwalshy.easiermc.itemsandblocks.items.Scrap;
import com.bwfcwalshy.easiermc.itemsandblocks.items.TapeMeasure;
import com.bwfcwalshy.easiermc.itemsandblocks.items.TreeTap;
import com.bwfcwalshy.easiermc.itemsandblocks.multiblock.AdvancedCraftingTable;
import com.bwfcwalshy.easiermc.itemsandblocks.multiblock.MultiBlock;
import com.bwfcwalshy.easiermc.recipe.AdvancedRecipe;

import me.ialistannen.itemrecipes.easiermc.util.Util;
import nl.shanelab.multiblock.MultiBlockFactory;

public class Handler {

    private Map<ItemCategory, Set<? extends EasierMCBase>> registery;
    private Map<Location, BlockBase> blocks;

    private List<AdvancedRecipe> advancedRecipes;

    private static Handler instance;

    private EasierMC main;
    public Handler(EasierMC easierMC){
        this.main = easierMC;
        blocks = new HashMap<>();
        registery = new HashMap<>();

        this.advancedRecipes = new ArrayList<>();

        registery.put(ItemCategory.BLOCKS, new HashSet<ItemBase>());
        registery.put(ItemCategory.ITEMS, new HashSet<ItemBase>());
        registery.put(ItemCategory.MULTIBLOCKS, new HashSet<ItemBase>());

        instance = this;
    }

    public static Handler getInstance(){
        return instance;
    }

    public void addBlock(BlockBase block, Location loc){
        this.blocks.put(loc, block.copy());
    }

    public void removeBlock(Location location) {
        if(this.blocks.containsKey(location))
            this.blocks.remove(location);
    }

    @SuppressWarnings("unchecked")
    private void registerBlock(BlockBase block){
        ((Set<? super EasierMCBase>) this.registery.get(ItemCategory.BLOCKS)).add(block);
    }

    @SuppressWarnings("unchecked")
    private void registerItem(ItemBase item){
        ((Set<? super EasierMCBase>) this.registery.get(ItemCategory.ITEMS)).add(item);
    }

    @SuppressWarnings("unchecked")
    private void registerMultiBlock(MultiBlock multiblock) {
        ((Set<? super EasierMCBase>) this.registery.get(ItemCategory.MULTIBLOCKS)).add(multiblock);
        MultiBlockFactory.INSTANCE.register(main, multiblock.getClass());
    }

    @SuppressWarnings("unchecked")
    public boolean isBlock(ItemStack is){
        for(BlockBase block : (Set<BlockBase>) registery.get(ItemCategory.BLOCKS)) {
            if (itemStackEquals(is, block.getItem(), false, true)) return true;
        }
        return false;
    }

    public boolean isBlock(Location loc){
        return blocks.containsKey(loc);
    }

    /**
     * Get a BlockBase from the ItemStack, this will return null if no block is found un the registery.
     * @param is ItemStack of the BlockBase.
     * @return The BlockBase of that ItemStack if found, null otherwise.
     */
    public BlockBase getBlock(ItemStack is){
        for(BlockBase block : (Set<BlockBase>) registery.get(ItemCategory.BLOCKS))
            if(block.getItem().isSimilar(is)) return block;
        return null;
        //throw new IllegalArgumentException("That block for that ItemStack does not exist or is not registered!");
    }

    /**
     * Get a block by it's simple name, for example "BlockBreaker" will return the BlockBreaker instance.
     * @param simpleName The blocks simple name.
     * @return The BlockBase of that simple name if found, null otherwise.
     */
    public BlockBase getBlock(String simpleName) {
        for(BlockBase block : (Set<BlockBase>) registery.get(ItemCategory.BLOCKS))
            if(block.getSimpleName().equalsIgnoreCase(simpleName)) return block;
        throw new IllegalArgumentException("That block (" + simpleName + ") does not exist or is not registered!");
    }

    /**
     * Get a block by a location, this has to be a location of a BlockBase otherwise this will return null!
     * @param location Location of the block you are trying to get.
     * @return Returns the BlockBase at the passed location, null otherwise.
     */
    public BlockBase getBlock(Location location) {
        if(blocks.containsKey(location))
            return blocks.get(location);
        return null;
    }

    /**
     * Get an item by it's simple name, for example "MasterStar" will return the MasterStar instance.
     * @param simpleName Simple name of the item.
     * @return Returns the item of that simple name if found, null otherwise.
     */
    public ItemBase getItem(String simpleName) {
        for(ItemBase item : (Set<ItemBase>) registery.get(ItemCategory.ITEMS))
            if(item.getSimpleName().equalsIgnoreCase(simpleName)) return item;
        throw new IllegalArgumentException("That item (" + simpleName + ") does not exist or is not registered!");
    }

    /**
     * Get an item by it's ItemStack, for example giving it the MasterStar ItemStack will return MasterStar.
     * This method is mainly used for getting the item in a users hand.
     * @param is ItemStack of the item you want to find.
     * @return Returns the item of the ItemStack if found, null otherwise.
     */
    public ItemBase getItem(ItemStack is) {
        for(ItemBase item : (Set<ItemBase>) registery.get(ItemCategory.ITEMS))
            if(itemStackEquals(is, item.getItem(), false, false)) return item;
        return null;
        //throw new IllegalArgumentException("That item for that ItemStack does not exist or is not registered!");
    }

    /**
     * get a multiblock structure by it's simple name, for example "AdvancedCraftingTable" will return the AdvancedCraftingTable multiblock instance.
     * @param simpleName Simple name of the multiblock structure.
     * @return Returns the multiblock structure of that name if found, null otherwise.
     */
    public MultiBlock getMuiltiBlock(String simpleName) {
        for(MultiBlock multiblock : (Set<MultiBlock>) registery.get(ItemCategory.MULTIBLOCKS))
            if(multiblock.getSimpleName().equalsIgnoreCase(simpleName)) return multiblock;
        throw new IllegalArgumentException("That multiblock for that ItemStack does not exist or is not registered!");
    }

    /**
     * Checks if a simple name is a valid block. This is used to check blocks when loading.
     * @param simpleName Simple name of the block you are trying to find.
     * @return If that block exists.
     */
    public boolean isValidBlock(String simpleName) {
        for(BlockBase block : getBlockRegistery())
            if(block.getSimpleName().equalsIgnoreCase(simpleName)) return true;
        return false;
    }

    public void registerBlocks() {
        registerBlock(new BlockBreaker());
        registerBlock(new AutoShear());
        registerBlock(new WellMiner());
        registerBlock(new Generator());
        registerBlock(new TrashBin());
        registerBlock(new IronCable());
        registerBlock(new GoldCable());
        registerBlock(new GlassFibreCable());
        registerBlock(new Batbox());

        registerItem(new MasterStar());
        registerItem(new ReinforcedStick());
        registerItem(new EnderSword());
        registerItem(new TapeMeasure());
        registerItem(new LongFallBoots());
        registerItem(new TreeTap());
        registerItem(new Rubber());
        registerItem(new Scrap());

        registerMultiBlock(new AdvancedCraftingTable());
    }

    // This is best to call after the register blocks since this uses the registry.
    public void registerRecipes(){
        for(ItemCategory category : registery.keySet()){
            for(EasierMCBase base : registery.get(category)){
                if(base.getRecipe() != null && !(base.getRecipe() instanceof AdvancedRecipe)) Bukkit.addRecipe(base.getRecipe());
                else if(base.getRecipe() != null && base.getRecipe() instanceof AdvancedRecipe) this.advancedRecipes.add((AdvancedRecipe) base.getRecipe());
            }
        }
    }

    public Map<Location, BlockBase> getBlocks() {
        return blocks;
    }

    public Map<Location, BlockBase> getBlocks(String simpleName) {
        Map<Location, BlockBase> blockBases = new HashMap<>();
        blocks.forEach((location, blockBase) -> { if(blockBase.getSimpleName().equalsIgnoreCase(simpleName)) blockBases.put(location, blockBase); });
        return blockBases;
    }

    public Set<EasierMCBase> getEntireRegistery(){
        Set<EasierMCBase> entireRegistery = new HashSet<>();
        for(Set<? extends EasierMCBase> base : registery.values()){
            entireRegistery.addAll(base);
        }
        return entireRegistery;
    }

    public Set<BlockBase> getBlockRegistery() {
        return (Set<BlockBase>) registery.get(ItemCategory.BLOCKS);
    }

    public Set<ItemBase> getItemRegistery() {
        return (Set<ItemBase>) registery.get(ItemCategory.ITEMS);
    }

    /**
     * This will return an item, block, multiblock, whatever the ItemStack returns. This is not limited to any one type.
     * @param item
     * @return
     */
    public EasierMCBase getItemFromEverything(ItemStack item) {
        for(EasierMCBase base : getEntireRegistery())
            if(itemStackEquals(item, base.getItem(), false, false)) return base;
        return null;
    }

    /**
     * This will return an item, block, multiblock, whatever the simple name returns. This is not limited to any one type.
     * @param simpleName
     * @return
     */
    public EasierMCBase getItemFromEverything(String simpleName) {
        for(EasierMCBase base : getEntireRegistery())
            if(base.getSimpleName().equalsIgnoreCase(simpleName)) return base;
        return null;
    }

    public List<AdvancedRecipe> getAdvancedRecipes() {
        return this.advancedRecipes;
    }

    /**
     * Check if an ItemStack equals another.
     * @param check The ItemStack you wish to check.
     * @param compare The ItemStack you are comparing to.
     * @param checkAmount Make sure the checked ItemStack is the same as or greater than the compared ItemStack
     * @return Returns if the ItemStack is equal to the other.
     */
    public boolean itemStackEquals(ItemStack check, ItemStack compare, boolean checkAmount){
        return itemStackEquals(check, compare, checkAmount, true);
    }

    /**
     * Check if an ItemStack equals another.
     * @param toCheck The ItemStack you wish to check.
     * @param compare The ItemStack you are comparing to.
     * @param checkAmount Make sure the checked ItemStack is the same as or greater than the compared ItemStack
     * @param checkLore Check the lore of the ItemStack and see if it matches the other.
     * @return Returns if the ItemStack is equal to the other.
     */
    public boolean itemStackEquals(ItemStack toCheck, ItemStack compare, boolean checkAmount, boolean checkLore){
        if(toCheck == null) toCheck = new ItemStack(Material.AIR);
        if(compare == null) compare = new ItemStack(Material.AIR);

        if(compare.getType() == toCheck.getType()){
            if(compare.hasItemMeta()) {
                if (!toCheck.hasItemMeta()) return false;
                else {
                    if (compare.getItemMeta().hasDisplayName()){
                        if (!toCheck.getItemMeta().hasDisplayName()) return false;
                        else
                            if (!toCheck.getItemMeta().getDisplayName().equals(compare.getItemMeta().getDisplayName())) return false;
                    }
                    if (checkLore && compare.getItemMeta().hasLore()) {
                        if (!toCheck.getItemMeta().hasLore()) return false;
                        else if (!toCheck.getItemMeta().getLore().equals(compare.getItemMeta().getLore())) return false;
                    }
                }
            }
            if(checkAmount && toCheck.getAmount() < compare.getAmount()) return false;
            return true;
        }
        return false;
    }

    /**
     * Check if a string is higher than the current one.
     * @param currentVer Current version of the plugin.
     * @param version The version to check.
     * @return If version is greater than currentVar
     */
    public boolean isHigherVersion(String currentVer, String version){
        String[] currentVersion = currentVer.replace("Version: v", "").split("\\.");
        String[] ver = version.replace("Version: v", "").split("\\.");

        int revision = 0;
        int verRevision = 0;
        if(currentVersion[2].contains("-")) {
            revision = Integer.parseInt(currentVersion[2].replaceFirst("[0-9]+-", ""));
            if(ver[2].contains("-")){
                verRevision =Integer.parseInt(ver[2].replaceFirst("[0-9]+-", ""));
            }
        }

        if(Integer.parseInt(currentVersion[0]) > Integer.parseInt(ver[0]))
            return true;
        else if(Integer.parseInt(currentVersion[1]) > Integer.parseInt(ver[1]))
            return true;
        else if(Integer.parseInt(currentVersion[2].replaceAll("-[0-9]+", "")) > Integer.parseInt(ver[2].replaceAll("-[0-9]+", "")))
            return true;
        else if(revision > verRevision)
            return true;
        else
            return false;
    }

    public String getVersion(ItemStack is){
        if(!is.hasItemMeta() || !is.getItemMeta().hasLore()) return null;
        for(String s : is.getItemMeta().getLore()){
            if(Util.isHiddenLine(s)) {
                s = Util.showString(s);
                if (s.contains("Version: ")) {
                    return s.replace("Version: v", "");
                }
            }
        }
        throw new IllegalArgumentException(is.getItemMeta().getDisplayName() + " - Does not have a version!");
        //return null;
    }
}
