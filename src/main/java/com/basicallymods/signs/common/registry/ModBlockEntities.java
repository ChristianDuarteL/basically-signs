package com.basicallymods.signs.common.registry;

import com.basicallymods.signs.BasicallySigns;
import com.basicallymods.signs.common.entity.SignBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

import static com.basicallymods.signs.common.registry.ModBlocks.SIGN_BLOCKS_BY_COLOR;

public class ModBlockEntities {
    public static DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, BasicallySigns.MOD_ID);
    public static final RegistryObject<BlockEntityType<SignBlockEntity>> SIGN_BLOCK_ENTITIES =
            REGISTRY.register("sign_block_entity", () ->
                    BlockEntityType.Builder.of(
                            SignBlockEntity::new,
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
