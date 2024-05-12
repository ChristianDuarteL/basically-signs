package com.basicallymods.signs.common.registry;

import com.basicallymods.signs.common.data.SignColor;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.basicallymods.signs.BasicallySigns.MOD_ID;

public class ModAtlases {
    public static final Map<SignColor, Material> COLORED_SIGN_MATERIALS =
            Arrays.stream(SignColor.values()).collect(Collectors.toMap(Function.identity(), ModAtlases::getSignMaterial));


    public static Material getSignMaterial(SignColor dyeType) {
        return new Material(
                Sheets.SIGN_SHEET,
                new ResourceLocation(MOD_ID, "entity/signs/" + dyeType.getName())
        );
    }
}
