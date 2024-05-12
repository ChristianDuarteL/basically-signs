package com.basicallymods.signs.common.registry;

import com.basicallymods.signs.BasicallySigns;
import com.basicallymods.signs.common.data.SignRecipe;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

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
