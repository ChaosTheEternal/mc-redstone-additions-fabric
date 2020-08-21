package com.github.chaostheeternal.redstone_additions;

import com.github.chaostheeternal.redstone_additions.blocks.*;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class RedstoneAdditionsModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(RedstoneInverterBlock.BLOCK, RenderLayer.getCutout());
    }
}