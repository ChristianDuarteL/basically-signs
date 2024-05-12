package com.basicallymods.signs.common.registry;

import com.basicallymods.signs.BasicallySigns;
import com.basicallymods.signs.common.data.SignRecipe;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipeTypes {
    public static DeferredRegister<RecipeType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, BasicallySigns.MOD_ID);

    public static RegistryObject<RecipeType<SignRecipe>> SIGN_WORKBENCH_RECIPE = register("sign_workbench");

    static <T extends Recipe<?>> RegistryObject<RecipeType<T>> register(String name){
        return REGISTRY.register(name, () -> new RecipeType<T>(){
            @Override
            public String toString() {
                return name;
            }
        });
    }
}
