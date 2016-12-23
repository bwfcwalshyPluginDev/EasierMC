package com.bwfcwalshy.easiermc.itemsandblocks.blocks.machines;

import com.bwfcwalshy.easiermc.itemsandblocks.bases.MachineBase;
import com.bwfcwalshy.easiermc.utils.pathfinder.Node;

import java.util.Collection;

/**
 * An interface for machines that provide Energy to other machines
 * <p>
 * <br>
 * <b>All methods in this class must be implemented in a <u>Tread safe</u> way</b>
 */
public interface EnergyProvider extends MachineBase {

    /**
     * Sets the connected machines
     * <p>
     * This method is <b>Thread safe</b>
     *
     * @param machines All machines connected to this EnergyProvider
     */
    void setConnectedMachines(Collection<Node<EnergyConsumer>> machines);

    /**
     * Gets all connected machines
     * <p>
     * This method is <b>Thread safe</b>, though there is no guarantee the list is still updated after you obtain it.
     *
     * @return All connected machines
     */
    Collection<Node<EnergyConsumer>> getConnectedMachines();

    /**
     * Checks if you want to serve the consumer
     *
     * @param consumer The consumer to check
     * @return True if you want to serve the consumer
     */
    default boolean acceptConsumer(EnergyConsumer consumer) {
        if (consumer.getClass() == getClass()) {
            return false;
        }
        if (consumer.getSimpleName().equalsIgnoreCase(getSimpleName())) {
            return false;
        }
        return true;
    }

    /**
     * The maximum EU a machine can output in 1 tick.
     *
     * @return Returns the machines maximum EU output per tick.
     */
    int getEuOutput();
}
