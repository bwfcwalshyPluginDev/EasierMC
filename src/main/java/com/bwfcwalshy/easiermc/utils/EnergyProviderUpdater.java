package com.bwfcwalshy.easiermc.utils;

import com.bwfcwalshy.easiermc.Handler;
import com.bwfcwalshy.easiermc.itemsandblocks.bases.BlockBase;
import com.bwfcwalshy.easiermc.itemsandblocks.bases.Cable;
import com.bwfcwalshy.easiermc.itemsandblocks.blocks.machines.EnergyConsumer;
import com.bwfcwalshy.easiermc.itemsandblocks.blocks.machines.EnergyProvider;
import com.bwfcwalshy.easiermc.utils.pathfinder.Node;
import com.bwfcwalshy.easiermc.utils.pathfinder.PathSearcher;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;

import java.util.Collection;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Updates {@link EnergyProvider#getConnectedMachines()}
 */
public class EnergyProviderUpdater {

    private static final Collection<BlockFace> NEARBY_CABLE_FACES = EnumSet.of(
            BlockFace.DOWN,
            BlockFace.UP,
            BlockFace.NORTH,
            BlockFace.SOUTH,
            BlockFace.EAST,
            BlockFace.WEST
    );

    /**
     * Updates all {@link EnergyProvider}s
     */
    public void run() {
        Handler handler = Handler.getInstance();

        Function<Node<BlockBase>, Collection<Node<BlockBase>>> expandFunction = node -> {
            Collection<Node<BlockBase>> nodes = new LinkedList<>();
            for (BlockFace face : NEARBY_CABLE_FACES) {
                Location next = node.getLocation().clone()
                        .add(face.getModX(), face.getModY(), face.getModZ());

                if (!handler.isBlock(next)) {
                    continue;
                }

                BlockBase blockBase = handler.getBlock(next);
                if (!(blockBase instanceof Cable) && !(blockBase instanceof EnergyConsumer)) {
                    continue;
                }

                nodes.add(new Node<>(next, blockBase));
            }
            return nodes;
        };

        BiFunction<Node<BlockBase>, Node<BlockBase>, Double> gCostFunction = (start, end) -> 1D;

        Predicate<Node<BlockBase>> endNodePredicate = node -> node.getValue() instanceof EnergyConsumer;

        handler.getBlocks().entrySet()
                .stream()
                .filter(entry -> entry.getValue() instanceof EnergyProvider)
                .forEach(entry -> {
                    Node<BlockBase> startNode = new Node<>(entry.getKey(), entry.getValue());
                    PathSearcher<BlockBase> searcher = new PathSearcher<>(
                            expandFunction,
                            gCostFunction,
                            endNodePredicate
                    );

                    Collection<Node<EnergyConsumer>> endNodes = new LinkedList<>();
                    
                    EnergyProvider energyProvider = (EnergyProvider) entry.getValue();
                    
                    for (Node<? extends BlockBase> blockBaseNode : searcher.start(startNode)) {
                        if (!(blockBaseNode.getValue() instanceof EnergyConsumer)) {
                            continue;
                        }
                        if(!energyProvider.acceptConsumer((EnergyConsumer) blockBaseNode.getValue())) {
                            continue;
                        }
                        @SuppressWarnings("unchecked")
                        Node<EnergyConsumer> casted = (Node<EnergyConsumer>) blockBaseNode;
                        endNodes.add(casted);
                    }

                    System.out.println("I will add the following connected machines: "
                            + endNodes.stream().map(energyConsumerNode -> energyConsumerNode.getLocation().toVector()
                            + " " + energyConsumerNode.getValue().getSimpleName()).collect(Collectors.toList())
                            + " to "
                            + entry.getValue().getSimpleName());
                    System.out.println(endNodes.size());
                    energyProvider.setConnectedMachines(endNodes);
                });
    }
}
