package com.bwfcwalshy.easiermc.itemsandblocks.blocks.machines;

import com.bwfcwalshy.easiermc.utils.pathfinder.Node;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

/**
 * An {@link EnergyProvider} implementation
 */
public abstract class AbstractEnergyProvider implements EnergyProvider {

    private Collection<Node<EnergyConsumer>> machines;

    @Override
    public void setConnectedMachines(Collection<Node<EnergyConsumer>> machines) {
        this.machines = Collections.newSetFromMap(new ConcurrentHashMap<>());
        this.machines.addAll(machines);
    }

    @Override
    public Collection<Node<EnergyConsumer>> getConnectedMachines() {
        return Collections.unmodifiableCollection(machines);
    }
}
