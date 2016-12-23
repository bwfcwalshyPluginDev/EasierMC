package com.bwfcwalshy.easiermc.itemsandblocks.blocks.machines;

import com.bwfcwalshy.easiermc.itemsandblocks.bases.MachineBase;

/**
 * A machine that consumes energy
 */
public interface EnergyConsumer extends MachineBase {

    /**
     * The maximum EU a machine can input in 1 tick.
     *
     * @return Returns the machines maximum EU input per tick.
     */
    int getEuInput();

    /**
     * Adds the given amount of EU, maxing at the capacity
     *
     * @param amount The amount of EU to add
     */
    default void addEU(int amount) {
        setCurrentEu(Math.min(getEuCapacity(), getCurrentEu() + amount));
    }

    /**
     * @return True if this {@link EnergyConsumer} is filled
     */
    default boolean isFull() {
        return getCurrentEu() >= getEuCapacity();
    }

    /**
     * @param amountToAdd The amount of EU to add
     * @return True if the machine will be full if {@code amountToAdd} is added
     */
    default boolean willBeFilled(int amountToAdd) {
        return getCurrentEu() + amountToAdd >= getEuCapacity();
    }
}
