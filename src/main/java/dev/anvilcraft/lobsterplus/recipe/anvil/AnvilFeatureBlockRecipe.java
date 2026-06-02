package dev.anvilcraft.lobsterplus.recipe.anvil;

import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.anvilcraft.lib.v2.util.predicate.BlockStatePredicate;
import dev.anvilcraft.lib.v2.util.predicate.ChanceBlockState;
import dev.anvilcraft.lobsterplus.init.recipe.ModRecipeTypes;
import dev.dubhe.anvilcraft.recipe.anvil.builder.AbstractRecipeBuilder;
import dev.dubhe.anvilcraft.recipe.anvil.predicate.block.HasAnvil;
import dev.dubhe.anvilcraft.recipe.anvil.util.WrapUtils;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.AbstractProcessRecipe;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Vec3i;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.NonNull;

import java.util.function.Consumer;


public class AnvilFeatureBlockRecipe extends AbstractProcessRecipe<AnvilFeatureBlockRecipe> {
    public static final RecipeSerializer<AnvilFeatureBlockRecipe> SERIALIZER = new RecipeSerializer<>(
        RecordCodecBuilder.mapCodec(instance -> instance.group(
            BlockStatePredicate.CODEC
                .fieldOf("ingredient")
                .forGetter(AnvilFeatureBlockRecipe::getFirstInputBlock),
            ChanceBlockState.CODEC
                .codec()
                .fieldOf("result")
                .forGetter(AnvilFeatureBlockRecipe::getFirstResultBlock),
            HasAnvil.CODEC
                .forGetter(AnvilFeatureBlockRecipe::getHasAnvil)
        ).apply(instance, AnvilFeatureBlockRecipe::new)),
        StreamCodec.composite(
            BlockStatePredicate.STREAM_CODEC,
            AnvilFeatureBlockRecipe::getFirstInputBlock,
            ChanceBlockState.STREAM_CODEC,
            AnvilFeatureBlockRecipe::getFirstResultBlock,
            HasAnvil.STREAM_CODEC,
            AnvilFeatureBlockRecipe::getHasAnvil,
            AnvilFeatureBlockRecipe::new
        )
    );

    /**
     * 构造一个铁砧特性配方
     *
     * @param ingredient    原料方块列表
     * @param result        结果方块列表
     * @param hasAnvil      铁砧条件
     */
    public AnvilFeatureBlockRecipe(
        BlockStatePredicate ingredient,
        ChanceBlockState result,
        HasAnvil hasAnvil
    ) {
        super(
            new Property()
                .setBlockInputOffset(new Vec3i(0, -1, 0))
                .setConsumeInputBlocks(true)
                .setInputBlocks(ingredient)
                .setHasAnvil(hasAnvil)
                .setBlockOutputOffset(new Vec3i(0, -1, 0))
                .setResultBlocks(result)
        );
    }

    @Override
    public @NonNull RecipeSerializer<AnvilFeatureBlockRecipe> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public @NonNull RecipeType<AnvilFeatureBlockRecipe> getType() {
        return ModRecipeTypes.ANVIL_FEATURE_BLOCK.get();
    }

    /**
     * 创建一个构建器实例
     *
     * @return 构建器实例
     */
    public static AnvilFeatureBlockRecipe.Builder builder() {
        return new AnvilFeatureBlockRecipe.Builder();
    }

    /**
     * 铁砧特性配方构建器
     */
    public static class Builder extends AbstractRecipeBuilder<AnvilFeatureBlockRecipe> {
        /**
         * 输入方块谓词
         */
        private BlockStatePredicate input = null;

        /**
         * 结果方块
         */
        private ChanceBlockState result = null;

        /**
         * 铁砧条件构建器
         */
        private HasAnvil hasAnvil = HasAnvil.DEFAULT;


        /**
         * 设置输入方块
         *
         * @param input 输入方块谓词
         *
         * @return 构建器实例
         */
        public AnvilFeatureBlockRecipe.Builder input(BlockStatePredicate input) {
            this.input = (input);
            return this;
        }

        /**
         * 设置输入方块（标签形式）
         *
         * @param input 输入方块标签
         *
         * @return 构建器实例
         */
        public AnvilFeatureBlockRecipe.Builder input(HolderGetter<Block> blocks, TagKey<Block> input) {
            this.input = BlockStatePredicate.builder().of(blocks, input).build();
            return this;
        }

        /**
         * 设置输入方块
         *
         * @param input 输入方块
         *
         * @return 构建器实例
         */
        public AnvilFeatureBlockRecipe.Builder input(Block input) {
            this.input = (BlockStatePredicate.builder().of(input).build());
            return this;
        }

        /**
         * 设置结果方块
         *
         * @param result 结果方块
         *
         * @return 构建器实例
         */
        public AnvilFeatureBlockRecipe.Builder result(ChanceBlockState result) {
            this.result = (result);
            return this;
        }

        /**
         * 设置结果方块
         *
         * @param block 结果方块
         *
         * @return 构建器实例
         */
        public AnvilFeatureBlockRecipe.Builder result(Block block) {
            this.result = (new ChanceBlockState(block.defaultBlockState(),1.0f));
            return this;
        }

        /**
         * 设置铁砧条件
         *
         * @param anvil 铁砧方块
         *
         * @return 构建器实例
         */
        public AnvilFeatureBlockRecipe.Builder anvil(Block anvil) {
            this.hasAnvil = new HasAnvil(BlockStatePredicate.builder().of(anvil));
            return this;
        }

        /**
         * 设置铁砧条件
         *
         * @param anvil 铁砧方块标签
         *
         * @return 构建器实例
         */
        public AnvilFeatureBlockRecipe.Builder anvil(HolderGetter<Block> blocks, TagKey<Block> anvil) {
            this.hasAnvil = new HasAnvil(BlockStatePredicate.builder().of(blocks, anvil));
            return this;
        }

        /**
         * 设置铁砧条件
         *
         * @param consumer 铁砧条件谓词消费者
         *
         * @return 构建器实例
         */
        public AnvilFeatureBlockRecipe.Builder anvil(Consumer<BlockStatePredicate.Builder> consumer) {
            BlockStatePredicate.Builder builder = BlockStatePredicate.builder();
            consumer.accept(builder);
            this.hasAnvil = new HasAnvil(builder);
            return this;
        }


        @Override
        public @NonNull AnvilFeatureBlockRecipe buildRecipe() {
            return new AnvilFeatureBlockRecipe(input, result, hasAnvil);
        }

        @Override
        public void validate(@NonNull Identifier id) {
            if (this.input == null) {
                throw new IllegalArgumentException("Recipe input must not be null, RecipeId: " + id);
            }
            if (this.result == null) {
                throw new IllegalArgumentException("Recipe result must not be null, RecipeId: " + id);
            }
        }

        @Override
        public @NonNull String getType() {
            return "anvil_feature_block";
        }

        @Override
        public @NonNull ItemStackTemplate getResult() {
            return WrapUtils.getItem(this.result);
        }
    }
}
