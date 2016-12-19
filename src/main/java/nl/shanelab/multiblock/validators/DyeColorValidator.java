package nl.shanelab.multiblock.validators;

import nl.shanelab.multiblock.IMaterialValidator;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class DyeColorValidator implements IMaterialValidator {

    private final DyeColor dyeColor;

    private final Material material;

    public DyeColorValidator(DyeColor dyeColor) {
        this(dyeColor, Material.WOOL);
    }

    public DyeColorValidator(DyeColor dyeColor, Material alternate) {
        this.dyeColor = dyeColor;
        this.material = alternate;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean validateBlock(Block block) {
        if (block == null || block.getType() != material) {
            return false;
        }

        return DyeColor.getByData(block.getData()) == dyeColor; // ((Wool)block.getState().getData()).getColor() == dyeColor;
    }
}
