package com.github.chaostheeternal.redstone_additions.blockEntities;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GlazeContainerBlockEntityRenderer extends BlockEntityRenderer<GlazeContainerBlockEntity> {
    public static Logger LOGGER = LogManager.getLogger();
    public GlazeContainerBlockEntityRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(GlazeContainerBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (!entity.isEmpty()) { //Why is this failing to get the right entity?  Or why is the entity seemingly in the wrong state?
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