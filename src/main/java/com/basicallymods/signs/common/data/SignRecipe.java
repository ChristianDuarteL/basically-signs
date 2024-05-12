package com.basicallymods.signs.common.data;

import com.basicallymods.signs.common.registry.ModItems;
import com.basicallymods.signs.common.registry.ModRecipeSerializers;
import com.basicallymods.signs.common.registry.ModRecipeTypes;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SignRecipe implements Recipe<Container> {
    protected final Ingredient ingredient;
    protected final Ingredient dye;

    protected final ItemStack result;
    private final RecipeType<?> type;
    private final RecipeSerializer<?> serializer;
    protected final ResourceLocation id;
    protected final String group;

    public SignRecipe(RecipeType<?> pType, RecipeSerializer<?> pSerializer, ResourceLocation pId, String pGroup, Ingredient pIngredient, Ingredient dye, ItemStack pResult) {
        this.type = pType;
        this.serializer = pSerializer;
        this.id = pId;
        this.group = pGroup;
        this.ingredient = pIngredient;
        this.result = pResult;
        this.dye = dye;
    }

    public SignRecipe(ResourceLocation pId, String pGroup, Ingredient pIngredient, Ingredient pDye, ItemStack pResult) {
        this(ModRecipeTypes.SIGN_WORKBENCH_RECIPE.get(), ModRecipeSerializers.SIGN_WORKBENCH_RECIPE.get(), pId, pGroup, pIngredient, pDye, pResult);
    }

    public @NotNull RecipeType<?> getType() {
        return this.type;
    }

    public @NotNull RecipeSerializer<?> getSerializer() {
        return this.serializer;
    }

    public @NotNull ResourceLocation getId() {
        return this.id;
    }

    public @NotNull String getGroup() {
        return this.group;
    }

    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess pRegistryAccess) {
        return this.result;
    }

    public @NotNull NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> nonnulllist = NonNullList.create();
        nonnulllist.add(this.ingredient);
        return nonnulllist;
    }

    @Override
    public boolean matches(Container pContainer, @NotNull Level pLevel) {
        return this.ingredient.test(pContainer.getItem(0)) && this.dye.test(pContainer.getItem(1));
    }

    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    public @NotNull ItemStack assemble(@NotNull Container pContainer, @NotNull RegistryAccess pRegistryAccess) {
        return this.result.copy();
    }

    public @NotNull ItemStack getToastSymbol() {
        return new ItemStack(ModItems.SIGN_ITEMS_BY_SIGN_COLOR.get(SignColor.AMBER).get());
    }

    public static final Serializer<SignRecipe> SIGN_RECIPE_SERIALIZER = new Serializer<>(SignRecipe::new);

    public static class Serializer<T extends SignRecipe> implements RecipeSerializer<T> {

        final SignRecipe.Serializer.SingleItemMaker<T> factory;

        public Serializer(SignRecipe.Serializer.SingleItemMaker<T> pFactory) {
            this.factory = pFactory;
        }

        public T fromJson(ResourceLocation pRecipeId, JsonObject pJson) {
            String s = GsonHelper.getAsString(pJson, "group", "");
            Ingredient ingredient;
            if (GsonHelper.isArrayNode(pJson, "ingredient")) {
                ingredient = Ingredient.fromJson(GsonHelper.getAsJsonArray(pJson, "ingredient"), false);
            } else {
                ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(pJson, "ingredient"), false);
            }
            Ingredient dye;
            if (GsonHelper.isArrayNode(pJson, "dye")) {
                dye = Ingredient.fromJson(GsonHelper.getAsJsonArray(pJson, "dye"), false);
            } else {
                dye = Ingredient.fromJson(GsonHelper.getAsJsonObject(pJson, "dye"), false);
            }

            String s1 = GsonHelper.getAsString(pJson, "result");
            ItemStack itemstack = new ItemStack(BuiltInRegistries.ITEM.get(new ResourceLocation(s1)), 1);
            return this.factory.create(pRecipeId, s, ingredient, dye, itemstack);
        }

        public T fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            String s = pBuffer.readUtf();
            Ingredient ingredient = Ingredient.fromNetwork(pBuffer);
            Ingredient dye = Ingredient.fromNetwork(pBuffer);
            ItemStack itemstack = pBuffer.readItem();
            return this.factory.create(pRecipeId, s, ingredient, dye, itemstack);
        }

        public void toNetwork(FriendlyByteBuf pBuffer, T pRecipe) {
            pBuffer.writeUtf(pRecipe.getGroup());
            pRecipe.ingredient.toNetwork(pBuffer);
            pRecipe.dye.toNetwork(pBuffer);
            pBuffer.writeItem(pRecipe.result);
        }

        public interface SingleItemMaker<T extends SignRecipe> {
            T create(ResourceLocation pId, String pGroup, Ingredient pIngredient, Ingredient pDye, ItemStack pResult);
        }
    }
}
