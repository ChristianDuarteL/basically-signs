package com.basicallymods.signs.common.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import static com.basicallymods.signs.common.registry.ModBlockEntities.SIGN_BLOCK_ENTITIES;

public class SignBlockEntity extends net.minecraft.world.level.block.entity.SignBlockEntity {

    public SignBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(pPos, pBlockState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return SIGN_BLOCK_ENTITIES.get();
    }
}