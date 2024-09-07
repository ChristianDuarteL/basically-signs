package com.basicallymods.signs.api;

import com.basicallymods.signs.client.renderer.ColoredSignRenderer;
import com.basicallymods.signs.common.block.StandingSignBlock;
import com.basicallymods.signs.common.block.WallSignBlock;
import com.basicallymods.signs.common.data.ISignColor;
import com.basicallymods.signs.common.entity.SignBlockEntity;
import com.basicallymods.signs.common.registry.ModBlockEntities;
import com.basicallymods.signs.common.registry.ModBlocks;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignsRegisterer {
    public final DeferredRegister<Block> blocksRegistry;
    public final DeferredRegister<Item> itemsRegistry;
    public final DeferredRegister<BlockEntityType<?>> blockEntityRegister;
    public final Map<ISignColor, ModBlocks.SignBlock> SIGN_BLOCKS_BY_COLOR = new HashMap<>();
    public final Map<ISignColor, RegistryObject<? extends Item>> SIGN_ITEMS_BY_SIGN_COLOR = new HashMap<>();
    private RegistryObject<BlockEntityType<SignBlockEntity>> signBlockEntity;

    public final String MOD_ID;

    public SignsRegisterer(String MOD_ID) {
        this(MOD_ID, DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID), DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID), DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MOD_ID));
    }

    public SignsRegisterer(String MOD_ID, DeferredRegister<Block> blocksRegistry, DeferredRegister<Item> itemsRegistry, DeferredRegister<BlockEntityType<?>> blockEntityRegister) {
        this.blocksRegistry = blocksRegistry;
        this.itemsRegistry = itemsRegistry;
        this.blockEntityRegister = blockEntityRegister;
        this.MOD_ID = MOD_ID;
        this.signBlockEntity = null;
        this.signBlockEntity = blockEntityRegister.register("sign_block_entity", () ->
            BlockEntityType.Builder.of(
                    SignBlockEntity.make(this.signBlockEntity),
                    SIGN_BLOCKS_BY_COLOR
                            .values()
                            .stream()
                            .map(e -> List.of(e.standing().get(), e.wall().get()))
                            .reduce(new ArrayList<>(), (a, b) -> {
                                a.addAll(b);
                                return a;
                            }).toArray(Block[]::new)
            ).build(null));
    }

    public void registerBasic(ISignColor sign) {
        RegistryObject<StandingSignBlock> standingBlock = blocksRegistry.register(sign.standingSignName(), () -> new StandingSignBlock(sign, this.signBlockEntity));
        RegistryObject<WallSignBlock> wallSignBlock = blocksRegistry.register(sign.wallSignName(), () -> new WallSignBlock(sign, this.signBlockEntity));
        ModBlocks.SignBlock block = new ModBlocks.SignBlock(standingBlock, wallSignBlock);
        SIGN_BLOCKS_BY_COLOR.put(sign, block);
        RegistryObject<SignItem> item = itemsRegistry.register(
                sign.itemName(),
                () -> new SignItem(
                        new Item.Properties().stacksTo(64),
                        standingBlock.get(),
                        wallSignBlock.get()
                )
        );

        SIGN_ITEMS_BY_SIGN_COLOR.put(sign, item);
    }

    public void setupClient(){
        BlockEntityRenderers.register(signBlockEntity.get(), ColoredSignRenderer::new);
    }

    public RegistryObject<BlockEntityType<SignBlockEntity>> getSignBlockEntity() {
        return signBlockEntity;
    }
}
