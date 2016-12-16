package com.bwfcwalshy.easiermc;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bwfcwalshy.easiermc.itemsandblocks.Category;
import com.bwfcwalshy.easiermc.itemsandblocks.EasierMCBase;
import com.bwfcwalshy.easiermc.itemsandblocks.blocks.BlockBase;
import com.bwfcwalshy.easiermc.itemsandblocks.blocks.Generator;
import com.bwfcwalshy.easiermc.recipe.nodes.ItemRootNode;
import com.bwfcwalshy.easiermc.utils.pathfinder.Node;
import com.bwfcwalshy.easiermc.utils.pathfinder.PathSearcher;
import com.perceivedev.perceivecore.gui.Gui;
import com.perceivedev.perceivecore.gui.components.panes.tree.TreePane;
import com.perceivedev.perceivecore.util.JSONMessage;
import com.perceivedev.perceivecore.util.TextUtils;

import me.ialistannen.itemrecipes.easiermc.util.PlayerHistory;

public class EasierMCCommand implements CommandExecutor {

    private Handler handler;
    private EasierMC easierMC;

    public EasierMCCommand(EasierMC easierMC) {
        this.easierMC = easierMC;

        this.handler = easierMC.getHandler();
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You need to be a player to use that command!");
            return true;
        }
        Player player = (Player) sender;
        if (args.length == 0) {

        }
        else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("recipe")) {
                if (sender.hasPermission("easiermc.recipe")) {
                    TreePane treePane = new TreePane(9, 6);
                    Gui gui = new Gui("&3&lEasierMc &7- &lRecipes", 6, treePane) {
                        @Override
                        protected void onClose() {
                            PlayerHistory.INSTANCE.removePlayer(player.getUniqueId());
                        }
                    };

                    ItemRootNode itemRootNode = new ItemRootNode(null, treePane.getSize(), Category.values);
                    treePane.setRoot(itemRootNode);

                    gui.open(player);
                }
                else {
                    sender.sendMessage(ChatColor.RED + "You do not have permission for this command.");
                }
            }
            else if (args[0].equalsIgnoreCase("debug-version")) {
                if (handler.getItemFromEverything(player.getInventory().getItemInMainHand()) != null) {
                    System.out.println("Version: " + ChatColor.YELLOW + handler.getVersion(player.getInventory()
                            .getItemInMainHand()));
                }
            }
            else if (args[0].equalsIgnoreCase("debug")) {
                if (sender.hasPermission("easiermc.debug")) {
                    sender.sendMessage(ChatColor.GRAY + "Total EasierMC blocks: " + ChatColor.YELLOW + handler
                            .getBlocks()
                            .keySet()
                            .size());
                    double averageTime = easierMC.getTickTask().getAverageTime();
                    sender.sendMessage(ChatColor.GRAY + "Average tick time: " + ChatColor.YELLOW + (averageTime /
                            1000000) + "ms (" + averageTime + ")");
                }
            }
            else if (args[0].equalsIgnoreCase("debug-cable")) {
                Block targetBlock = player.getTargetBlock((Set<Material>) null, 100);
                Handler handler = Handler.getInstance();
                PathSearcher<BlockBase> pathSearcher = new PathSearcher<>(cableNode -> {
                    List<Node<BlockBase>> nodes = new LinkedList<>();
                    for (int x = -1; x <= 1; x++) {
                        for (int y = -1; y <= 1; y++) {
                            for (int z = -1; z <= 1; z++) {
                                if (x == 0 && y == 0 && z == 0) {
                                    continue;
                                }
                                if (Math.abs(x) + Math.abs(y) + Math.abs(z) > 1) {
                                    continue;
                                }
                                Location location = cableNode.getLocation().add(x, y, z);
                                if (!handler.isBlock(location)) {
                                    continue;
                                }
                                BlockBase block = handler.getBlock(location);

                                Node<BlockBase> node = new Node<>(location, block);
                                nodes.add(node);
                            }
                        }
                    }
                    return nodes;
                },
                        (cableNode, cableNode2) -> 1d,
                        cableNode -> cableNode.getValue() instanceof Generator);
                pathSearcher.setDebug(true);
                Node<BlockBase> start = new Node<>(targetBlock.getLocation(), handler.getBlock(targetBlock
                        .getLocation()));
                Collection<Node<BlockBase>> nodes = pathSearcher.start(start);

                player.sendMessage(TextUtils.colorize("&7Found &6" + nodes.size() + " &7generators."));

                int outerCounter = 0;
                // reverse as the smallest is drawn last
                for (Node<BlockBase> node : nodes.stream()
                        .sorted(Comparator.reverseOrder())
                        .collect(Collectors.toList())) {
                    Node<BlockBase> tmp = node;
                    byte data = (byte) (outerCounter++ % 16);

                    int counter = 0;
                    while ((tmp = tmp.getParent()) != null && counter++ < 10000) {
                        tmp.getLocation().getBlock().setData(data);
                    }

                    Location location = node.getLocation();
                    JSONMessage message = JSONMessage.create("Generator distance: ")
                            .color(ChatColor.GRAY)
                            .then(Integer.toString(counter))
                            .color(ChatColor.GOLD)
                            .runCommand("/minecraft:tp @p "
                                    + location.getBlockX() + " " + location.getBlockY() + " " + location.getBlockZ())
                            .tooltip("/minecraft:tp @p "
                                    + location.getBlockX() + " " + location.getBlockY() + " " + location.getBlockZ());
                    message.send(player);
                }
            }
            // /emc give
            // /emc recipe
            // /emc debug
        }
        else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("give")) {
                if (sender.hasPermission("easiermc.give")) {
                    String item = args[1];
                    EasierMCBase base = handler.getItemFromEverything(item);
                    if (base != null) {
                        player.getInventory().addItem(base.getItem());
                    }
                    else {
                        sender.sendMessage(ChatColor.RED + "Invalid item!");
                    }
                }
                else {
                    sender.sendMessage(ChatColor.RED + "You do not have permission for this command.");
                }
            }
        }
        return false;
    }
}
