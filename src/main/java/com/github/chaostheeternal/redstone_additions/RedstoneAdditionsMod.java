package com.github.chaostheeternal.redstone_additions;

import com.github.chaostheeternal.redstone_additions.blocks.*;

import net.fabricmc.api.ModInitializer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RedstoneAdditionsMod implements ModInitializer {
    public static Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "redstone_additions";
    public static final String MOD_NAME = "Redstone Additions";

    @Override
    public void onInitialize() {
        RedstoneInverterBlock.RegisterBlock();
        InstantOffRepeaterBlock.RegisterBlock();
        SignalExtendedObserverBlock.RegisterBlock();
        GlossyBlock.RegisterBlocks();
    }
}