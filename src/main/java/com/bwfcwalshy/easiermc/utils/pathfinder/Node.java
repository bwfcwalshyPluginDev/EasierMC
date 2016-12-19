package com.bwfcwalshy.easiermc.utils.pathfinder;

import org.bukkit.Location;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.function.BiFunction;

/**
 * A node in the found graph
 *
 * @param <T> The type of the node
 */
public class Node<T> implements Comparable<Node<T>> {

    private final Location location;
    private T value;
    private double gCost = Double.MAX_VALUE;

    private Node<T> parent;

    /**
     * @param location The location of the node
     * @param value    The value of the node
     */
    public Node(Location location, T value) {
        this.location = location.clone();
        this.value = value;
    }

    /**
     * @return The G cost to get to this node
     */
    public double getGCost() {
        return gCost;
    }

    /**
     * Rests the g cost to 0. Needed for the start Node, or {@link Double#MAX_VALUE} propagates through
     */
    void resetGCostToZero() {
        gCost = 0;
    }

    /**
     * @param parent            The parent node
     * @param nodeGCostFunction The function to calculate the g cost between two nodes
     * @return The calculated g cost
     */
    double calculateGCost(Node<T> parent, BiFunction<Node<T>, Node<T>, Double> nodeGCostFunction) {
        double result = parent.getGCost();
        result += nodeGCostFunction.apply(parent, this);

        return result;
    }

    /**
     * @return The location of this Node
     */
    public Location getLocation() {
        return location.clone();
    }

    /**
     * @return The parent node
     */
    public Node<T> getParent() {
        return parent;
    }

    /**
     * @param parent            The new parent
     * @param nodeGCostFunction The function to calculate the g cost between two nodes
     */
    void setParent(Node<T> parent, BiFunction<Node<T>, Node<T>, Double> nodeGCostFunction) {
        this.parent = parent;
        this.gCost = calculateGCost(parent, nodeGCostFunction);
    }

    /**
     * @return The value
     */
    public T getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Node)) {
            return false;
        }
        Node<?> node = (Node<?>) o;
        return Objects.equals(location, node.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location);
    }

    @Override
    public int compareTo(@Nonnull Node<T> o) {
        int result = Double.compare(getGCost(), o.getGCost());

        // if there is no tie, return.
        if (result != 0) {
            return result;
        }

        // Resolve ties. NEVER RETURN 0, as that is interpreted as equal objects, which can NOT BE DECIDED
        // based on the G cost
        return System.identityHashCode(this) > System.identityHashCode(o) ? 1 : -1;
    }

    @Override
    public String toString() {
        return "Node{" +
                "location=" + location +
                ", value=" + value +
                ", gCost=" + gCost +
                '}';
    }
}
