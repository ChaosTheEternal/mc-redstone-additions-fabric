package com.github.chaostheeternal.redstone_additions.blockEntities;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;

public class GlazeContainerBlockEntityRenderer extends BlockEntityRenderer<GlazeContainerBlockEntity> {
    public GlazeContainerBlockEntityRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(GlazeContainerBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        BlockState state;
        if (!entity.isEmpty()) {
            state = entity.getEmulatedBlockState();
            MinecraftClient.getInstance().getBlockRenderManager().renderBlockAsEntity(state, matrices, vertexConsumers, light, overlay);
        } else {
            state = Blocks.HONEY_BLOCK.getDefaultState(); //TODO: render the empty "honey block" appearance, meaning I won't be using a predestined block, or need to fake a block that I don't register for that... which might work
            MinecraftClient.getInstance().getBlockRenderManager().renderBlockAsEntity(state, matrices, vertexConsumers, light, overlay);
        }
        matrices.pop();
    }
    
}