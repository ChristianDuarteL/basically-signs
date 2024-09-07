package com.basicallymods.signs.data;

import com.basicallymods.signs.api.DataGenerators;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import static com.basicallymods.signs.BasicallySigns.MOD_ID;
import static com.basicallymods.signs.BasicallySigns.registerer;

public class ItemModels extends ItemModelProvider {

    public ItemModels(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        DataGenerators.registerItemModels(this, registerer);
    }
}
