package dev.anvilcraft.lobsterplus.init.recipe;

import dev.anvilcraft.lobsterplus.LobsterPlus;
import dev.anvilcraft.lobsterplus.recipe.anvil.AnvilFeatureBlockRecipe;
import dev.anvilcraft.lobsterplus.recipe.anvil.AnvilFeatureItemRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipeSerializers {
    private static final DeferredRegister<RecipeSerializer<?>> DF = DeferredRegister.create(
        Registries.RECIPE_SERIALIZER,
        LobsterPlus.MOD_ID
    );

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<AnvilFeatureBlockRecipe>> ANVIL_FEATURE_BLOCK =
        DF.register(
            "anvil_feature_block",
            () -> AnvilFeatureBlockRecipe.SERIALIZER
        );

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<AnvilFeatureItemRecipe>> ANVIL_FEATURE_ITEM =
        DF.register(
            "anvil_feature_item",
            () -> AnvilFeatureItemRecipe.SERIALIZER
        );

    public static void register(IEventBus bus) {
        DF.register(bus);
    }
}
