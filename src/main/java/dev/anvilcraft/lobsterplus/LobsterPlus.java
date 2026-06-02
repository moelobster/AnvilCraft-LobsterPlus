package dev.anvilcraft.lobsterplus;

import com.mojang.logging.LogUtils;
import dev.anvilcraft.lib.v2.registrum.Registrum;
import dev.anvilcraft.lobsterplus.data.ModDatagen;
import dev.anvilcraft.lobsterplus.init.ModBlocks;
import dev.anvilcraft.lobsterplus.init.ModItemGroups;
import dev.anvilcraft.lobsterplus.init.recipe.ModRecipeSerializers;
import dev.anvilcraft.lobsterplus.init.recipe.ModRecipeTypes;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(LobsterPlus.MOD_ID)
public class LobsterPlus {
    public static final String MOD_ID = "anvilcraft_lobsterplus";
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final Registrum REGISTRATE = Registrum.create(MOD_ID);


    public LobsterPlus(IEventBus modEventBus, ModContainer modContainer) {
        LOGGER.info("Hello Lobster");
        ModItemGroups.register(modEventBus);
        ModBlocks.register();

        ModDatagen.init();
        ModRecipeTypes.register(modEventBus);
        ModRecipeSerializers.register(modEventBus);
    }

    public static Identifier of(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
