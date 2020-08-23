package com.github.chaostheeternal.redstone_additions;

import com.github.chaostheeternal.redstone_additions.blockEntities.GlazeContainerBlockEntity;
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
        //Would like to genericize this if possible, seems like it would involve reflection hackery that is above me right now
        Registry.register(Registry.BLOCK, new Identifier(MOD_ID, RedstoneInverterBlock.ID), RedstoneInverterBlock.BLOCK);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, RedstoneInverterBlock.ID), RedstoneInverterBlock.getBlockItem());

        Registry.register(Registry.BLOCK, new Identifier(MOD_ID, InstantOffRepeaterBlock.ID), InstantOffRepeaterBlock.BLOCK);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, InstantOffRepeaterBlock.ID), InstantOffRepeaterBlock.getBlockItem());

        Registry.register(Registry.BLOCK, new Identifier(MOD_ID, SignalExtendedObserverBlock.ID), SignalExtendedObserverBlock.BLOCK);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, SignalExtendedObserverBlock.ID), SignalExtendedObserverBlock.getBlockItem());

        Registry.register(Registry.BLOCK, new Identifier(MOD_ID, GlazeContainerBlock.ID), GlazeContainerBlock.BLOCK);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, GlazeContainerBlock.ID), GlazeContainerBlock.getBlockItem());
        
        GlazeContainerBlockEntity.ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, GlazeContainerBlock.ID), GlazeContainerBlockEntity.ENTITY);
    }
}