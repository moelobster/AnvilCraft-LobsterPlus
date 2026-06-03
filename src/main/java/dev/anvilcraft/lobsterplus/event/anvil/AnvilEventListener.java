package dev.anvilcraft.lobsterplus.event.anvil;

import dev.anvilcraft.lobsterplus.LobsterPlus;
import dev.anvilcraft.lobsterplus.block.LobsterAnvilBlock;
import dev.dubhe.anvilcraft.api.event.AnvilEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = LobsterPlus.MOD_ID)
public class AnvilEventListener {
    @SubscribeEvent
    public static void onAnvilHurtEntity(AnvilEvent.HurtEntity event) {
        Entity hurtedEntity = event.getHurtedEntity();
        if (!(hurtedEntity instanceof LivingEntity entity)) return;
        if (!(hurtedEntity.level() instanceof ServerLevel serverLevel)) return;
        if (!(entity instanceof Zombie zombie)) return;
        Block block = event.getEntity().getBlockState().getBlock();
        if(!(block instanceof LobsterAnvilBlock anvilBlock))    return;
        zombie.doUnderWaterConversion(serverLevel);
    }
}
