package com.github.chaostheeternal.redstone_additions.blockEntities;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;

public class GlazeContainerBlockEntityRenderer extends BlockEntityRenderer<GlazeContainerBlockEntity> {
    public GlazeContainerBlockEntityRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(GlazeContainerBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (!entity.isEmpty()) { //Why is this failing to get the right entity?
            matrices.push();
            BlockState state;
            state = entity.getEmulatedBlockState();
            int lightAbove = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getPos().up());
            MinecraftClient.getInstance().getBlockRenderManager().renderBlockAsEntity(state, matrices, vertexConsumers, lightAbove, overlay);
            matrices.pop();
        } else {
            //Don't need to do anything, as the Block getRenderType will be Model
        }
    }    
}