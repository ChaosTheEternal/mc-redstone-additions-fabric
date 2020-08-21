package com.github.chaostheeternal.redstone_additions;

import com.github.chaostheeternal.redstone_additions.blocks.*;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RedstoneAdditionsMod implements ModInitializer {
    public static Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "redstone_additions";
    public static final String MOD_NAME = "Redstone Additions";

    @Override
    public void onInitialize() {
        log(Level.INFO, "Initializing");
        Registry.register(Registry.BLOCK, new Identifier(MOD_ID, RedstoneInverterBlock.ID), RedstoneInverterBlock.BLOCK);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, RedstoneInverterBlock.ID), RedstoneInverterBlock.getBlockItem());
    }

    public static void log(Level level, String message){
        LOGGER.log(level, "["+MOD_NAME+"] " + message);
    }
}