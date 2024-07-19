package com.basicallymods.signs.api;

import com.google.common.collect.Iterables;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.function.Function;

public class DataGenerators {
    public static LootTableProvider.SubProviderEntry getLootProvider(SignsRegisterer registerer) {
        return new LootTableProvider.SubProviderEntry(() -> new BlockLootProvider(registerer), LootContextParamSets.BLOCK);
    }

    public static class BlockLootProvider extends BlockLootSubProvider {
        public final SignsRegisterer registerer;
        public BlockLootProvider(SignsRegisterer registerer) {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags());
            this.registerer = registerer;
        }
        @Override
        protected void generate() {
            registerer.SIGN_BLOCKS_BY_COLOR.values().forEach(e -> {
                dropSelf(e.standing().get());
                dropSelf(e.wall().get());
            });
        }

        @Override
        protected @NotNull Iterable<Block> getKnownBlocks() {
            return Iterables.concat(registerer.SIGN_BLOCKS_BY_COLOR.values().stream().flatMap(e -> Set.of((Block) e.wall().get(),
                    e.standing().get()).stream())::iterator);
        }

    }


    public static void registerBlockModels(BlockModelProvider provider, SignsRegisterer registerer){
        final ResourceLocation planks_particles = new ResourceLocation(
                "minecraft", "block/spruce_planks"
        );
        registerer.SIGN_BLOCKS_BY_COLOR.values().forEach(
                block -> {
                    provider.sign(ForgeRegistries.BLOCKS.getKey(block.standing().get()).getPath(), planks_particles);
                    provider.sign(ForgeRegistries.BLOCKS.getKey(block.wall().get()).getPath(), planks_particles);
                }
        );
    }

    public static void registerBlockStates(BlockStateProvider provider, SignsRegisterer registerer){
        registerer.SIGN_BLOCKS_BY_COLOR.values().forEach(e -> {
            provider.simpleBlock(e.standing().get(), existingModel(e.standing().get(), provider, registerer));
            provider.simpleBlock(e.wall().get(), existingModel(e.wall().get(), provider, registerer));
        });
    }

    public static void registerBlockTags(Function<TagKey<Block>, IntrinsicHolderTagsProvider.IntrinsicTagAppender<Block>> tag, SignsRegisterer registerer) {
        tag.apply(net.minecraft.tags.BlockTags.STANDING_SIGNS).add(
                registerer.SIGN_BLOCKS_BY_COLOR
                        .values()
                        .stream()
                        .map(e -> e.standing().get())
                        .toArray(StandingSignBlock[]::new)
        );
        tag.apply(net.minecraft.tags.BlockTags.WALL_SIGNS).add(
                registerer.SIGN_BLOCKS_BY_COLOR
                        .values()
                        .stream()
                        .map(e -> e.wall().get())
                        .toArray(WallSignBlock[]::new)
        );
    }

    public static void registerItemTags(Function<TagKey<Item>, IntrinsicHolderTagsProvider.IntrinsicTagAppender<Item>> tag, SignsRegisterer registerer) {
        tag.apply(ItemTags.SIGNS).add(
                registerer.SIGN_ITEMS_BY_SIGN_COLOR
                        .values()
                        .stream()
                        .map(RegistryObject::get)
                        .toArray(Item[]::new)
        );
    }

    public static void registerItemModels(ItemModelProvider provider, SignsRegisterer registerer){
        registerer.SIGN_ITEMS_BY_SIGN_COLOR.forEach((key, value) -> provider.withExistingParent(ForgeRegistries.ITEMS.getKey(value.get()).getPath(), "minecraft:item/generated")
                .texture("layer0", key.getItemTexture()));
    }

    private static String blockName(Block block, SignsRegisterer registerer) {
        return registerer.blocksRegistry.getEntries().stream().filter(e ->
                e.get() == block
        ).findFirst().get().getId().getPath();
    }

    private static ResourceLocation resourceBlock(String path, SignsRegisterer registerer) {
        return new ResourceLocation(registerer.MOD_ID, "block/" + path);
    }

    private static ModelFile existingModel(Block block, BlockStateProvider provider, SignsRegisterer registerer) {
        return new ModelFile.ExistingModelFile(resourceBlock(blockName(block,registerer), registerer), provider.models().existingFileHelper);
    }
}
