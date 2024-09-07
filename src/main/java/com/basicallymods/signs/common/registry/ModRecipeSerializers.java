package com.basicallymods.signs.common.registry;

import com.basicallymods.signs.BasicallySigns;
import com.basicallymods.signs.common.data.SignRecipe;
import net.minecraft.world.item.crafting.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipeSerializers {
    public static DeferredRegister<RecipeSerializer<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, BasicallySigns.MOD_ID);

    public static RegistryObject<RecipeSerializer<?>> SIGN_WORKBENCH_RECIPE = register(
            "sign_workbench",
            SignRecipe.SIGN_RECIPE_SERIALIZER
    );

    static RegistryObject<RecipeSerializer<?>> register(String name, RecipeSerializer<?> serializer){
        return REGISTRY.register(name, () -> serializer);
    }
}
