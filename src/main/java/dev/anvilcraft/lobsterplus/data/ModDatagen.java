package dev.anvilcraft.lobsterplus.data;

import dev.anvilcraft.lib.v2.registrum.providers.ProviderType;
import dev.anvilcraft.lobsterplus.LobsterPlus;
import dev.anvilcraft.lobsterplus.data.lang.LangHandler;
import dev.anvilcraft.lobsterplus.data.recipe.RecipeHandler;
import net.neoforged.fml.common.EventBusSubscriber;

import static dev.anvilcraft.lobsterplus.LobsterPlus.REGISTRATE;

@EventBusSubscriber(modid = LobsterPlus.MOD_ID)
public class ModDatagen {
    /**
     * 初始化生成器
     */
    public static void init() {
        REGISTRATE.addDataGenerator(ProviderType.LANG, LangHandler::init);
        REGISTRATE.addDataGenerator(ProviderType.RECIPE, RecipeHandler::init);
    }
}
