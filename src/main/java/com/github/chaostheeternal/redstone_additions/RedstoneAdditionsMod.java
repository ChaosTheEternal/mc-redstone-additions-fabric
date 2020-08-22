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
        // Any way to automate this, only passing in the class name?
        Registry.register(Registry.BLOCK, new Identifier(MOD_ID, RedstoneInverterBlock.ID), RedstoneInverterBlock.BLOCK);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, RedstoneInverterBlock.ID), RedstoneInverterBlock.getBlockItem());

        Registry.register(Registry.BLOCK, new Identifier(MOD_ID, InstantOffRepeaterBlock.ID), InstantOffRepeaterBlock.BLOCK);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, InstantOffRepeaterBlock.ID), InstantOffRepeaterBlock.getBlockItem());

        Registry.register(Registry.BLOCK, new Identifier(MOD_ID, SignalExtendedObserverBlock.ID), SignalExtendedObserverBlock.BLOCK);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, SignalExtendedObserverBlock.ID), SignalExtendedObserverBlock.getBlockItem());
    }

    public static void log(Level level, String message){
        LOGGER.log(level, "["+MOD_NAME+"] " + message);
    }
}