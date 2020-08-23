package com.github.chaostheeternal.redstone_additions;

import com.github.chaostheeternal.redstone_additions.blockEntities.GlazeContainerBlockEntity;
import com.github.chaostheeternal.redstone_additions.blockEntities.GlazeContainerBlockEntityRenderer;
import com.github.chaostheeternal.redstone_additions.blocks.*;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.render.RenderLayer;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RedstoneAdditionsModClient implements ClientModInitializer {
    public static Logger LOGGER = LogManager.getLogger();

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(RedstoneInverterBlock.BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(InstantOffRepeaterBlock.BLOCK, RenderLayer.getCutout());
        ColorProviderRegistry.BLOCK.register((state, pos, world, layer) -> InstantOffRepeaterBlock.getWireColor(state), InstantOffRepeaterBlock.BLOCK);
        BlockRenderLayerMap.INSTANCE.putBlock(GlazeContainerBlock.BLOCK, RenderLayer.getTranslucent()); //Don't know if this'll be needed if I'm doing manual rendering anyways
        BlockEntityRendererRegistry.INSTANCE.register(GlazeContainerBlockEntity.ENTITY, GlazeContainerBlockEntityRenderer::new);
    }
}