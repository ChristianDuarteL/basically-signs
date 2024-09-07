package com.basicallymods.signs.common.registry;

import com.basicallymods.signs.BasicallySigns;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, BasicallySigns.MOD_ID);

    public static final RegistryObject<Item> workbench = REGISTRY.register("workbench", () -> new BlockItem(ModBlocks.SIGN_WORKBENCH.get(), new Item.Properties().stacksTo(64)));
}
