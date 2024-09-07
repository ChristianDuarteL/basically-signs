package com.basicallymods.signs.common.data;


import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Consumer;

import static com.basicallymods.signs.common.data.SignRecipe.SIGN_RECIPE_SERIALIZER;

public class SignRecipeBuilder implements RecipeBuilder {
    private final RecipeCategory category;
    private final Item result;
    private final Ingredient ingredient;
    private final Ingredient dye;
    private final int count;
    private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();
    @Nullable
    private String group;
    private final RecipeSerializer<?> type;

    public SignRecipeBuilder(RecipeCategory pCategory, RecipeSerializer<?> pType, Ingredient pIngredient, Ingredient pDye, ItemLike pResult, int pCount) {
        this.category = pCategory;
        this.type = pType;
        this.result = pResult.asItem();
        this.ingredient = pIngredient;
        this.dye = pDye;
        this.count = pCount;
    }

    public static SignRecipeBuilder of(Ingredient pIngredient, Ingredient pDye, RecipeCategory pCategory, ItemLike pResult) {
        return new SignRecipeBuilder(pCategory, SIGN_RECIPE_SERIALIZER, pIngredient, pDye, pResult, 1);
    }

    public SignRecipeBuilder unlockedBy(String pCriterionName, CriterionTriggerInstance pCriterionTrigger) {
        this.advancement.addCriterion(pCriterionName, pCriterionTrigger);
        return this;
    }

    public SignRecipeBuilder group(@Nullable String pGroupName) {
        this.group = pGroupName;
        return this;
    }

    public Item getResult() {
        return this.result;
    }

    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ResourceLocation pRecipeId) {
        this.ensureValid(pRecipeId);
        this.advancement.parent(ROOT_RECIPE_ADVANCEMENT).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pRecipeId)).rewards(AdvancementRewards.Builder.recipe(pRecipeId)).requirements(RequirementsStrategy.OR);
        pFinishedRecipeConsumer.accept(new Result(pRecipeId, this.type, this.group == null ? "" : this.group, this.ingredient, this.dye, this.result, this.count, this.advancement, pRecipeId.withPrefix("recipes/" + this.category.getFolderName() + "/")));
    }

    private void ensureValid(ResourceLocation pId) {
        if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + pId);
        }
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final String group;
        private final Ingredient ingredient;
        private final Ingredient dye;
        private final Item result;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;
        private final RecipeSerializer<?> type;

        public Result(ResourceLocation pId, RecipeSerializer<?> pType, String pGroup, Ingredient pIngredient, Ingredient pDye, Item pResult, int pCount, Advancement.Builder pAdvancement, ResourceLocation pAdvancementId) {
            this.id = pId;
            this.type = pType;
            this.group = pGroup;
            this.ingredient = pIngredient;
            this.dye = pDye;
            this.result = pResult;
            this.advancement = pAdvancement;
            this.advancementId = pAdvancementId;
        }

        public void serializeRecipeData(JsonObject pJson) {
            if (!this.group.isEmpty()) {
                pJson.addProperty("group", this.group);
            }

            pJson.add("ingredient", this.ingredient.toJson());
            pJson.add("dye", this.dye.toJson());
            pJson.addProperty("result", Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(this.result)).toString());
        }

        public ResourceLocation getId() {
            return this.id;
        }

        public RecipeSerializer<?> getType() {
            return this.type;
        }

        @Nullable
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Nullable
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}