package com.bwfcwalshy.easiermc;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bwfcwalshy.easiermc.itemsandblocks.Category;
import com.bwfcwalshy.easiermc.itemsandblocks.EasierMCBase;
import com.perceivedev.perceivecore.gui.Gui;
import com.perceivedev.perceivecore.gui.components.panes.tree.TreePane;

import com.bwfcwalshy.easiermc.recipe.nodes.ItemRootNode;

import me.ialistannen.itemrecipes.easiermc.util.PlayerHistory;

public class EasierMCCommand implements CommandExecutor {

    private Handler handler;
    private EasierMC easierMC;
    public EasierMCCommand(EasierMC easierMC){
        this.easierMC = easierMC;

        this.handler = easierMC.getHandler();
    }

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
                    TreePane treePane = new TreePane(9, 6);
                    Gui gui = new Gui("&3&lEasierMc &7- &lRecipes", 6, treePane) {
                        @Override
                        protected void onClose() {
                            PlayerHistory.INSTANCE.removePlayer(player.getUniqueId());
                        }
                    };
                    
                    ItemRootNode itemRootNode = new ItemRootNode(null , treePane.getSize(), Category.values);
                    treePane.setRoot(itemRootNode);
                    
                    gui.open(player);
                }else {
                    sender.sendMessage(ChatColor.RED + "You do not have permission for this command.");
                }
            }else if(args[0].equalsIgnoreCase("debug-version")){
                if(handler.getItemFromEverything(player.getInventory().getItemInMainHand()) != null) {
                    sender.sendMessage("Latest version: " + ChatColor.YELLOW + EasierMC.VERSION);
                    sender.sendMessage("Version: " + ChatColor.YELLOW + handler.getVersion(player.getInventory().getItemInMainHand()));
                }
            }else if(args[0].equalsIgnoreCase("debug")){
                if(sender.hasPermission("easiermc.debug")){
                    sender.sendMessage(ChatColor.GRAY + "Total EasierMC blocks: " + ChatColor.YELLOW + handler.getBlocks().keySet().size());
                    double averageTime = easierMC.getTickTask().getAverageTime();
                    sender.sendMessage(ChatColor.GRAY + "Average tick time: " + ChatColor.YELLOW + (averageTime/1000000) + "ms (" + averageTime + ")");
                }
            }
            // /emc give
            // /emc recipe
            // /emc debug
        }else if(args.length == 2){
            if(args[0].equalsIgnoreCase("give")){
                if(sender.hasPermission("easiermc.give")){
                    String item = args[1];
                    EasierMCBase base = handler.getItemFromEverything(item);
                    if(base != null){
                        player.getInventory().addItem(base.getItem());
                    }else{
                        sender.sendMessage(ChatColor.RED + "Invalid item!");
                    }
                }else{
                    sender.sendMessage(ChatColor.RED + "You do not have permission for this command.");
                }
            }
        }
        return false;
    }
}
