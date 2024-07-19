package com.basicallymods.signs.data;

import com.basicallymods.signs.BasicallySigns;
import com.basicallymods.signs.api.DataGenerators;
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
        DataGenerators.registerBlockModels(this, BasicallySigns.registerer);
    }
}
