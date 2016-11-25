package com.bwfcwalshy.easiermc;

import com.bwfcwalshy.easiermc.itemsandblocks.blocks.*;
import com.bwfcwalshy.easiermc.itemsandblocks.items.ItemBase;
import com.bwfcwalshy.easiermc.itemsandblocks.items.MasterStar;
import com.bwfcwalshy.easiermc.itemsandblocks.items.ReinforcedStick;
import com.bwfcwalshy.easiermc.itemsandblocks.multiblock.AdvancedCraftingTable;
import com.bwfcwalshy.easiermc.itemsandblocks.multiblock.MultiBlock;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Handler {

    private Set<BlockBase> blockRegistery;
    private Set<ItemBase> itemRegistery;
    private Set<MultiBlock> multiBlockRegistery;
    private Map<Location, BlockBase> blocks;
    private static Handler instance;

    public Handler(){
        blocks = new HashMap<>();
        blockRegistery = new HashSet<>();
        itemRegistery = new HashSet<>();
        multiBlockRegistery = new HashSet<>();

        instance = this;
    }

    public static Handler getInstance(){
        return instance;
    }

    public void addBlock(BlockBase block, Location loc){
        this.blocks.put(loc, block);
    }

    public void removeBlock(Location location) {
        if(this.blocks.containsKey(location))
            this.blocks.remove(location);
    }

    private void registerBlock(BlockBase block){
        this.blockRegistery.add(block);
    }

    private void registerItem(ItemBase item){
        this.itemRegistery.add(item);
    }

    private void registerMultiBlock(MultiBlock multiblock) {
        this.multiBlockRegistery.add(multiblock);
    }

    public boolean isBlock(ItemStack is){
        for(BlockBase block : blockRegistery){
            if(block.getItem().isSimilar(is))
                return true;
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
        for(BlockBase block : blockRegistery)
            if(block.getItem().isSimilar(is)) return block;
        return null;
    }

    /**
     * Get a block by it's simple name, for example "BlockBreaker" will return the BlockBreaker instance.
     * @param simpleName The blocks simple name.
     * @return The BlockBase of that simple name if found, null otherwise.
     */
    public BlockBase getBlock(String simpleName) {
        for(BlockBase block : blockRegistery)
            if(block.getSimpleName().equalsIgnoreCase(simpleName)) return block;
        return null;
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
        for(ItemBase item : itemRegistery)
            if(item.getSimpleName().equalsIgnoreCase(simpleName)) return item;
        return null;
    }

    /**
     * get a multiblock structure by it's simple name, for example "AdvancedCraftingTable" will return the AdvancedCraftingTable multiblock instance.
     * @param simpleName Simple name of the multiblock structure.
     * @return Returns the multiblock structure of that name if found, null otherwise.
     */
    public ItemStack getMuiltiBlock(String simpleName) {
        for(MultiBlock multiblock : multiBlockRegistery)
            if(multiblock.getSimpleName().equalsIgnoreCase(simpleName)) return multiblock;
        return null;
    }

    public void registerBlocks() {
        registerBlock(new BlockBreaker());
        registerBlock(new AutoShear());
        registerBlock(new WellMiner());
        registerBlock(new Generator());

        registerItem(new MasterStar());
        registerItem(new ReinforcedStick());

        registerMultiBlock(new AdvancedCraftingTable());
    }

    // This is best to call after the register blocks since this uses the registry.
    public void registerRecipes(){
        blockRegistery.forEach(block -> { if(block.getRecipe() != null) Bukkit.addRecipe(block.getRecipe()); });
        itemRegistery.forEach(item -> { if(item.getRecipe() != null) Bukkit.addRecipe(item.getRecipe()); });
    }

    public Map<Location, BlockBase> getBlocks() {
        return blocks;
    }

    public Set<BlockBase> getBlockRegistery() {
        return blockRegistery;
    }

    public Set<ItemBase> getItemRegistery() {
        return itemRegistery;
    }
}
