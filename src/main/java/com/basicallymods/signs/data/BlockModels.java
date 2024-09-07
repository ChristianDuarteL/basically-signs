package com.basicallymods.signs.data;

import com.basicallymods.signs.BasicallySigns;
import com.basicallymods.signs.api.DataGenerators;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import static com.basicallymods.signs.BasicallySigns.MOD_ID;

public class BlockModels extends BlockModelProvider {
    public BlockModels(PackOutput generator, ExistingFileHelper existingFileHelper) {
        super(generator, MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        DataGenerators.registerBlockModels(this, BasicallySigns.registerer);
    }
}
