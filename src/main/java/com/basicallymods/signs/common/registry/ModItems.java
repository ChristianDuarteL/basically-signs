package com.basicallymods.signs.common.registry;

import com.basicallymods.signs.BasicallySigns;
import com.basicallymods.signs.common.data.SignColor;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.basicallymods.signs.common.registry.ModBlocks.SIGN_BLOCKS_BY_COLOR;

public class ModItems {
    public static DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, BasicallySigns.MOD_ID);

    public static final Map<SignColor, RegistryObject<Item>> SIGN_ITEMS_BY_SIGN_COLOR =
            Arrays.stream(SignColor.values()).collect(
                    Collectors.toMap(
                            Function.identity(),
                            e -> REGISTRY.register(
                                    e.getName() + "_sign",
                                    () -> new SignItem(
                                            new Item.Properties().stacksTo(64),
                                            SIGN_BLOCKS_BY_COLOR.get(e).standing().get(),
                                            SIGN_BLOCKS_BY_COLOR.get(e).wall().get()
                                    )

                            )
                    )
            );

    public static final RegistryObject<Item> workbench = REGISTRY.register("workbench", () -> new BlockItem(ModBlocks.SIGN_WORKBENCH.get(), new Item.Properties().stacksTo(64)));
}
