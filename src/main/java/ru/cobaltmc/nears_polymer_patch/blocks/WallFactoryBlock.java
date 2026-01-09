package ru.cobaltmc.nears_polymer_patch.blocks;

import eu.pb4.factorytools.api.block.FactoryBlock;
import eu.pb4.factorytools.api.block.model.generic.BSMMParticleBlock;
import eu.pb4.factorytools.api.virtualentity.BlockModel;
import eu.pb4.polymer.blocks.api.BlockModelType;
import eu.pb4.polymer.blocks.api.PolymerBlockResourceUtils;
import eu.pb4.polymer.blocks.api.PolymerTexturedBlock;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import xyz.nucleoid.packettweaker.PacketContext;

import java.util.function.BiFunction;

public record WallFactoryBlock(boolean tick, BiFunction<BlockState, BlockPos, BlockModel> modelFunction) implements FactoryBlock, PolymerTexturedBlock, BSMMParticleBlock  {

    public static BlockState FACING_NORTH = PolymerBlockResourceUtils.requestEmpty(BlockModelType.SOUTH_TRAPDOOR);
    public static BlockState FACING_SOUTH = PolymerBlockResourceUtils.requestEmpty(BlockModelType.NORTH_TRAPDOOR);
    public static BlockState FACING_WEST = PolymerBlockResourceUtils.requestEmpty(BlockModelType.EAST_TRAPDOOR);
    public static BlockState FACING_EAST = PolymerBlockResourceUtils.requestEmpty(BlockModelType.WEST_TRAPDOOR);

    @Override
    public BlockState getPolymerBlockState(BlockState state, PacketContext context) {
        if (state.hasProperty(HorizontalDirectionalBlock.FACING)) {
            switch (state.getValue(HorizontalDirectionalBlock.FACING)) {
                case NORTH -> { return FACING_NORTH; }
                case SOUTH -> { return FACING_SOUTH; }
                case WEST -> { return FACING_WEST; }
                case EAST -> { return FACING_EAST; }
            }
        }
        return Blocks.BEDROCK.defaultBlockState();
    }

    @Override
    public @Nullable ElementHolder createElementHolder(ServerLevel world, BlockPos pos, BlockState initialBlockState) {
        return this.modelFunction.apply(initialBlockState, pos);
    }

    @Override
    public boolean tickElementHolder(ServerLevel world, BlockPos pos, BlockState initialBlockState) {
        return this.tick;
    }
}
