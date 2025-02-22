package com.basicallymods.signs.common.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class SignBlockEntity extends net.minecraft.world.level.block.entity.SignBlockEntity {

    public static BlockEntityType.BlockEntitySupplier<SignBlockEntity> make (Supplier<BlockEntityType<SignBlockEntity>> type) {
        return (a, b) -> new SignBlockEntity(a, b, type.get());
    }

    public SignBlockEntity(BlockPos pPos, BlockState pBlockState, BlockEntityType<SignBlockEntity> type) {
        super(type, pPos, pBlockState);
    }
}