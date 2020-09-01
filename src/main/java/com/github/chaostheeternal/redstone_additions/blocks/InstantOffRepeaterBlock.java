package com.github.chaostheeternal.redstone_additions.blocks;

import java.util.Random;

import com.github.chaostheeternal.redstone_additions.RedstoneAdditionsMod;
import com.github.chaostheeternal.redstone_additions.interfaces.IRedstoneWireMixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractRedstoneGateBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.TickPriority;
import net.minecraft.world.World;

public class InstantOffRepeaterBlock extends AbstractRedstoneGateBlock implements IRedstoneWireMixin {
    public static final IntProperty DELAY = Properties.DELAY;
    public static final String ID = "instant_off_repeater";
    public static final Block BLOCK = new InstantOffRepeaterBlock(FabricBlockSettings.of(Material.SUPPORTED).breakInstantly().sounds(BlockSoundGroup.WOOD));

    public InstantOffRepeaterBlock(Settings settings) {
        super(settings);
        this.setDefaultState(getStateManager().getDefaultState().with(FACING, Direction.NORTH).with(POWERED, false).with(DELAY, 1));
    }
    public static void RegisterBlock() {
        Registry.register(Registry.BLOCK, new Identifier(RedstoneAdditionsMod.MOD_ID, ID), BLOCK);
        Registry.register(Registry.ITEM, new Identifier(RedstoneAdditionsMod.MOD_ID, ID), new BlockItem(BLOCK, new Item.Settings().group(ItemGroup.REDSTONE)));
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
    protected int getUpdateDelayInternal(BlockState state) {
        return state.get(DELAY) * 2;
    }
    
    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if ((Boolean) state.get(POWERED)) {
            Direction direction = (Direction) state.get(FACING);
            double d = (double) pos.getX() + 0.5D + (random.nextDouble() - 0.5D) * 0.2D;
            double e = (double) pos.getY() + 0.4D + (random.nextDouble() - 0.5D) * 0.2D;
            double f = (double) pos.getZ() + 0.5D + (random.nextDouble() - 0.5D) * 0.2D;
            float g = -5.0F;
            if (random.nextBoolean()) {
                g = (float) ((Integer) state.get(DELAY) * 2 - 1);
            }

            g /= 16.0F;
            double h = (double) (g * (float) direction.getOffsetX());
            double i = (double) (g * (float) direction.getOffsetZ());
            world.addParticle(DustParticleEffect.RED, d + h, e, f + i, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    protected void updatePowered(World world, BlockPos pos, BlockState state) {
        if (!this.isLocked(world, pos, state)) {
            boolean isPowered = (Boolean) state.get(POWERED);
            boolean sbPowered = this.hasPower(world, pos, state);
            if (isPowered != sbPowered && !world.getBlockTickScheduler().isTicking(pos, this)) {
                TickPriority tickPriority = TickPriority.HIGH;
                if (this.isTargetNotAligned(world, pos, state)) {
                    tickPriority = TickPriority.EXTREMELY_HIGH;
                } else if (isPowered) {
                    tickPriority = TickPriority.VERY_HIGH;
                }
                if (sbPowered) world.getBlockTickScheduler().schedule(pos, this, this.getUpdateDelayInternal(state), tickPriority);
                if (isPowered) world.getBlockTickScheduler().schedule(pos, this, 2, tickPriority); //OVERRIDE
            }
        }
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, DELAY, POWERED);
    }    

    public boolean canRedstoneConnect(BlockState state, Direction side) {
        if (side == null) return false; //This seems to fire if redstone is placed on a neighboring block on a different Y
        Direction myFacing = state.get(FACING);
        return (myFacing == side || myFacing == side.getOpposite()); //Input is the way this block is facing, output is opposite the way this block is facing
    }

	public static int getWireColor(BlockState state) {
        if (state.get(POWERED)) {
            return -16777216 | 255 << 16 | 51 << 8 | 0;
        } else {
            return -16777216 | 77 << 16 | 0 << 8 | 0;
        }
	}
}