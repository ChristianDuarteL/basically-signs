package com.basicallymods.signs.common.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class SignBlockEntity extends net.minecraft.world.level.block.entity.SignBlockEntity {

    public static BlockEntityType.BlockEntitySupplier<SignBlockEntity> make (Supplier<BlockEntityType<SignBlockEntity>> typeSupplier) {
        return (a, b) -> new SignBlockEntity(a, b, typeSupplier);
    }

    final Supplier<BlockEntityType<SignBlockEntity>> typeSupplier;

    public SignBlockEntity(BlockPos pPos, BlockState pBlockState, Supplier<BlockEntityType<SignBlockEntity>> typeSupplier) {
        super(pPos, pBlockState);
        this.typeSupplier = typeSupplier;
    }

    @Override
    public BlockEntityType<?> getType() {
        return typeSupplier.get();
    }
}