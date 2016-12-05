package com.bwfcwalshy.easiermc.itemsandblocks.items;

import com.bwfcwalshy.easiermc.Handler;
import com.bwfcwalshy.easiermc.itemsandblocks.Category;
import com.bwfcwalshy.easiermc.itemsandblocks.AdvancedRecipe;
import com.bwfcwalshy.easiermc.utils.ItemStackBuilder;
import com.bwfcwalshy.easiermc.utils.nbt.ItemNBTUtil;
import com.bwfcwalshy.easiermc.utils.nbt.NBTWrappers;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.Arrays;
import java.util.UUID;

public class EnderSword implements ItemBase {

    private Handler handler = Handler.getInstance();

    @Override
    public String getName() {
        return ChatColor.DARK_PURPLE + "Ender " + ChatColor.GRAY + "Sword";
    }

    @Override
    public String getSimpleName() {
        return "EnderSword";
    }

    @Override
    public Category getCategory() {
        return Category.WEAPONS;
    }

    @Override
    public ItemStack getItem() {
        return new ItemStackBuilder(Material.DIAMOND_SWORD, getName(), Arrays.asList(ChatColor.GRAY + "This is the most powerful sword known to man.", " "
                , ChatColor.DARK_PURPLE + "Powered by a dragon egg, this sword will guarantee you can slay any dragon in the land.")).build();
    }

    @Override
    public Recipe getRecipe() {
        return null;
    }

    @Override
    public AdvancedRecipe getAdvancedRecipe(){
        return new AdvancedRecipe(getItem(), "sds", "sds", "rer").setIngredient('s', handler.getItem("MasterStar").getItem()).setIngredient('d', Material.DIAMOND_BLOCK)
                .setIngredient('r', handler.getItem("ReinforcedStick").getItem()).setIngredient('e', Material.DRAGON_EGG);
    }

    @Override
    public void onInteract(PlayerInteractEvent e){
        if(e.getPlayer().getInventory().getItemInMainHand().equals(getItem())){
            ItemStack sword = e.getPlayer().getInventory().getItemInMainHand();

            // Add attackDamage crap
            NBTWrappers.NBTTagCompound tag = ItemNBTUtil.getTag(sword);

            if(tag.hasKey("AttributeModifiers")) return;
            NBTWrappers.NBTTagList attributeModifiers = tag.hasKey("AttributeModifiers") ? (NBTWrappers.NBTTagList) tag.get("AttributeModifiers") : new NBTWrappers.NBTTagList();

            attributeModifiers.getRawList().clear();

            UUID randomID = UUID.randomUUID();

            NBTWrappers.NBTTagCompound attackDamage = new NBTWrappers.NBTTagCompound();
            attackDamage.setInt("Operation", 0);
            attackDamage.setInt("Amount", 30); // Default sword does 7
            attackDamage.setString("Name", "generic.attackDamage");
            attackDamage.setInt("UUIDMost", (int) randomID.getMostSignificantBits());
            attackDamage.setInt("UUIDLeast", (int) randomID.getLeastSignificantBits());
            attackDamage.setString("AttributeName", "generic.attackDamage");
            attackDamage.setString("Slot", "mainhand");

            randomID = UUID.randomUUID();

            NBTWrappers.NBTTagCompound attackSpeed = new NBTWrappers.NBTTagCompound();
            attackSpeed.setInt("Operation", 0);
            attackSpeed.setDouble("Amount", -2);
            attackSpeed.setString("Name", "generic.attackSpeed");
            attackSpeed.setInt("UUIDMost", (int) randomID.getMostSignificantBits());
            attackSpeed.setInt("UUIDLeast", (int) randomID.getLeastSignificantBits());
            attackSpeed.setString("AttributeName", "generic.attackSpeed");
            attackSpeed.setString("Slot", "mainhand");

            attributeModifiers.add(attackDamage);
            attributeModifiers.add(attackSpeed);

            tag.set("AttributeModifiers", attributeModifiers);

            sword = ItemNBTUtil.setNBTTag(tag, sword);

            e.getPlayer().getInventory().setItemInMainHand(sword);
        }
    }
}
