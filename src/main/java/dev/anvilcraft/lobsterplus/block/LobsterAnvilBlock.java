package dev.anvilcraft.lobsterplus.block;

import dev.dubhe.anvilcraft.api.hammer.IHammerRemovable;
import dev.dubhe.anvilcraft.block.better.BetterAnvilBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

public class LobsterAnvilBlock extends BetterAnvilBlock implements IHammerRemovable {
    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
    private static final VoxelShape BASE = Block.box(2.0, 0.0, 2.0, 14.0, 4.0, 14.0);
    private static final VoxelShape X_LEG1 = Block.box(4.0, 4.0, 5.0, 12.0, 10.0, 11.0);
    private static final VoxelShape X_TOP = Block.box(0.0, 10.0, 3.0, 16.0, 16.0, 13.0);
    private static final VoxelShape Z_LEG1 = Block.box(5.0, 4.0, 4.0, 11.0, 10.0, 12.0);
    private static final VoxelShape Z_TOP = Block.box(3.0, 10.0, 0.0, 13.0, 16.0, 16.0);
    private static final VoxelShape X_AXIS_AABB = Shapes.or(BASE, X_LEG1, X_TOP);
    private static final VoxelShape Z_AXIS_AABB = Shapes.or(BASE, Z_LEG1, Z_TOP);
    public static final Component CONTAINER_TITLE = Component.translatable("container.repair");

    public LobsterAnvilBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any()
            .setValue(FACING, Direction.NORTH));
    }

    @Override
    public int getDustColor(
        @NonNull BlockState blockState,
        @NonNull BlockGetter blockGetter,
        @NonNull BlockPos blockPos
    ) {
        return blockState.getMapColor(blockGetter, blockPos).col;
    }

    @Override
    public @NonNull BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getClockWise());
    }

    @Override
    protected @NonNull VoxelShape getShape(
        BlockState state,
        @NonNull BlockGetter level,
        @NonNull BlockPos pos,
        @NonNull CollisionContext context
    ) {
        Direction direction = state.getValue(FACING);
        return direction.getAxis() == Direction.Axis.X ? X_AXIS_AABB : Z_AXIS_AABB;
    }

    @Override
    public void falling(FallingBlockEntity entity) {
        entity.setHurtsEntities(0.5f, 15);
    }

    @Override
    public void onLand(
        @NonNull Level level,
        @NonNull BlockPos pos,
        @NonNull BlockState state,
        @NonNull BlockState replaceableState,
        FallingBlockEntity fallingBlock
    ) {
        if (!fallingBlock.isSilent()) {
            level.playSound(null, pos, SoundEvents.BABY_NAUTILUS_AMBIENT, SoundSource.BLOCKS, 1.0F, 1.0F);
        }
    }

    @Override
    public void onBrokenAfterFall(
        @NonNull Level level,
        @NonNull BlockPos pos,
        FallingBlockEntity fallingBlock
    ) {
        if (!fallingBlock.isSilent()) {
            level.levelEvent(1029, pos, 0);
        }
    }

    @Override
    protected boolean isPathfindable(
        @NonNull BlockState state,
        @NonNull PathComputationType pathComputationType
    ) {
        return false;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    protected @NonNull BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public @NonNull DamageSource getFallDamageSource(Entity entity) {
        return entity.damageSources().anvil(entity);
    }


    @Override
    protected @Nullable MenuProvider getMenuProvider(
        @NonNull BlockState state,
        @NonNull Level level,
        @NonNull BlockPos pos
    ) {
        return new SimpleMenuProvider(
            (syncId, inventory, player) ->
                new AnvilMenu(
                    syncId,
                    inventory,
                    ContainerLevelAccess.create(level, pos)
                ),
            CONTAINER_TITLE
        );
    }
}
