package com.github.chaostheeternal.redstone_additions.interfaces;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.Direction;

public interface IRedstoneWireMixin {
    public boolean canRedstoneConnect(BlockState state, Direction dir);
}