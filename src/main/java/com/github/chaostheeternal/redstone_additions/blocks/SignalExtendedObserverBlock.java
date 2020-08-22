package com.github.chaostheeternal.redstone_additions.blocks;

import java.util.Random;

import com.github.chaostheeternal.redstone_additions.interfaces.IRedstoneWireMixin;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.ObserverBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class SignalExtendedObserverBlock extends ObserverBlock implements IRedstoneWireMixin {
    public static final IntProperty DELAY = Properties.DELAY;
    public static final IntProperty COUNTDOWN = IntProperty.of("countdown", 0, 4);
    public static final String ID = "signal_extended_observer";
    public static final SignalExtendedObserverBlock BLOCK = new SignalExtendedObserverBlock(FabricBlockSettings.of(Material.STONE).hardness(3.0F));
    public SignalExtendedObserverBlock(Settings settings) {
        super(settings);
        this.setDefaultState(getStateManager().getDefaultState().with(DELAY, 1).with(COUNTDOWN, 0));
    }

    public static BlockItem getBlockItem() {
        return new BlockItem(BLOCK, new Item.Settings().group(ItemGroup.REDSTONE));
    }
    
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!player.abilities.allowModifyWorld) {
            return ActionResult.PASS;
        } else {
            world.setBlockState(pos, (BlockState) state.cycle(DELAY), 3);
            return ActionResult.success(world.isClient);
        }
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        if (state.get(FACING) == direction) {
            if (!state.get(POWERED)) { this.scheduleTick(world, pos); }
            world.setBlockState(pos, state.with(COUNTDOWN, state.get(DELAY)), 2);
        }
        return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(POWERED)) {
            int newCount = state.get(COUNTDOWN) - 1;
            if (newCount <= 0) {
                world.setBlockState(pos, (BlockState) state.with(COUNTDOWN, 0).with(POWERED, false), 2);
            } else {
                world.setBlockState(pos, (BlockState) state.with(COUNTDOWN, newCount), 2);
                world.getBlockTickScheduler().schedule(pos, this, 2);
            }
        } else {
            world.setBlockState(pos, (BlockState) state.with(POWERED, true), 2);
            world.getBlockTickScheduler().schedule(pos, this, 2);
        }
        this.updateNeighbors(world, pos, state);
    }
    
    private void scheduleTick(WorldAccess world, BlockPos pos) {
        if (!world.isClient() && !world.getBlockTickScheduler().isScheduled(pos, this)) {
            world.getBlockTickScheduler().schedule(pos, this, 2);
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED, DELAY, COUNTDOWN);
    }

    public boolean canRedstoneConnect(BlockState state, Direction dir) {
        return dir == state.get(FACING);
    }
}