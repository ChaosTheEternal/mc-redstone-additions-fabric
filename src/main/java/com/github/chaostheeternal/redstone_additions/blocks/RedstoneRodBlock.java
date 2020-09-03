package com.github.chaostheeternal.redstone_additions.blocks;

import java.util.Iterator;
import java.util.Random;

import com.github.chaostheeternal.redstone_additions.RedstoneAdditionsMod;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FacingBlock;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class RedstoneRodBlock extends FacingBlock implements Waterloggable {
    protected static final VoxelShape Y_SHAPE = Block.createCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);
    protected static final VoxelShape Z_SHAPE = Block.createCuboidShape(6.0D, 6.0D, 0.0D, 10.0D, 10.0D, 16.0D);
    protected static final VoxelShape X_SHAPE = Block.createCuboidShape(0.0D, 6.0D, 6.0D, 16.0D, 10.0D, 10.0D); 
    private static final Vector3f[] rgbPowerLevel;

    public static final DirectionProperty POWER_TO = DirectionProperty.of("power_to", Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.UP, Direction.DOWN);
    public static final IntProperty POWER = Properties.POWER;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final String ID = "redstone_rod";
    public static final Block BLOCK = new RedstoneRodBlock(FabricBlockSettings.of(Material.SUPPORTED).breakInstantly().sounds(BlockSoundGroup.WOOD).nonOpaque());
    protected RedstoneRodBlock(Settings settings) {
        super(settings);
        this.setDefaultState(getStateManager().getDefaultState().with(FACING, Direction.UP).with(POWER_TO, Direction.UP).with(POWER, 0).with(WATERLOGGED, false));
    }
    public static void RegisterBlock() {
        Registry.register(Registry.BLOCK, new Identifier(RedstoneAdditionsMod.MOD_ID, ID), BLOCK);
        Registry.register(Registry.ITEM, new Identifier(RedstoneAdditionsMod.MOD_ID, ID), new BlockItem(BLOCK, new Item.Settings().group(ItemGroup.REDSTONE)));
    }

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return (BlockState)state.with(FACING, rotation.rotate((Direction)state.get(FACING)));
    }
 
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return (BlockState)state.with(FACING, mirror.apply((Direction)state.get(FACING)));
    }
 
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch(((Direction)state.get(FACING)).getAxis()) {
        case X:
        default:
            return X_SHAPE;
        case Z:
            return Z_SHAPE;
        case Y:
            return Y_SHAPE;
        }
    }
 
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        Direction direction = ctx.getSide();
        return this.getDefaultState().with(FACING, direction).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }
 
    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        int powerLevel = state.get(POWER);
        if (powerLevel != 0) {
            Vector3f rgb = rgbPowerLevel[powerLevel];
            Direction direction = (Direction)state.get(FACING);
            double d = (double)pos.getX() + 0.55D - (double)(random.nextFloat() * 0.1F);
            double e = (double)pos.getY() + 0.55D - (double)(random.nextFloat() * 0.1F);
            double f = (double)pos.getZ() + 0.55D - (double)(random.nextFloat() * 0.1F);
            double g = (double)(0.4F - (random.nextFloat() + random.nextFloat()) * 0.4F);
            if (random.nextInt(5) == 0) {
                world.addParticle(new DustParticleEffect(rgb.getX(), rgb.getY(), rgb.getZ(), 1.0F), d + (double)direction.getOffsetX() * g, e + (double)direction.getOffsetY() * g, f + (double)direction.getOffsetZ() * g, random.nextGaussian() * 0.005D, random.nextGaussian() * 0.005D, random.nextGaussian() * 0.005D);
            } 
        }
    }
