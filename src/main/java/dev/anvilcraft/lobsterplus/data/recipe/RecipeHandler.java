package dev.anvilcraft.lobsterplus.data.recipe;

import dev.anvilcraft.lib.v2.registrum.providers.generators.RegistrumRecipeProvider;

public class RecipeHandler {
    public static void init(RegistrumRecipeProvider provider) {
        AnvilFeatureRecipeLoader.init(provider);
    }
}
