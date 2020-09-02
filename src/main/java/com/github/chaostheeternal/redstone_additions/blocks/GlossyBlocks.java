package com.github.chaostheeternal.redstone_additions.blocks;

import com.github.chaostheeternal.redstone_additions.RedstoneAdditionsMod;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class GlossyBlocks {
    public static void RegisterBlocks() {
        //TODO: Need to revisit to assign item groups correctly
        RegisterBlock("glossy_stone", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, MaterialColor.STONE).requiresTool().strength(1.5F, 6.0F)));        
        RegisterBlock("glossy_smooth_stone", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, MaterialColor.STONE).requiresTool().strength(2.0F, 6.0F)));
        RegisterBlock("glossy_granite", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, MaterialColor.DIRT).requiresTool().strength(1.5F, 6.0F)));
        RegisterBlock("glossy_polished_granite", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, MaterialColor.DIRT).requiresTool().strength(1.5F, 6.0F)));
        RegisterBlock("glossy_diorite", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, MaterialColor.QUARTZ).requiresTool().strength(1.5F, 6.0F)));
        RegisterBlock("glossy_polished_diorite", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, MaterialColor.QUARTZ).requiresTool().strength(1.5F, 6.0F)));
        RegisterBlock("glossy_andesite", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, MaterialColor.STONE).requiresTool().strength(1.5F, 6.0F)));
        RegisterBlock("glossy_polished_andesite", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, MaterialColor.STONE).requiresTool().strength(1.5F, 6.0F)));
        RegisterBlock("glossy_bricks", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, MaterialColor.RED).requiresTool().strength(2.0F, 6.0F)));
        RegisterBlock("glossy_stone_bricks", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.STONE).requiresTool().strength(1.5F, 6.0F)));
        RegisterBlock("glossy_chiseled_stone_bricks", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.STONE).requiresTool().strength(1.5F, 6.0F)));
        RegisterBlock("glossy_lapis_block", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.METAL, MaterialColor.LAPIS).requiresTool().strength(3.0F, 3.0F)));
        RegisterBlock("glossy_iron_block", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.METAL, MaterialColor.IRON).requiresTool().strength(5.0F, 6.0F).sounds(BlockSoundGroup.METAL)));
        RegisterBlock("glossy_gold_block", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.METAL, MaterialColor.GOLD).requiresTool().strength(3.0F, 6.0F).sounds(BlockSoundGroup.METAL)));
        RegisterBlock("glossy_diamond_block", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.METAL, MaterialColor.DIAMOND).requiresTool().strength(5.0F, 6.0F).sounds(BlockSoundGroup.METAL)));
        RegisterBlock("glossy_netherite_block", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.METAL, MaterialColor.BLACK).requiresTool().strength(50.0F, 1200.0F).sounds(BlockSoundGroup.NETHERITE)));
        RegisterBlock("glossy_emerald_block", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.METAL, MaterialColor.EMERALD).requiresTool().strength(5.0F, 6.0F).sounds(BlockSoundGroup.METAL)));
        RegisterBlock("glossy_sandstone", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, MaterialColor.SAND).requiresTool().strength(0.8F)));
        RegisterBlock("glossy_chiseled_sandstone", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, MaterialColor.SAND).requiresTool().strength(0.8F)));
        RegisterBlock("glossy_cut_sandstone", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, MaterialColor.SAND).requiresTool().strength(0.8F)));
        RegisterBlock("glossy_smooth_sandstone", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, MaterialColor.SAND).requiresTool().strength(2.0F, 6.0F)));
        RegisterBlock("glossy_red_sandstone", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, MaterialColor.ORANGE).requiresTool().strength(0.8F)));
        RegisterBlock("glossy_chiseled_red_sandstone", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, MaterialColor.ORANGE).requiresTool().strength(0.8F)));
        RegisterBlock("glossy_cut_red_sandstone", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, MaterialColor.ORANGE).requiresTool().strength(0.8F)));
        RegisterBlock("glossy_smooth_red_sandstone", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, MaterialColor.ORANGE).requiresTool().strength(2.0F, 6.0F)));
        RegisterBlock("glossy_white_concrete", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, DyeColor.WHITE).requiresTool().strength(1.8F)));
        RegisterBlock("glossy_orange_concrete", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, DyeColor.ORANGE).requiresTool().strength(1.8F)));
        RegisterBlock("glossy_magenta_concrete", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, DyeColor.MAGENTA).requiresTool().strength(1.8F)));
        RegisterBlock("glossy_light_blue_concrete", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, DyeColor.LIGHT_BLUE).requiresTool().strength(1.8F)));
        RegisterBlock("glossy_yellow_concrete", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, DyeColor.YELLOW).requiresTool().strength(1.8F)));
        RegisterBlock("glossy_lime_concrete", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, DyeColor.LIME).requiresTool().strength(1.8F)));
        RegisterBlock("glossy_pink_concrete", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, DyeColor.PINK).requiresTool().strength(1.8F)));
        RegisterBlock("glossy_gray_concrete", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, DyeColor.GRAY).requiresTool().strength(1.8F)));
        RegisterBlock("glossy_light_gray_concrete", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, DyeColor.LIGHT_GRAY).requiresTool().strength(1.8F)));
        RegisterBlock("glossy_cyan_concrete", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, DyeColor.CYAN).requiresTool().strength(1.8F)));
        RegisterBlock("glossy_purple_concrete", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, DyeColor.PURPLE).requiresTool().strength(1.8F)));
        RegisterBlock("glossy_blue_concrete", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, DyeColor.BLUE).requiresTool().strength(1.8F)));
        RegisterBlock("glossy_brown_concrete", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, DyeColor.BROWN).requiresTool().strength(1.8F)));
        RegisterBlock("glossy_green_concrete", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, DyeColor.GREEN).requiresTool().strength(1.8F)));
        RegisterBlock("glossy_red_concrete", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, DyeColor.RED).requiresTool().strength(1.8F)));
        RegisterBlock("glossy_black_concrete", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, DyeColor.BLACK).requiresTool().strength(1.8F)));
        RegisterBlock("glossy_quartz_block", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, MaterialColor.QUARTZ).requiresTool().strength(0.8F)));
        RegisterBlock("glossy_chiseled_quartz_block", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, MaterialColor.QUARTZ).requiresTool().strength(0.8F)));
        RegisterBlock("glossy_quartz_pillar", ItemGroup.BUILDING_BLOCKS, new GlossyPillarBlock(AbstractBlock.Settings.of(Material.STONE, MaterialColor.QUARTZ).requiresTool().strength(0.8F)));
        RegisterBlock("glossy_smooth_quartz", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, MaterialColor.QUARTZ).requiresTool().strength(2.0F, 6.0F)));
        RegisterBlock("glossy_quartz_bricks", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, MaterialColor.QUARTZ).requiresTool().strength(0.8F))); //.copy(QUARTZ_BLOCK)
        RegisterBlock("glossy_nether_bricks", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, MaterialColor.NETHER).requiresTool().strength(2.0F, 6.0F).sounds(BlockSoundGroup.NETHER_BRICKS)));
        RegisterBlock("glossy_chiseled_nether_bricks", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, MaterialColor.NETHER).requiresTool().strength(2.0F, 6.0F).sounds(BlockSoundGroup.NETHER_BRICKS)));
        RegisterBlock("glossy_red_nether_bricks", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, MaterialColor.NETHER).requiresTool().strength(2.0F, 6.0F).sounds(BlockSoundGroup.NETHER_BRICKS)));
        RegisterBlock("glossy_glowstone", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.GLASS, MaterialColor.SAND).strength(0.3F).sounds(BlockSoundGroup.GLASS).lightLevel((state) -> { return 15; })));
        RegisterBlock("glossy_shroomlight", ItemGroup.DECORATIONS, new GlossyBlock(AbstractBlock.Settings.of(Material.SOLID_ORGANIC, MaterialColor.RED).strength(1.0F).sounds(BlockSoundGroup.SHROOMLIGHT).lightLevel((state) -> { return 15; })));
        RegisterBlock("glossy_basalt", ItemGroup.BUILDING_BLOCKS, new GlossyPillarBlock(AbstractBlock.Settings.of(Material.STONE, MaterialColor.BLACK).requiresTool().strength(1.25F, 4.2F).sounds(BlockSoundGroup.BASALT)));
        RegisterBlock("glossy_polished_basalt", ItemGroup.BUILDING_BLOCKS, new GlossyPillarBlock(AbstractBlock.Settings.of(Material.STONE, MaterialColor.BLACK).requiresTool().strength(1.25F, 4.2F).sounds(BlockSoundGroup.BASALT)));
        RegisterBlock("glossy_polished_blackstone", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, MaterialColor.BLACK).requiresTool().strength(2.0F, 6.0F))); //.copy(BLACKSTONE)
        RegisterBlock("glossy_polished_blackstone_bricks", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, MaterialColor.BLACK).requiresTool().strength(2.0F, 6.0F))); //.copy(POLISHED_BLACKSTONE)
        RegisterBlock("glossy_chiseled_polished_blackstone", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, MaterialColor.BLACK).requiresTool().strength(2.0F, 6.0F))); //.copy(POLISHED_BLACKSTONE)
        RegisterBlock("glossy_purpur_block", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, MaterialColor.MAGENTA).requiresTool().strength(1.5F, 6.0F)));
        RegisterBlock("glossy_purpur_pillar", ItemGroup.BUILDING_BLOCKS, new GlossyPillarBlock(AbstractBlock.Settings.of(Material.STONE, MaterialColor.MAGENTA).requiresTool().strength(1.5F, 6.0F)));
        RegisterBlock("glossy_end_stone_bricks", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, MaterialColor.SAND).requiresTool().strength(3.0F, 9.0F)));
        RegisterBlock("glossy_sea_lantern", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.GLASS, MaterialColor.QUARTZ).strength(0.3F).sounds(BlockSoundGroup.GLASS).lightLevel((state) -> { return 15; })));
        RegisterBlock("glossy_prismarine_bricks", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, MaterialColor.DIAMOND).requiresTool().strength(1.5F, 6.0F)));
        RegisterBlock("glossy_dark_prismarine", ItemGroup.BUILDING_BLOCKS, new GlossyBlock(AbstractBlock.Settings.of(Material.STONE, MaterialColor.DIAMOND).requiresTool().strength(1.5F, 6.0F)));
        RegisterBlock("glossy_honeycomb_block", ItemGroup.DECORATIONS, new GlossyBlock(AbstractBlock.Settings.of(Material.ORGANIC_PRODUCT, MaterialColor.ORANGE).strength(0.6F).sounds(BlockSoundGroup.CORAL)));
    }
    private static void RegisterBlock(String id, ItemGroup itemGroup, Block block) {
        Registry.register(Registry.BLOCK, new Identifier(RedstoneAdditionsMod.MOD_ID, id), block);
        Registry.register(Registry.ITEM, new Identifier(RedstoneAdditionsMod.MOD_ID, id), new BlockItem(block, new Item.Settings().group(itemGroup)));        
    }
}
class GlossyBlock extends Block {
    public GlossyBlock(Settings settings) {
        super(settings);
    }
    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.PUSH_ONLY;
    }
}
class GlossyPillarBlock extends PillarBlock {
    public GlossyPillarBlock(Settings settings) {
        super(settings);
    }    
    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.PUSH_ONLY;
    }
}