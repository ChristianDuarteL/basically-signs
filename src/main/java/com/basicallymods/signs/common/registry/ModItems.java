package com.basicallymods.signs.common.registry;

import com.basicallymods.signs.BasicallySigns;
import com.basicallymods.signs.common.data.ISignColor;
import com.basicallymods.signs.common.data.SignColor;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.basicallymods.signs.common.registry.ModBlocks.SIGN_BLOCKS_BY_COLOR;

public class ModItems {
    public static DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, BasicallySigns.MOD_ID);

    public static final Map<ISignColor, RegistryObject<? extends Item>> SIGN_ITEMS_BY_SIGN_COLOR = new HashMap<>();

    public static final RegistryObject<Item> workbench = REGISTRY.register("workbench", () -> new BlockItem(ModBlocks.SIGN_WORKBENCH.get(), new Item.Properties().stacksTo(64)));
}
