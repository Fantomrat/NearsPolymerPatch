package ru.cobaltmc.nears_polymer_patch;

import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import eu.pb4.polymer.resourcepack.extras.api.ResourcePackExtras;
import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.ResourceLocation;

public class Nears_polymer_patch implements ModInitializer {
    public static final String MOD_ID = "nears_polymer_patch";

    @Override
    public void onInitialize() {
        PolymerResourcePackUtils.addModAssets("nears");
        PolymerResourcePackUtils.addModAssets(MOD_ID);

        ResourcePackExtras.forDefault().addBridgedModelsFolder(ResourceLocation.fromNamespaceAndPath("nears", "block"));

    }
}
