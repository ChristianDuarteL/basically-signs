package com.basicallymods.signs.data;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import static com.basicallymods.signs.BasicallySigns.MOD_ID;
import static com.basicallymods.signs.common.registry.ModBlocks.*;

public class BlockStates extends BlockStateProvider {
    public BlockStates(PackOutput gen, ExistingFileHelper exFileHelper) {
        super(gen, MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        SIGN_BLOCKS_BY_COLOR.values().forEach(e -> {
            simpleBlock(e.standing().get(), existingModel(e.standing().get()));
            simpleBlock(e.wall().get(), existingModel(e.wall().get()));
        });
        horizontalBlock(
            SIGN_WORKBENCH.get(),
            existingModel(SIGN_WORKBENCH.get()),
            270
        );
    }

    private String blockName(Block block) {
        return REGISTRY.getEntries().stream().filter(e ->
                e.get() == block
        ).findFirst().get().getId().getPath();
    }

    public ResourceLocation resourceBlock(String path) {
        return new ResourceLocation(MOD_ID, "block/" + path);
    }

    public ModelFile existingModel(Block block) {
        return new ModelFile.ExistingModelFile(resourceBlock(blockName(block)), models().existingFileHelper);
    }
}
