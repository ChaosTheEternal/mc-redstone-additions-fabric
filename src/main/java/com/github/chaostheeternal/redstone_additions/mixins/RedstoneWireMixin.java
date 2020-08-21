package com.github.chaostheeternal.redstone_additions.mixins;

import com.github.chaostheeternal.redstone_additions.blocks.InstantOffRepeaterBlock;
import com.github.chaostheeternal.redstone_additions.blocks.RedstoneInverterBlock;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.util.math.Direction;

@Mixin(RedstoneWireBlock.class)
public class RedstoneWireMixin {
    @Inject(at = @At(value = "HEAD"), method = "connectsTo(Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/Direction;)Z", cancellable = true)
    private static void connectsToMixin(BlockState state, Direction dir, CallbackInfoReturnable<Boolean> cir) {
		if(state.getBlock() instanceof RedstoneInverterBlock) {
			cir.setReturnValue(((RedstoneInverterBlock) state.getBlock()).canRedstoneConnect(state, dir));
        } else if (state.getBlock() instanceof InstantOffRepeaterBlock) {
            cir.setReturnValue(((InstantOffRepeaterBlock) state.getBlock()).canRedstoneConnect(state, dir));
        }
        //TODO: Other overrides as needed if implementing more blocks that only accept input on facing
    }
}