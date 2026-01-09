package ru.cobaltmc.nears_polymer_patch.mixin;

import eu.pb4.factorytools.api.block.FactoryBlock;
import eu.pb4.factorytools.api.block.model.generic.BlockStateModelManager;
import eu.pb4.polymer.core.api.block.PolymerBlock;
import eu.pb4.polymer.core.api.item.PolymerItem;
import eu.pb4.polymer.virtualentity.api.BlockWithElementHolder;
import net.digitalpear.nears.Nears;
import net.digitalpear.nears.common.blocks.*;
import net.digitalpear.nears.init.NBlocks;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.cobaltmc.nears_polymer_patch.blocks.BaseFactoryBlock;
import ru.cobaltmc.nears_polymer_patch.blocks.WallFactoryBlock;
import ru.cobaltmc.nears_polymer_patch.blocks.WaterloggableFactoryBlock;
import ru.cobaltmc.nears_polymer_patch.items.PolyBaseItem;
import ru.cobaltmc.nears_polymer_patch.models.CinderGrainModel;

import java.util.function.Function;

@Mixin(NBlocks.class)
public class NBlocksMixin {


    @Inject(
            method = "createBlockWithoutItem",
            at = @At("RETURN")
    )
    private static void onBlockRegistered(
            String blockID,
            Function<BlockBehaviour.Properties, Block> factory,
            BlockBehaviour.Properties settings,
            CallbackInfoReturnable<Block> cir
    ) {
        var block = cir.getReturnValue();
        FactoryBlock polymerBlock = switch (block) {
            case CInderGrainCropBlock cInderGrainCropBlock -> new WallFactoryBlock(false, CinderGrainModel::new);
            case NearHangStemBlock nearHangStemBlock -> BaseFactoryBlock.VINE;
            case NearHangBlock nearHangBlock -> BaseFactoryBlock.BARRIER;
            case FaarGrowthBlock faarGrowthBlock -> BaseFactoryBlock.SAPLING;
            case SoulBerryBushBlock soulBerryBushBlock -> BaseFactoryBlock.SAPLING;
            case FlowerPotBlock flowerPotBlock -> BaseFactoryBlock.BARRIER;
            case null, default ->
                    block instanceof SimpleWaterloggedBlock ? WaterloggableFactoryBlock.BARRIER : BaseFactoryBlock.BARRIER;
        };

        PolymerBlock.registerOverlay(block, polymerBlock);
        BlockWithElementHolder.registerOverlay(block, polymerBlock);
        BlockStateModelManager.addBlock(Nears.id(blockID), block);
    }

    @Inject(
            method = "createBlockWithItem*",
            at = @At("RETURN")
    )
    private static void onBlockRegisteredItem(
            String blockID,
            Function<BlockBehaviour.Properties, Block> factory,
            BlockBehaviour.Properties settings,
            CallbackInfoReturnable<Block> cir
    ) {
        var block = cir.getReturnValue();
        FactoryBlock polymerBlock = switch (block) {
            case CinderGrassBlock cinderGrassBlock -> BaseFactoryBlock.SAPLING;
            case FaarBundleBlock faarBundleBlock -> BaseFactoryBlock.BARRIER;
            case HayBlock hayBlock -> BaseFactoryBlock.BARRIER;
            case null, default ->
                    block instanceof SimpleWaterloggedBlock ? WaterloggableFactoryBlock.BARRIER : BaseFactoryBlock.BARRIER;
        };

        PolymerBlock.registerOverlay(block, polymerBlock);
        BlockWithElementHolder.registerOverlay(block, polymerBlock);
        BlockStateModelManager.addBlock(Nears.id(blockID), block);
    }

    @Inject(
            method = "createBlockItem",
            at = @At("RETURN")
    )
    private static void onBlockItemRegistered(Block block,CallbackInfoReturnable<Item> cir) {
        PolymerItem polymerItem;

        polymerItem = new PolyBaseItem(cir.getReturnValue());

        PolymerItem.registerOverlay(cir.getReturnValue(), polymerItem);
    }
}