package nl.shanelab.multiblock.patternobjects;

import nl.shanelab.multiblock.IMaterial;
import nl.shanelab.multiblock.MaterialWrapper;
import nl.shanelab.multiblock.PatternObject;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;

/**
 * Block pattern for the multiblock structure
 *
 * @author ShaneCraft
 */
public class PatternBlock extends PatternObject {

    private final IMaterial material;
    private ItemStack itemStack;

    public PatternBlock(@Nonnull Material blockMaterial, int x, int y, int z) {
        this(blockMaterial, new Vector(x, y, z));
        if (x > 2 || x < -2 || y > 2 || y < -2 || z > 2 || z < -2)
            throw new IllegalArgumentException("Multiblocks must be a max size of 3x3!!");
    }

    public PatternBlock(ItemStack itemStack, int x, int y, int z) {
        this(itemStack, new Vector(x, y, z));
        if (x > 2 || x < -2 || y > 2 || y < -2 || z > 2 || z < -2)
            throw new IllegalArgumentException("Multiblocks must be a max size of 3x3!!");
    }

    public PatternBlock(@Nonnull Material blockMaterial, Vector relativeVec) {
        super(relativeVec);

        if (!blockMaterial.isBlock()) {
            throw new IllegalArgumentException(String.format("The given blockMaterial %s is not a valid block material.", blockMaterial.toString()));
        }

        this.material = new MaterialWrapper(blockMaterial);
        this.itemStack = new ItemStack(blockMaterial);
    }

    public PatternBlock(@Nonnull ItemStack itemStack, Vector relativeVec) {
        super(relativeVec);

        if (!itemStack.getType().isBlock()) {
            throw new IllegalArgumentException(String.format("The given blockMaterial %s is not a valid block material.", itemStack.getType().toString()));
        }

        this.material = new MaterialWrapper(itemStack.getType());
        this.itemStack = itemStack;
    }

    public PatternBlock(@Nonnull IMaterial material, int x, int y, int z) {
        this(material, new Vector(x, y, z));
    }

    public PatternBlock(@Nonnull IMaterial material, Vector relativeVec) {
        super(relativeVec);

        if (!material.getType().isBlock()) {
            throw new IllegalArgumentException(String.format("The given blockMaterial %s is not a valid block material.", material.getType().toString()));
        }

        this.material = material;
    }

    @Override
    protected PatternObject createRotatedClone(Vector vector) {
        return new PatternBlock(material, vector);
    }

    @Override
    public boolean isValid(Location location) {
        return material.isValidBlock(location.getBlock());
    }

    public Material getMaterial() {
        return material.getType();
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }
}
