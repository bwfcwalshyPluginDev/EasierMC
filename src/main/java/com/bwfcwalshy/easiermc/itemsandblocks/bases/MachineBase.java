package com.bwfcwalshy.easiermc.itemsandblocks.bases;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public interface MachineBase extends BlockBase {

    Set<BlockFace> checkedFaces = EnumSet.of(BlockFace.UP, BlockFace.DOWN, BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST);

    /**
     * The maximum EU a machine can hold
     *
     * @return Returns the machines maximum capacity.
     */
    int getEuCapacity();

    /**
     * The maximum EU a machine can output in 1 tick.
     *
     * @return Returns the machines maximum EU output per tick.
     */
    int getEuOutput();

    /**
     * The maximum EU a machine can input in 1 tick.
     *
     * @return Returns the machines maximum EU input per tick.
     */
    int getEuInput();

    /**
     * Gets the current EU of the machine.
     *
     * @return Returns the machines current EU value.
     */
    int getCurrentEu();

    /**
     * Sets the current EU of the machine.
     *
     * @param currentEU The new current EU value of the machine.
     */
    void setCurrentEu(int currentEU);

    /**
     * This method is used to handle output of EU to other machines and cables around it.
     * <b>It is highly recommended to NOT override this method</b>
     *
     * @param location The location of the machine, pass in the location argument used in the tick method.
     */
    default void handleOutput(Location location) {
        if(getCurrentEu() <= 0) return;

        List<BlockBase> outputs = new ArrayList<>();
        for (BlockFace face : BlockFace.values()) {
            if (checkedFaces.contains(face)) {
                if (handler.isBlock(location.getBlock().getRelative(face).getLocation())) {
                    BlockBase base = handler.getBlock(location.getBlock().getRelative(face).getLocation());

                    if (base instanceof MachineBase) {
                        if(((MachineBase) base).getEuInput() > 0)
                            outputs.add(base);
                    }else if(base instanceof Cable)
                        outputs.add(base);
                }
            }
        }

        if (outputs.size() == 0) return;
        int canOutput = getEuOutput();
        if(getCurrentEu() < getEuOutput())
            canOutput = getCurrentEu();
        int euPerOutput = canOutput / outputs.size();

        System.out.println(getSimpleName() + " has " + getCurrentEu());
        System.out.println(euPerOutput);

        for (BlockBase output : outputs) {
            if (output instanceof MachineBase) {
                MachineBase machine = (MachineBase) output;
                System.out.println("a");
                if (machine.getEuInput() <= 0 || machine.getCurrentEu() == machine.getEuCapacity()) return;
                System.out.println("b");
                System.out.println(machine.getEuInput());
                if (euPerOutput <= machine.getEuInput()) {
                    System.out.println("c");
                    System.out.println(getSimpleName() + " is giving " + machine.getSimpleName() + " " + euPerOutput + " EU");
                    if ((machine.getCurrentEu() + euPerOutput) > machine.getEuCapacity())
                        machine.setCurrentEu(machine.getEuCapacity());
                    else
                        machine.setCurrentEu(machine.getCurrentEu() + euPerOutput);
                    System.out.println(machine.getCurrentEu());
                    setCurrentEu(getCurrentEu() - euPerOutput);
                }
            } else {
                // Do the node crap
            }
        }
    }
}