//#region Redstone Power
    @Environment(EnvType.CLIENT)
    public static int getWireColor(BlockState state) {
        Vector3f vector3f = rgbPowerLevel[state.get(POWER)];
        return MathHelper.packRgb(vector3f.getX(), vector3f.getY(), vector3f.getZ());
    }

    @Override
    public boolean emitsRedstonePower(BlockState state) {
        return false;
    }

    private PowerAndDirection getReceivedRedstonePower(World world, BlockPos pos, BlockState state) {
        Direction facing = state.get(FACING);
        Direction opposite = facing.getOpposite();
        int fromFace = (facing != state.get(POWER_TO) || state.get(POWER) == 0) ? world.getEmittedRedstonePower(pos.offset(facing), facing) : 0;
        if (fromFace != 0) { return new PowerAndDirection(fromFace, opposite); }
        int fromRear = (opposite != state.get(POWER_TO) || state.get(POWER) == 0) ? world.getEmittedRedstonePower(pos.offset(opposite), opposite) : 0;
        if (fromRear != 0) { return new PowerAndDirection(fromRear, facing); }
        return new PowerAndDirection(0, Direction.UP);
    }

    private void update(World world, BlockPos pos, BlockState state) {
        PowerAndDirection i = this.getReceivedRedstonePower(world, pos, state);
        int curPower = state.get(POWER);
        if (curPower != i.power) {
            if (world.getBlockState(pos) == state) { world.setBlockState(pos, state.with(POWER, i.power).with(POWER_TO, i.to), 2); }

            world.updateNeighborsAlways(pos, this);
            world.updateNeighborsAlways(pos.offset(state.get(FACING)), this);
            world.updateNeighborsAlways(pos.offset(state.get(FACING).getOpposite()), this);
        }
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (!world.isClient) { this.update(world, pos, state); }
        super.neighborUpdate(state, world, pos, block, fromPos, notify);
    }

    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!oldState.isOf(state.getBlock()) && !world.isClient) {
            this.update(world, pos, state);
            Iterator<Direction> vertDirections = Direction.Type.VERTICAL.iterator();
            while(vertDirections.hasNext()) {
                Direction direction = (Direction)vertDirections.next();
                world.updateNeighborsAlways(pos.offset(direction), this);
            }
        }
    }

    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!moved && !state.isOf(newState.getBlock())) {
            super.onStateReplaced(state, world, pos, newState, moved);
            if (!world.isClient) {
                Direction[] allDirections = Direction.values();
                int adLength = allDirections.length;

                for(int i = 0; i < adLength; ++i) {
                    Direction direction = allDirections[i];
                    world.updateNeighborsAlways(pos.offset(direction), this);
                }
                this.update(world, pos, state);
            }
        }
    }

    public int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {        
        return getWeakRedstonePower(state, world, pos, direction);
    }

    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        if (direction.getOpposite() == state.get(POWER_TO)) {
            int power = Math.max(0, state.get(POWER) - 1); //Since I can't work like dust does (powering the block it's on to set the power level), I have to step down here'
            return power != 0 && world.getBlockState(pos.offset(direction.getOpposite())).isSolidBlock(world, pos.offset(direction.getOpposite())) ? 15 : power;
        } else {
            return 0;
        }
    }
//#endregion
//#region Waterlogging
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        if (state.get(WATERLOGGED)) { world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world)); }
        return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }
//#endregion
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWER_TO, POWER, WATERLOGGED);
    }

    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.NORMAL;
    }

    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }
    
    static {
        rgbPowerLevel = new Vector3f[16];
        for(int i = 0; i <= 15; ++i) {
            float f = (float)i / 15.0F;
            float g = f * 0.6F + (f > 0.0F ? 0.4F : 0.3F);
            float h = MathHelper.clamp(f * f * 0.7F - 0.5F, 0.0F, 1.0F);
            float j = MathHelper.clamp(f * f * 0.6F - 0.7F, 0.0F, 1.0F);
            rgbPowerLevel[i] = new Vector3f(g, h, j);
        }
    }

    class PowerAndDirection {
        public PowerAndDirection(int power, Direction to) {
            this.power = power;
            this.to = to;
        }
        public int power;
        public Direction to;
    }
}
