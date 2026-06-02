package dev.anvilcraft.lobsterplus.init;

import dev.anvilcraft.lib.v2.registrum.util.entry.BlockEntry;
import dev.anvilcraft.lobsterplus.block.LobsterAnvilBlock;
import dev.anvilcraft.lobsterplus.data.recipe.RegistrumBlockRecipeLoader;
import dev.dubhe.anvilcraft.init.block.ModBlockTags;
import dev.dubhe.anvilcraft.util.registrater.DataGenUtil;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Blocks;

import static dev.anvilcraft.lobsterplus.LobsterPlus.REGISTRATE;

public class ModBlocks {
    static {
        REGISTRATE.defaultCreativeTab(ModItemGroups.Mod_ITEMS.getKey());
    }

    public static final BlockEntry<LobsterAnvilBlock> LOBSTER_ANVIL = REGISTRATE
            .block("lobster_anvil", LobsterAnvilBlock::new)
            .initialProperties(() -> Blocks.FIRE_CORAL_BLOCK)
            .properties(p -> p.noOcclusion().isValidSpawn(Blocks::never))
            .blockstate(DataGenUtil::noExtraModelOrState)
            .item()
            .tag(ItemTags.ANVIL)
            .build()
            .tag(BlockTags.ANVIL, BlockTags.MINEABLE_WITH_PICKAXE, ModBlockTags.NON_MAGNETIC, ModBlockTags.CANT_BROKEN_ANVIL)
            .recipe(RegistrumBlockRecipeLoader::LobsterAnvil)
            .register();


    public static void register() {
    }
}
