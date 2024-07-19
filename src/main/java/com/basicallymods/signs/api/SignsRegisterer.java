package com.basicallymods.signs.api;

import com.basicallymods.signs.common.block.StandingSignBlock;
import com.basicallymods.signs.common.block.WallSignBlock;
import com.basicallymods.signs.common.data.ISignColor;
import com.basicallymods.signs.common.registry.ModBlocks;
import com.basicallymods.signs.common.registry.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

public class SignsRegisterer {
    public final DeferredRegister<Block> blocksRegistry;
    public final DeferredRegister<Item> itemsRegistry;
    public final Map<ISignColor, ModBlocks.SignBlock> SIGN_BLOCKS_BY_COLOR = new HashMap<>();
    public final Map<ISignColor, RegistryObject<? extends Item>> SIGN_ITEMS_BY_SIGN_COLOR = new HashMap<>();

    public final String MOD_ID;

    public SignsRegisterer(String MOD_ID) {
        this(MOD_ID, DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID), DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID));
    }

    public SignsRegisterer(String MOD_ID, DeferredRegister<Block> blocksRegistry, DeferredRegister<Item> itemsRegistry) {
        this.blocksRegistry = blocksRegistry;
        this.itemsRegistry = itemsRegistry;
        this.MOD_ID = MOD_ID;
    }

    public void registerBasic(ISignColor sign) {
        RegistryObject<StandingSignBlock> standingBlock = blocksRegistry.register(sign.standingSignName(), () -> new StandingSignBlock(sign));
        RegistryObject<WallSignBlock> wallSignBlock = blocksRegistry.register(sign.wallSignName(), () -> new WallSignBlock(sign));
        ModBlocks.SignBlock block = new ModBlocks.SignBlock(standingBlock, wallSignBlock);
        SIGN_BLOCKS_BY_COLOR.put(sign, block);
        ModBlocks.SIGN_BLOCKS_BY_COLOR.put(sign, block);
        RegistryObject<SignItem> item = itemsRegistry.register(
                sign.itemName(),
                () -> new SignItem(
                        new Item.Properties().stacksTo(64),
                        standingBlock.get(),
                        wallSignBlock.get()
                )
        );

        SIGN_ITEMS_BY_SIGN_COLOR.put(sign, item);
        ModItems.SIGN_ITEMS_BY_SIGN_COLOR.put(sign, item);
    }
}
