package com.basicallymods.signs.data;

import com.basicallymods.signs.common.data.SignRecipe;
import com.basicallymods.signs.common.data.SignRecipeBuilder;
import com.basicallymods.signs.common.registry.ModItems;
import com.google.gson.JsonObject;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Consumer;

import static com.basicallymods.signs.BasicallySigns.MOD_ID;

public class Recipes extends RecipeProvider {

    public Recipes(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        ModItems.SIGN_ITEMS_BY_SIGN_COLOR.forEach((e, c) -> {
            SignRecipeBuilder
                .of(Ingredient.of(ItemTags.SIGNS), Ingredient.of(Items.BLUE_DYE), RecipeCategory.DECORATIONS, c.get())
                .unlockedBy("has_sign", has(ItemTags.SIGNS))
                .save(pWriter);
        });
    }
}
