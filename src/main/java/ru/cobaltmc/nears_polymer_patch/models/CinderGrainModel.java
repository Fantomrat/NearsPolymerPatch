package ru.cobaltmc.nears_polymer_patch.models;

import eu.pb4.factorytools.api.virtualentity.BlockModel;
import eu.pb4.factorytools.api.virtualentity.ItemDisplayElementUtil;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import net.digitalpear.nears.Nears;
import net.digitalpear.nears.common.blocks.CInderGrainCropBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.Map;

public class CinderGrainModel extends BlockModel {

    private static final Map<Integer, ItemStack> AGE_MODELS = new HashMap<>();

    static {
        for (int age = 0; age <= 7; age++) {
            AGE_MODELS.put(
                    age,
                    ItemDisplayElementUtil.getModel(
                            Nears.id("block/cinder_grain" + age)
                    )
            );
        }
    }

    private final ItemDisplayElement main;

    public CinderGrainModel(BlockState state, BlockPos pos) {
        this.main = ItemDisplayElementUtil.createSimple();
        this.main.setScale(new Vector3f(1f));
        this.addElement(main);
        update(state);
    }

    private void update(BlockState state) {
        int age = state.getValue(CInderGrainCropBlock.AGE);
        main.setItem(AGE_MODELS.get(age));

        Direction facing = state.getValue(CInderGrainCropBlock.FACING);

        float yaw = switch (facing) {
            case NORTH -> 180f;
            case SOUTH -> 0f;
            case WEST  -> 90f;
            case EAST  -> -90f;
            default -> 0f;
        };

        main.setRotation(0f, yaw);
    }
}
