package com.bwfcwalshy.easiermc.utils.pathfinder;

import com.bwfcwalshy.easiermc.itemsandblocks.bases.Cable;

import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * A Path finder. It doesn't find the best path between two nodes, but it finds all end nodes connected with the start
 *
 * @param <T> The type of the nodes
 */
public class PathSearcher<T> {

    /**
     * Just to prevent {@link StackOverflowError}s due to too long cable lines
     */
    @SuppressWarnings("FieldCanBeLocal")
    private final long MAX_ITERATIONS = 10000;

    private Function<Node<T>, Collection<Node<T>>> nodeExpandFunction;
    private BiFunction<Node<T>, Node<T>, Double> gCostFunction;
    private Predicate<Node<T>> endNodePredicate;

    private TreeSet<Node<T>> open = new TreeSet<>();
    private Set<Node<T>> closed = new HashSet<>();

    private Set<Node<T>> results = new HashSet<>();

    private boolean debug = false;

    /**
     * @param nodeExpandFunction The function to get adjacent nodes
     * @param gCostFunction      The function to calculate the g-cost between two nodes
     * @param endNodePredicate   The predicate for an end node
     */
    public PathSearcher(Function<Node<T>, Collection<Node<T>>> nodeExpandFunction,
                        BiFunction<Node<T>, Node<T>, Double> gCostFunction,
                        Predicate<Node<T>> endNodePredicate) {
        this.nodeExpandFunction = nodeExpandFunction;
        this.gCostFunction = gCostFunction;
        this.endNodePredicate = endNodePredicate;
    }

    /**
     * In debug mode visited cables will be highlighted in a random color
     *
     * @param debug True if debug mode should be activated.
     */
    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    /**
     * @param start The start node
     * @return The found nodes. Unordered
     */
    public Collection<Node<T>> start(Node<T> start) {
        start.resetGCostToZero();
        open.add(start);
        run(debug);
        return Collections.unmodifiableCollection(results);
    }

    @SuppressWarnings("deprecation")
    private void run(boolean debug) {
        Node<T> tmp;
        long counter = 0;

        byte random = (byte) ThreadLocalRandom.current().nextInt(15);

        while (counter++ < MAX_ITERATIONS && (tmp = open.pollFirst()) != null) {
            if (endNodePredicate.test(tmp)) {
                results.add(tmp);
            }

            // color the cables if debug is true
            if (debug) {
                if (tmp.getValue() instanceof Cable) {
                    Block block = tmp.getLocation().getBlock();
                    block.setType(Material.STAINED_GLASS_PANE);
                    block.setData(random);
                }
            }

            closed.add(tmp);

            expand(tmp);
        }
    }

    private void expand(Node<T> current) {
        for (Node<T> node : nodeExpandFunction.apply(current)) {
            if (node.equals(current)) {
                continue;
            }

            if (closed.contains(node)) {
                continue;
            }

            double newGCost = node.calculateGCost(current, gCostFunction);
            if (newGCost < node.getGCost()) {
                // found a shorter path
                node.setParent(current, gCostFunction);
            }
            open.add(node);
        }
    }
}
