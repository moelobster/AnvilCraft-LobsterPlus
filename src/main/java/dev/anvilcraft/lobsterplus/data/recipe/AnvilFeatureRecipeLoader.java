package dev.anvilcraft.lobsterplus.data.recipe;

import dev.anvilcraft.lib.v2.registrum.providers.generators.RegistrumRecipeProvider;
import dev.anvilcraft.lobsterplus.init.ModBlocks;
import dev.anvilcraft.lobsterplus.recipe.anvil.AnvilFeatureBlockRecipe;
import dev.anvilcraft.lobsterplus.recipe.anvil.AnvilFeatureItemRecipe;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class AnvilFeatureRecipeLoader {
    public static void init(RegistrumRecipeProvider provider) {
        anvilFeature(provider, Blocks.DEAD_BRAIN_CORAL_BLOCK, Blocks.BRAIN_CORAL_BLOCK, ModBlocks.LOBSTER_ANVIL.get());
        anvilFeature(provider, Blocks.DEAD_BUBBLE_CORAL_BLOCK, Blocks.BUBBLE_CORAL_BLOCK, ModBlocks.LOBSTER_ANVIL.get());
        anvilFeature(provider, Blocks.DEAD_FIRE_CORAL_BLOCK, Blocks.FIRE_CORAL_BLOCK, ModBlocks.LOBSTER_ANVIL.get());
        anvilFeature(provider, Blocks.DEAD_HORN_CORAL_BLOCK, Blocks.HORN_CORAL_BLOCK, ModBlocks.LOBSTER_ANVIL.get());
        anvilFeature(provider, Blocks.DEAD_TUBE_CORAL_BLOCK, Blocks.TUBE_CORAL_BLOCK, ModBlocks.LOBSTER_ANVIL.get());
        anvilFeature(provider, Items.DEAD_BRAIN_CORAL, Items.BRAIN_CORAL, ModBlocks.LOBSTER_ANVIL.get());
        anvilFeature(provider, Items.DEAD_BUBBLE_CORAL, Items.BUBBLE_CORAL, ModBlocks.LOBSTER_ANVIL.get());
        anvilFeature(provider, Items.DEAD_FIRE_CORAL, Items.FIRE_CORAL, ModBlocks.LOBSTER_ANVIL.get());
        anvilFeature(provider, Items.DEAD_HORN_CORAL, Items.HORN_CORAL, ModBlocks.LOBSTER_ANVIL.get());
        anvilFeature(provider, Items.DEAD_TUBE_CORAL, Items.TUBE_CORAL, ModBlocks.LOBSTER_ANVIL.get());
        anvilFeature(provider, Items.DEAD_BRAIN_CORAL_FAN, Items.BRAIN_CORAL_FAN, ModBlocks.LOBSTER_ANVIL.get());
        anvilFeature(provider, Items.DEAD_BUBBLE_CORAL_FAN, Items.BUBBLE_CORAL_FAN, ModBlocks.LOBSTER_ANVIL.get());
        anvilFeature(provider, Items.DEAD_FIRE_CORAL_FAN, Items.FIRE_CORAL_FAN, ModBlocks.LOBSTER_ANVIL.get());
        anvilFeature(provider, Items.DEAD_HORN_CORAL_FAN, Items.HORN_CORAL_FAN, ModBlocks.LOBSTER_ANVIL.get());
        anvilFeature(provider, Items.DEAD_TUBE_CORAL_FAN, Items.TUBE_CORAL_FAN, ModBlocks.LOBSTER_ANVIL.get());
        anvilFeature(provider, Blocks.DIRT, Blocks.MUD, ModBlocks.LOBSTER_ANVIL.get());
    }

    private static void anvilFeature(RegistrumRecipeProvider provider, Block input, Block result, Block anvil) {
        AnvilFeatureBlockRecipe.builder()
            .input(input)
            .result(result)
            .anvil(anvil)
            .save(provider);
        AnvilFeatureItemRecipe.builder()
            .requires(input)
            .result(result)
            .anvil(anvil)
            .save(provider);
    }

    private static void anvilFeature(RegistrumRecipeProvider provider, Item input, Item result, Block anvil) {
        AnvilFeatureItemRecipe.builder()
            .requires(input)
            .result(result)
            .anvil(anvil)
            .save(provider);
    }
}
