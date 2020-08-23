package com.github.chaostheeternal.redstone_additions.blocks;

import com.github.chaostheeternal.redstone_additions.blockEntities.GlazeContainerBlockEntity;

import org.jetbrains.annotations.Nullable;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GlazeContainerBlock extends BlockWithEntity {
    public static Logger LOGGER = LogManager.getLogger();
    public static final DirectionProperty FACING = Properties.FACING;
    public static final BooleanProperty FILLED = BooleanProperty.of("filled");
    public static final String ID = "glaze_container";
    public static final Material GLAZE = new Material.Builder(MaterialColor.GRAY).build();
    public static final GlazeContainerBlock BLOCK = new GlazeContainerBlock(FabricBlockSettings.of(GLAZE).hardness(0F).sounds(BlockSoundGroup.SLIME));
    public GlazeContainerBlock(Settings settings) {
        super(settings);
        this.setDefaultState(getStateManager().getDefaultState().with(FACING, Direction.NORTH).with(FILLED, false));
    }

    public static BlockItem getBlockItem() {
        return new BlockItem(BLOCK, new Item.Settings().group(ItemGroup.REDSTONE));
    }

    @Override @Nullable
    public BlockEntity createBlockEntity(BlockView world) {
        return new GlazeContainerBlockEntity();
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        if (state.get(FILLED)) {
            return BlockRenderType.INVISIBLE;
        } else {
            return BlockRenderType.MODEL;
        }
    }

    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.PUSH_ONLY;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) return ActionResult.SUCCESS;
        if (state.get(FILLED)) return ActionResult.PASS;
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity != null && blockEntity instanceof GlazeContainerBlockEntity && ((Inventory)blockEntity).canPlayerUse(player)) {
            ItemStack stack = player.getStackInHand(hand);
            Item item = stack.getItem();
            Block block = Block.getBlockFromItem(item);
            if (!stack.isEmpty() && item instanceof BlockItem && !(block instanceof BlockWithEntity) && block.getDefaultState().isOpaque()) {
                ((GlazeContainerBlockEntity)blockEntity).addBlockToContainer(stack, block);
                BlockState update = state.with(FILLED, true);
                world.setBlockState(pos, update, 11);
                world.markDirty(pos, blockEntity);
            } else {
            }
        }
        return ActionResult.SUCCESS;
    }
//#region Block Emulation
    //Will I actually be able to emulate hardness/blast resistance/tool usage for this one?
    //Is that going to be a new mixin?
//#endregion
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof Inventory) { ItemScatterer.spawn(world, pos, (Inventory) blockEntity); }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, FILLED);
    }
}