package dev.anvilcraft.lobsterplus.init;

import dev.anvilcraft.lobsterplus.LobsterPlus;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static dev.anvilcraft.lobsterplus.LobsterPlus.REGISTRATE;

public class ModItemGroups {
    private static final DeferredRegister<CreativeModeTab> DEFERRED_REGISTER =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, LobsterPlus.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> Mod_ITEMS = DEFERRED_REGISTER.register(
            "addon_items",
            () -> CreativeModeTab.builder()
                    .icon(ModBlocks.LOBSTER_ANVIL::asStack)
                    .displayItems((ctx, entries) -> {
                    })
                    .title(REGISTRATE.addLang("itemGroup", LobsterPlus.of("mod_items"), "AnvilCraft: Lobster Plus"))
                    .withTabsBefore(dev.dubhe.anvilcraft.init.item.ModItemGroups.ANVILCRAFT_BUILD_BLOCK.getId())
                    .build()
    );
    public static void register(IEventBus modEventBus) {
        DEFERRED_REGISTER.register(modEventBus);
    }
}
