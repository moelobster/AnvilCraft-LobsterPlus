package dev.anvilcraft.lobsterplus.init.recipe;

import dev.anvilcraft.lobsterplus.LobsterPlus;
import dev.anvilcraft.lobsterplus.recipe.anvil.AnvilFeatureBlockRecipe;
import dev.anvilcraft.lobsterplus.recipe.anvil.AnvilFeatureItemRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipeTypes {
    private static final DeferredRegister<RecipeType<?>> DF = DeferredRegister.create(Registries.RECIPE_TYPE, LobsterPlus.MOD_ID);

    public static final DeferredHolder<RecipeType<?>, RecipeType<AnvilFeatureItemRecipe>> ANVIL_FEATURE = registerType("anvil_feature");
    public static final DeferredHolder<RecipeType<?>, RecipeType<AnvilFeatureBlockRecipe>> ANVIL_FEATURE_BLOCK = registerType("anvil_feature_block");


    private static <T extends Recipe<?>> DeferredHolder<RecipeType<?>, RecipeType<T>> registerType(String name) {
        return DF.register(name, RecipeType::simple);
    }

    public static void register(IEventBus bus) {
        DF.register(bus);
    }
}
