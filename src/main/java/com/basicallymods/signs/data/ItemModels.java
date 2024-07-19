package com.basicallymods.signs.data;

import com.basicallymods.signs.api.DataGenerators;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import static com.basicallymods.signs.BasicallySigns.MOD_ID;
import static com.basicallymods.signs.BasicallySigns.registerer;
import static com.basicallymods.signs.common.registry.ModItems.SIGN_ITEMS_BY_SIGN_COLOR;

public class ItemModels extends ItemModelProvider {

    public ItemModels(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        DataGenerators.registerItemModels(this, registerer);
    }
}
