package com.basicallymods.signs.common.registry;

import com.basicallymods.signs.common.data.ISignColor;
import com.basicallymods.signs.common.data.SignColor;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ModAtlases {
    public static final Map<ISignColor, Material> COLORED_SIGN_MATERIALS =
            Arrays.stream(SignColor.values()).collect(Collectors.toMap(Function.identity(), ModAtlases::getSignMaterial));


    public static Material getSignMaterial(ISignColor dyeType) {
        return new Material(
            Sheets.SIGN_SHEET,
            dyeType.getEntityTexture()
        );
    }
}
