package com.github.chaostheeternal.redstone_additions.blocks;

import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import com.github.chaostheeternal.redstone_additions.RedstoneAdditionsMod;
import com.google.common.collect.Sets;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FacingBlock;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
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

public class RedstoneRodBlock extends FacingBlock {
    protected static final VoxelShape Y_SHAPE = Block.createCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);
    protected static final VoxelShape Z_SHAPE = Block.createCuboidShape(6.0D, 6.0D, 0.0D, 10.0D, 10.0D, 16.0D);
    protected static final VoxelShape X_SHAPE = Block.createCuboidShape(0.0D, 6.0D, 6.0D, 16.0D, 10.0D, 10.0D); 
    private static final Vector3f[] rgbPowerLevel;

    /* TODO: Remaining tasks
        1. Need to get "power_from" working, so the blocks can only power in one direction
            * "power_from" will apply when a rod gets powered and should chain
            * This will fix how it works now where it will power a block and then be powered by the block once the source turns off
            * When a rod has 0 power, the power_from should be UP (and I'll have to have logic to ignore that on update)
        2. Need to tweak how rods give power, so they only give in the direction "facing" but not "power_from"
            * This is dependent on #1
        3. Need to get the model to do the tinting correctly (so the rods appear like redstone)
        4. Need to maybe see about adding a property so the rods know if they're attached to a wall
            * That means I need to update it when neighbor on FACING or opposite changes
            * That could be so I can do the caps only when the block is attached, otherwise it just hangs out
            * Otherwise, I drop the caps entirely
        5. Need to implement water logging
            * Probably will look at Chain Block for that
    */

    public static final DirectionProperty POWER_FROM = DirectionProperty.of("power_from", Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.UP, Direction.DOWN);
    public static final IntProperty POWER = Properties.POWER;
    public static final String ID = "redstone_rod";
    public static final Block BLOCK = new RedstoneRodBlock(FabricBlockSettings.of(Material.SUPPORTED).breakInstantly().sounds(BlockSoundGroup.WOOD).nonOpaque());
    protected RedstoneRodBlock(Settings settings) {
        super(settings);
        this.setDefaultState(getStateManager().getDefaultState().with(FACING, Direction.UP).with(POWER_FROM, Direction.UP).with(POWER, 0));
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
 
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction direction = ctx.getSide();
        return this.getDefaultState().with(FACING, direction);
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
        return false; //is this what does the wire connecting?
    }

    private PowerAndDirection getReceivedRedstonePower(World world, BlockPos pos, BlockState state) {
        Direction facing = state.get(FACING);
        int fromFace = (facing != state.get(POWER_FROM) || state.get(POWER) == 0) ? world.getEmittedRedstonePower(pos.offset(facing), facing) : 0;
        if (fromFace != 0) { return new PowerAndDirection(fromFace, facing); }
        Direction opposite = facing.getOpposite();
        int fromRear = (facing.getOpposite() != state.get(POWER_FROM) || state.get(POWER) == 0) ? world.getEmittedRedstonePower(pos.offset(opposite), opposite) : 0;
        if (fromRear != 0) { return new PowerAndDirection(fromRear, opposite); }
        return new PowerAndDirection(0, Direction.UP);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (!world.isClient) { this.update(world, pos, state); }
        super.neighborUpdate(state, world, pos, block, fromPos, notify);
    }

    public int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {        
        if ((direction == state.get(FACING) || direction == state.get(FACING).getOpposite()) && direction != state.get(POWER_FROM)) {
            return state.get(POWER);
        } else {
            return 0;
        }
    }

    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return 0; //this doesn't directly power dust or adjacent components
    }

    private Direction getPowerFromDirection(int i, Direction facing) {
        if (i == 0) {
            return Direction.UP; //Reset
        } else if (i > 0) {
            return facing;
        } else {
            return facing.getOpposite();
        }
    }

    private void update(World world, BlockPos pos, BlockState state) {
        PowerAndDirection i = this.getReceivedRedstonePower(world, pos, state);
        if (state.get(POWER) != i.power) {
            if (world.getBlockState(pos) == state) { world.setBlockState(pos, state.with(POWER, i.power).with(POWER_FROM, i.powerFrom), 2); }

            Set<BlockPos> set = Sets.newHashSet();
            set.add(pos);
            Direction[] var6 = Direction.values();
            int var7 = var6.length;

            for(int var8 = 0; var8 < var7; ++var8) {
                Direction direction = var6[var8];
                set.add(pos.offset(direction));
            }

            Iterator<BlockPos> var10 = set.iterator();

            while(var10.hasNext()) {
                BlockPos blockPos = (BlockPos)var10.next();
                world.updateNeighborsAlways(blockPos, this);
            }
        }
    }

    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!oldState.isOf(state.getBlock()) && !world.isClient) {
            this.update(world, pos, state);
            Iterator<Direction> var6 = Direction.Type.VERTICAL.iterator();
            while(var6.hasNext()) {
                Direction direction = (Direction)var6.next();
                world.updateNeighborsAlways(pos.offset(direction), this);
            }
        }
    }

    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!moved && !state.isOf(newState.getBlock())) {
            super.onStateReplaced(state, world, pos, newState, moved);
            if (!world.isClient) {
                Direction[] var6 = Direction.values();
                int var7 = var6.length;

                for(int var8 = 0; var8 < var7; ++var8) {
                    Direction direction = var6[var8];
                    world.updateNeighborsAlways(pos.offset(direction), this);
                }
                this.update(world, pos, state);
            }
        }
    }
//#endregion
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWER_FROM, POWER);
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
        public PowerAndDirection(int power, Direction from) {
            this.power = power;
            this.powerFrom = from;
        }
        public int power;
        public Direction powerFrom;
    }

}
