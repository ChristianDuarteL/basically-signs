package com.basicallymods.signs.common.data;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;

public interface ISignColor {
    DyeColor getColor();
    String getName();
    ResourceLocation getEntityTexture();
    ResourceLocation getItemTexture();
    default String wallSignName() {
        return this.getName() + "_wall_sign";
    }
    default String standingSignName() {
        return this.getName() + "_sign";
    }
    default String itemName() {
        return this.getName() + "_sign";
    }
}
