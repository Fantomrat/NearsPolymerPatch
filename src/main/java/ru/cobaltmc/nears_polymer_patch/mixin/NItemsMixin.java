package ru.cobaltmc.nears_polymer_patch.mixin;

import eu.pb4.polymer.core.api.item.PolymerItem;
import net.digitalpear.nears.init.NItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.cobaltmc.nears_polymer_patch.items.PolyBaseItem;

import java.util.function.Function;

@Mixin(NItems.class)
public class NItemsMixin {
    @Inject(
            method = "createItem*",
            at = @At("RETURN")
    )
    private static void onItemRegistered(String id, Function<Item.Properties, Item> factory, Item.Properties settings, CallbackInfoReturnable<Item> cir) {
        PolymerItem polymerItem;

        polymerItem = new PolyBaseItem(cir.getReturnValue());

        PolymerItem.registerOverlay(cir.getReturnValue(), polymerItem);
    }

    @Inject(
            method = "createBlockItem",
            at = @At("RETURN")
    )
    private static void onBlockItemRegistered(String id, Block block, CallbackInfoReturnable<Item> cir) {
        PolymerItem polymerItem;

        polymerItem = new PolyBaseItem(cir.getReturnValue());

        PolymerItem.registerOverlay(cir.getReturnValue(), polymerItem);
    }
}

