package com.bwfcwalshy.easiermc;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class EasierMCCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(args.length == 0){

        }else if(args.length == 1){
            if(args[0].equalsIgnoreCase("recipe")){
                if(sender.hasPermission("easiermc.recipe")){

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
}
