package com.bwfcwalshy.easiermc;

import com.bwfcwalshy.easiermc.itemsandblocks.EasierMCBase;
import com.bwfcwalshy.easiermc.itemsandblocks.blocks.*;
import com.bwfcwalshy.easiermc.itemsandblocks.items.EnderSword;
import com.bwfcwalshy.easiermc.itemsandblocks.items.ItemBase;
import com.bwfcwalshy.easiermc.itemsandblocks.items.MasterStar;
import com.bwfcwalshy.easiermc.itemsandblocks.items.ReinforcedStick;
import com.bwfcwalshy.easiermc.itemsandblocks.multiblock.AdvancedCraftingTable;
import com.bwfcwalshy.easiermc.itemsandblocks.multiblock.MultiBlock;
import nl.shanelab.multiblock.MultiBlockFactory;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class Handler {

    private Map<ItemCategory, Set<? extends EasierMCBase>> registery;

    private Map<Location, BlockBase> blocks;
    private static Handler instance;

    private EasierMC main;
    public Handler(EasierMC easierMC){
        this.main = easierMC;
        blocks = new HashMap<>();

        registery = new HashMap<>();
        registery.put(ItemCategory.BLOCKS, new HashSet<ItemBase>());
        registery.put(ItemCategory.ITEMS, new HashSet<ItemBase>());
        registery.put(ItemCategory.MULTIBLOCKS, new HashSet<ItemBase>());

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
        ((Set<? super EasierMCBase>) this.registery.get(ItemCategory.BLOCKS)).add(block);
    }

    private void registerItem(ItemBase item){
        ((Set<? super EasierMCBase>) this.registery.get(ItemCategory.ITEMS)).add(item);
    }

    private void registerMultiBlock(MultiBlock multiblock) {
        ((Set<? super EasierMCBase>) this.registery.get(ItemCategory.MULTIBLOCKS)).add(multiblock);
        MultiBlockFactory.INSTANCE.register(main, multiblock.getClass());
    }

    public boolean isBlock(ItemStack is){
        for(BlockBase block : (Set<BlockBase>) registery.get(ItemCategory.BLOCKS))
            if(block.getItem().isSimilar(is)) return true;
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
    }

    /**
     * Get a block by it's simple name, for example "BlockBreaker" will return the BlockBreaker instance.
     * @param simpleName The blocks simple name.
     * @return The BlockBase of that simple name if found, null otherwise.
     */
    public BlockBase getBlock(String simpleName) {
        for(BlockBase block : (Set<BlockBase>) registery.get(ItemCategory.BLOCKS))
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
        for(ItemBase item : (Set<ItemBase>) registery.get(ItemCategory.ITEMS))
            if(item.getSimpleName().equalsIgnoreCase(simpleName)) return item;
        return null;
    }

    /**
     * get a multiblock structure by it's simple name, for example "AdvancedCraftingTable" will return the AdvancedCraftingTable multiblock instance.
     * @param simpleName Simple name of the multiblock structure.
     * @return Returns the multiblock structure of that name if found, null otherwise.
     */
    public MultiBlock getMuiltiBlock(String simpleName) {
        for(MultiBlock multiblock : (Set<MultiBlock>) registery.get(ItemCategory.MULTIBLOCKS))
            if(multiblock.getSimpleName().equalsIgnoreCase(simpleName)) return multiblock;
        return null;
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

        registerItem(new MasterStar());
        registerItem(new ReinforcedStick());
        registerItem(new EnderSword());

        registerMultiBlock(new AdvancedCraftingTable());
    }

    // This is best to call after the register blocks since this uses the registry.
    public void registerRecipes(){
        for(ItemCategory category : registery.keySet()){
            for(EasierMCBase base : registery.get(category)){
                if(base.getRecipe() != null) Bukkit.addRecipe(base.getRecipe());
            }
        }
    }

    public Map<Location, BlockBase> getBlocks() {
        return blocks;
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
            if(base.getItem().equals(item)) return base;
        return null;
    }
}
