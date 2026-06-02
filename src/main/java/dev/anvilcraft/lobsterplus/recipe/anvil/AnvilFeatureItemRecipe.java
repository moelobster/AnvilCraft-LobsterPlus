package dev.anvilcraft.lobsterplus.recipe.anvil;

import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.anvilcraft.lib.v2.util.predicate.BlockStatePredicate;
import dev.anvilcraft.lib.v2.util.predicate.ChanceItemStack;
import dev.anvilcraft.lib.v2.util.predicate.ItemIngredientPredicate;
import dev.anvilcraft.lobsterplus.init.recipe.ModRecipeTypes;
import dev.dubhe.anvilcraft.recipe.anvil.predicate.block.HasAnvil;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.AbstractProcessRecipe;
import dev.dubhe.anvilcraft.recipe.component.HasCauldronSimple;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Vec3i;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.function.Consumer;

public class AnvilFeatureItemRecipe extends AbstractProcessRecipe<AnvilFeatureItemRecipe> {
    public static final RecipeSerializer<AnvilFeatureItemRecipe> SERIALIZER = new RecipeSerializer<>(
        RecordCodecBuilder.mapCodec(instance -> instance.group(
            ItemIngredientPredicate.CODEC
                .listOf()
                .optionalFieldOf("ingredients", List.of())
                .forGetter(AnvilFeatureItemRecipe::getInputItems),
            ChanceItemStack.CODEC
                .listOf()
                .optionalFieldOf("results", List.of())
                .forGetter(AnvilFeatureItemRecipe::getResultItems),
            HasCauldronSimple.CODEC
                .forGetter(AnvilFeatureItemRecipe::getHasCauldron),
            HasAnvil.CODEC
                .forGetter(AnvilFeatureItemRecipe::getHasAnvil)
        ).apply(instance, AnvilFeatureItemRecipe::new)),
        StreamCodec.composite(
            ItemIngredientPredicate.STREAM_CODEC.apply(ByteBufCodecs.list()),
            AnvilFeatureItemRecipe::getInputItems,
            ChanceItemStack.STREAM_CODEC.apply(ByteBufCodecs.list()),
            AnvilFeatureItemRecipe::getResultItems,
            HasCauldronSimple.STREAM_CODEC,
            AnvilFeatureItemRecipe::getHasCauldron,
            HasAnvil.STREAM_CODEC,
            AnvilFeatureItemRecipe::getHasAnvil,
            AnvilFeatureItemRecipe::new
        )
    );

    /**
     * 构造一个铁砧特性配方
     *
     * @param itemIngredients 原料物品列表
     * @param results         结果物品列表
     * @param hasAnvil        铁砧条件
     */
    public AnvilFeatureItemRecipe(
        List<ItemIngredientPredicate> itemIngredients,
        List<ChanceItemStack> results,
        HasCauldronSimple hasCauldron,
        HasAnvil hasAnvil
    ) {
        super(
            new Property()
                .setItemInputOffset(new Vec3(0.0, -0.375, 0.0))
                .setItemInputRange(new Vec3(0.75, 0.75, 0.75))
                .setInputItems(itemIngredients)
                .setItemOutputOffset(new Vec3(0.0, -0.75, 0.0))
                .setResultItems(results)
                .setCauldronOffset(new Vec3i(0, -1, 0))
                .setHasCauldron(hasCauldron)
                .setHasAnvil(hasAnvil)
        );

    }

    @Override
    public @NonNull RecipeSerializer<AnvilFeatureItemRecipe> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public @NonNull RecipeType<AnvilFeatureItemRecipe> getType() {
        return ModRecipeTypes.ANVIL_FEATURE.get();
    }


    /**
     * 创建一个构建器实例
     *
     * @return 构建器实例
     */
    public static AnvilFeatureItemRecipe.Builder builder() {
        return new AnvilFeatureItemRecipe.Builder();
    }

    /**
     * 铁砧特性配方构建器
     */
    public static class Builder extends AbstractProcessRecipe.AbstractBuilder<AnvilFeatureItemRecipe, Builder> {
        /**
         * 炼药锅条件构建器
         */
        private final HasCauldronSimple.Builder hasCauldron = HasCauldronSimple.empty();

        /**
         * 铁砧条件构建器
         */
        private HasAnvil hasAnvil = HasAnvil.DEFAULT;

        /**
         * 设置铁砧条件
         *
         * @param anvil 铁砧方块
         * @return 构建器实例
         */
        public AnvilFeatureItemRecipe.Builder anvil(Block anvil) {
            this.hasAnvil = new HasAnvil(BlockStatePredicate.builder().of(anvil));
            return this;
        }

        /**
         * 设置铁砧条件
         *
         * @param anvil 铁砧方块标签
         * @return 构建器实例
         */
        public AnvilFeatureItemRecipe.Builder anvil(HolderGetter<Block> blocks, TagKey<Block> anvil) {
            this.hasAnvil = new HasAnvil(BlockStatePredicate.builder().of(blocks, anvil));
            return this;
        }

        /**
         * 设置铁砧条件
         *
         * @param consumer 铁砧条件谓词消费者
         * @return 构建器实例
         */
        public AnvilFeatureItemRecipe.Builder anvil(Consumer<BlockStatePredicate.Builder> consumer) {
            BlockStatePredicate.Builder builder = BlockStatePredicate.builder();
            consumer.accept(builder);
            this.hasAnvil = new HasAnvil(builder);
            return this;
        }

        @Override
        public @NonNull AnvilFeatureItemRecipe buildRecipe() {
            return new AnvilFeatureItemRecipe(
                this.itemIngredients,
                this.results,
                this.hasCauldron.build(),
                this.hasAnvil
            );
        }

        @Override
        public void validate(@NonNull Identifier id) {
            if (itemIngredients.isEmpty()) {
                throw new IllegalArgumentException("Recipe ingredients must not be empty, RecipeId: " + id);
            }
            if (results.isEmpty()) {
                throw new IllegalArgumentException("Recipe result must not be empty, RecipeId: " + id);
            }
        }

        @Override
        public @NonNull String getType() {
            return "anvil_feature_item";
        }

        @Override
        protected @NonNull Builder getThis() {
            return this;
        }
    }
}
