package com.github.chaostheeternal.redstone_additions.blockEntities;

import com.github.chaostheeternal.redstone_additions.blocks.GlazeContainerBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.collection.DefaultedList;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GlazeContainerBlockEntity extends BlockEntity implements Inventory {
    public static Logger LOGGER = LogManager.getLogger();
    private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
//#region Inventory Implementation
    @Override
    public void clear() {
        inventory.clear();
    }

    @Override
    public int size() {
        return inventory.size();
    }

    @Override
    public boolean isEmpty() {
        return inventory.get(0).isEmpty();
    }

    @Override
    public ItemStack getStack(int slot) {
        return inventory.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return inventory.remove(slot); //Since this inventory will only ever take a single item, removeStack will just remove it entirely
    }

    @Override
    public ItemStack removeStack(int slot) {
        return inventory.remove(slot); //Since this inventory will only ever take a single item, removeStack will just remove it entirely
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        inventory.set(slot, stack);
    }

    @Override
    public void markDirty() {
        LOGGER.info("markDirty"); //TODO: Anything I *need* to do here?
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true; //Only intended to use this as a second gate to avoid inserting when this is already filled, but whatever
    }
//#endregion
//#region Block Emulation (Caching)
    private int logCycle = 0;
    private Block _emulatedBlock = Blocks.AIR;
    private BlockState _emulatedState = Blocks.AIR.getDefaultState();
    public void addBlockToContainer(ItemStack stack, Block block) {
        inventory.set(0, stack.split(1)); //I know this is going in correctly... since breaking the block knows what items are inside to eject...
        LOGGER.info("setEmulatedBlock::{}", block.toString());
        _emulatedBlock = block;
        _emulatedState = block.getDefaultState();
    }
    public void loadEmulatedBlock() {
        ItemStack stack = inventory.get(0);
        Block block = Block.getBlockFromItem(stack.getItem());
        LOGGER.info("setEmulatedBlock::{}", block.toString());
        _emulatedBlock = block;
        _emulatedState = _emulatedBlock.getDefaultState();
    }
    public Block getEmulatedBlock() {
        return _emulatedBlock;
    }
    public BlockState getEmulatedBlockState() {
        if (logCycle % 80 == 0) {
            LOGGER.info("getEmulatedBlock::{}", inventory.toString());
            logCycle = 0;
        } logCycle++;
        return _emulatedState;
    }
//#endregion
//#region Block Emulation (Fetching)
    // private int logCycle = 0;
    // public void addBlockToContainer(ItemStack stack, Block block) {
    //     inventory.set(0, stack.split(1)); //I know this is going in correctly... since breaking the block knows what items are inside to eject...
    // }
    // public Block getEmulatedBlock() { 
    //     return Block.getBlockFromItem(inventory.get(0).getItem());
    // }
    // public BlockState getEmulatedBlockState() { 
    //     //...but why does this give me AIR?  It's almost like the renderer gets a different block entity
    //     BlockState state = Block.getBlockFromItem(inventory.get(0).getItem()).getDefaultState();
    //     if (logCycle % 80 == 0) {
    //         LOGGER.info("getEmulatedBlock::{}::{}", getPos().toString(), inventory.toString());
    //         logCycle = 0;
    //     } logCycle++;
    //     return state;
    // }
//#endregion

    public static BlockEntityType<GlazeContainerBlockEntity> ENTITY = BlockEntityType.Builder.create(GlazeContainerBlockEntity::new, GlazeContainerBlock.BLOCK).build(null);
    public GlazeContainerBlockEntity() {
        super(ENTITY);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        Inventories.toTag(tag, inventory);
        return super.toTag(tag);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        Inventories.fromTag(tag, inventory);
    }
}