package com.bwfcwalshy.easiermc;

import com.bwfcwalshy.easiermc.itemsandblocks.Category;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class EasierMCCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "You need to be a player to use that command!");
        }
        Player player = (Player) sender;
        if(args.length == 0){

        }else if(args.length == 1){
            if(args[0].equalsIgnoreCase("recipe")){
                if(sender.hasPermission("easiermc.recipe")){
                    openInventory(player);
                }else {
                    sender.sendMessage(ChatColor.RED + "You do not have permission for this command.");
                }
            }
            // /emc give
            // /emc recipe
            // /emc debug
        }
        return false;
    }

    private Inventory recipeInventory;
    private void openInventory(Player player){
        if(recipeInventory == null){
            recipeInventory = Bukkit.createInventory(null, 9*(Math.round(Category.values.length / 9)));

            for(Category category : Category.values){
                recipeInventory.addItem(category.getDisplayItem());
            }
        }

        player.openInventory(recipeInventory);
    }
}
