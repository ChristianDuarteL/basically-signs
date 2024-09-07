package com.basicallymods.signs.common.block;

import com.basicallymods.signs.common.block.state.ColoredSign;
import com.basicallymods.signs.common.data.ISignColor;
import com.basicallymods.signs.common.entity.SignBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class StandingSignBlock extends net.minecraft.world.level.block.StandingSignBlock implements ColoredSign {
    private final ISignColor color;
    private final Supplier<BlockEntityType<SignBlockEntity>> typeSupplier;

    public StandingSignBlock(ISignColor color, Supplier<BlockEntityType<SignBlockEntity>> typeSupplier) {
        super(Properties.copy(Blocks.OAK_SIGN), WoodType.SPRUCE);
        this.color = color;
        this.typeSupplier = typeSupplier;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SignBlockEntity(pos, state, typeSupplier);
    }

    @Override
    public ISignColor getColorSign() {
        return color;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        BlockEntity tileEntity = level.getBlockEntity(pos);
        Block block = state.getBlock();
        if (tileEntity instanceof net.minecraft.world.level.block.entity.SignBlockEntity signEntity && block instanceof ColoredSign sign) {
            ISignColor ISignColor = sign.getColorSign();
            signEntity.updateText((signText) -> {
                return signText.setColor(ISignColor.getColor());
            }, true);
        }
    }
}
