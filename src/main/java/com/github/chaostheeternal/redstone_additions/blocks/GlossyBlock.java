package com.github.chaostheeternal.redstone_additions.blocks;

import com.github.chaostheeternal.redstone_additions.RedstoneAdditionsMod;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class GlossyBlock extends Block {
    public GlossyBlock(Settings settings) {
        super(settings);
    }

    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.PUSH_ONLY;
    }

    public static void RegisterBlocks() {
        RegisterBlock("glossy_stone", new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, MaterialColor.STONE).requiresTool().strength(1.5F, 6.0F)));        
        RegisterBlock("glossy_granite", new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, MaterialColor.DIRT).requiresTool().strength(1.5F, 6.0F)));
        RegisterBlock("glossy_polished_granite", new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, MaterialColor.DIRT).requiresTool().strength(1.5F, 6.0F)));
        RegisterBlock("glossy_diorite", new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, MaterialColor.QUARTZ).requiresTool().strength(1.5F, 6.0F)));
        RegisterBlock("glossy_polished_diorite", new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, MaterialColor.QUARTZ).requiresTool().strength(1.5F, 6.0F)));
        RegisterBlock("glossy_andesite", new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, MaterialColor.STONE).requiresTool().strength(1.5F, 6.0F)));
        RegisterBlock("glossy_polished_andesite", new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, MaterialColor.STONE).requiresTool().strength(1.5F, 6.0F)));
        //Future:
        // Lapis Lazuli Block, Block of Iron, Block of Gold, Block of Diamond, Block of Netherite, Block of Emerald
        // Chiseled Sandstone, Cut Sandstone, Sandstone (I guess), Smooth Sandstone and Red variants
        // Block of Quartz, Quartz Pillar, Smooth Quartz Block, Chiseled Quartz Block, Quartz Bricks
        // All Concretes (fun)
        // Smooth Stone, Bricks, Stone Bricks, Chiseled Stone Bricks
        // Purpur Block, Purpur Pillar, End Stone Bricks, Glowstone (incl light), Shroomlight (incl light)
        // Basalt, Polished Basalt, Polished Blackstone, Chiseled Polished Blackstone, Polished Blackstone Bricks
        // Nether Bricks, Chiseled Nether Bricks, Red Nether Bricks
        // Dark Prismarine, Prismarine Bricks, Sea Lantern (incl light)
        // Honey Comb Block
    }
    private static void RegisterBlock(String id, Block block) {
        Registry.register(Registry.BLOCK, new Identifier(RedstoneAdditionsMod.MOD_ID, id), block);
        Registry.register(Registry.ITEM, new Identifier(RedstoneAdditionsMod.MOD_ID, id), new BlockItem(block, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));        
    }
}
