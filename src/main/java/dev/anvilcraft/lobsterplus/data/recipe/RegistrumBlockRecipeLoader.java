package dev.anvilcraft.lobsterplus.data.recipe;

import dev.anvilcraft.lib.v2.registrum.providers.DataGenContext;
import dev.anvilcraft.lib.v2.registrum.providers.generators.RegistrumRecipeProvider;
import dev.dubhe.anvilcraft.data.AnvilCraftDatagen;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

public class RegistrumBlockRecipeLoader {
    public static <T extends Block> void LobsterAnvil(DataGenContext<Block, T> ctx, RegistrumRecipeProvider provider) {
        HolderGetter<Item> lookup = provider.getItems();
        ShapedRecipeBuilder.shaped(lookup, RecipeCategory.MISC, ctx.get())
                .pattern("AAA")
                .pattern(" B ")
                .pattern("BBB")
                .define('A', Items.NAUTILUS_SHELL)
                .define('B', Items.FIRE_CORAL_BLOCK)
                .unlockedBy(AnvilCraftDatagen.hasItem(Items.NAUTILUS_SHELL), AnvilCraftDatagen.has(lookup, Items.NAUTILUS_SHELL))
                .save(provider);
    }
}
