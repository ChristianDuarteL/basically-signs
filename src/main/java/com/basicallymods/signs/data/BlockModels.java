package com.basicallymods.signs.data;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import static com.basicallymods.signs.BasicallySigns.MOD_ID;
import static com.basicallymods.signs.common.registry.ModBlocks.SIGN_BLOCKS_BY_COLOR;

public class BlockModels extends BlockModelProvider {
    public BlockModels(PackOutput generator, ExistingFileHelper existingFileHelper) {
        super(generator, MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        final ResourceLocation planks_particles = new ResourceLocation(
                "minecraft", "block/spruce_planks"
        );
        SIGN_BLOCKS_BY_COLOR.values().forEach(
                block -> {
                    sign(blockName(block.standing().get()), planks_particles);
                    sign(blockName(block.wall().get()), planks_particles);
                }
        );
    }

    private String blockName(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block).getPath();
    }
}
