package com.github.chaostheeternal.redstone_additions.blocks;

import java.util.Random;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractRedstoneGateBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class RedstoneInverterBlock extends AbstractRedstoneGateBlock {
    public static final BooleanProperty BURNED_OUT = BooleanProperty.of("burned_out");
    public static final String ID = "redstone_inverter";
    public static final Block BLOCK = new RedstoneInverterBlock(FabricBlockSettings.of(Material.SUPPORTED).breakInstantly().sounds(BlockSoundGroup.WOOD));
    public RedstoneInverterBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(POWERED, false).with(BURNED_OUT, false));
    }
    public static BlockItem getBlockItem() {
        return new BlockItem(BLOCK, new Item.Settings().group(ItemGroup.REDSTONE));
    }

    @Override
    protected int getUpdateDelayInternal(BlockState state) {
        return 0;
    }
    @Override
    protected int getMaxInputLevelSides(WorldView world, BlockPos pos, BlockState state) {
        // TODO Auto-generated method stub
        return super.getMaxInputLevelSides(world, pos, state);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (!state.get(POWERED) && !state.get(BURNED_OUT)) {
            Direction direction = state.get(FACING);
            double d0 = (double)((float)pos.getX() + 0.5F) + (double)(random.nextFloat() - 0.5F) * 0.2D;
            double d1 = (double)((float)pos.getY() + 0.4F) + (double)(random.nextFloat() - 0.5F) * 0.2D;
            double d2 = (double)((float)pos.getZ() + 0.5F) + (double)(random.nextFloat() - 0.5F) * 0.2D;
            float f = -5.0F;
            if (random.nextBoolean()) { f = (float)((random.nextInt(4) + 1) * 2 - 1); }
            f = f / 16.0F;
            double d3 = (double)(f * (float)direction.getOffsetX());
            double d4 = (double)(f * (float)direction.getOffsetZ());
            world.addParticle(DustParticleEffect.RED, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!this.isLocked(world, pos, state) && !state.get(BURNED_OUT) && !burnoutCheck(state, world, pos, random, this.hasPower(world, pos, state))) {
            boolean bl = (Boolean) state.get(POWERED);
            boolean bl2 = this.hasPower(world, pos, state);
            if (bl && !bl2) {
                world.setBlockState(pos, (BlockState) state.with(POWERED, false), 2);
            } else if (!bl) {
                world.setBlockState(pos, (BlockState) state.with(POWERED, true), 2);
                if (!bl2) {
                    world.getBlockTickScheduler().schedule(pos, this, this.getUpdateDelayInternal(state), net.minecraft.world.TickPriority.VERY_HIGH);
                }
            }
        }
    }

    @Override
    protected void appendProperties(Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED, BURNED_OUT);
    }

    @Override
    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        if (state.get(POWERED) || state.get(BURNED_OUT)) {
            return 0;
        } else {
            return state.get(FACING) == direction ? this.getOutputLevel(world, pos, state) : 0;
        }
    }

    //TODO: Need to figure out a "can connect redstone" method, below is what was from Forge
    // @Override
    // public boolean canConnectRedstone(BlockState state, World world, BlockPos pos, Direction side) {
    //     if (side == null) return false; //This seems to fire if redstone is placed on a neighboring block on a different Y
    //     Direction myFacing = state.get(FACING);
    //     return (myFacing == side || myFacing == side.getOpposite()); //Input is the way this block is facing, output is opposite the way this block is facing
    // }

    //#region Burnout Logic
    private static final java.util.Map<World, java.util.List<RedstoneInverterBlock.Toggle>> BURNED_INVERTERS = new java.util.WeakHashMap<>();
    
    public static boolean burnoutCheck(BlockState state, World worldIn, BlockPos pos, Random rand, boolean shouldBePowered) {
        java.util.List<RedstoneInverterBlock.Toggle> list = BURNED_INVERTERS.get(worldIn);

        while(list != null && !list.isEmpty() && worldIn.getTime() - (list.get(0)).time > 60L) {
            list.remove(0);
        }

        if (!state.get(POWERED)) {
            if (shouldBePowered) {
                if (isBurnedOut(worldIn, pos, false)) {
                    worldIn.syncWorldEvent(1502, pos, 0); //burnout effect
                    worldIn.setBlockState(pos, state.with(POWERED, true).with(BURNED_OUT, true), 3);
                    worldIn.getBlockTickScheduler().schedule(pos, worldIn.getBlockState(pos).getBlock(), 160);
                    return true;
                } else {
                    worldIn.setBlockState(pos, state.with(POWERED, true), 3);
                }
            }
        } else if (!shouldBePowered && !isBurnedOut(worldIn, pos, true)) {
            worldIn.setBlockState(pos, state.with(POWERED, false), 3);
        }  
        return false;
    }
    private static boolean isBurnedOut(World worldIn, BlockPos pos, boolean isPowered) {
        java.util.List<RedstoneInverterBlock.Toggle> list = BURNED_INVERTERS.computeIfAbsent(worldIn, (i) -> { return com.google.common.collect.Lists.newArrayList(); });
        if (isPowered) { list.add(new RedstoneInverterBlock.Toggle(pos.toImmutable(), worldIn.getTime())); }
        int i = 0;  
        for(int j = 0; j < list.size(); ++j) {
            RedstoneInverterBlock.Toggle inverterblock$toggle = list.get(j);
            if (inverterblock$toggle.pos.equals(pos) && ++i >= 8) { return true; }
        }  
        return false;
    }

    public static class Toggle {
        private final BlockPos pos;
        private final long time;
  
        public Toggle(BlockPos pos, long time) {
            this.pos = pos;
            this.time = time;
        }
    }
    //#endregion
}