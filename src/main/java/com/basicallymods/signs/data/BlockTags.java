package com.basicallymods.signs.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

import static com.basicallymods.signs.BasicallySigns.MOD_ID;
import static com.basicallymods.signs.common.registry.ModBlocks.SIGN_BLOCKS_BY_COLOR;

public class BlockTags extends BlockTagsProvider {
    public BlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(net.minecraft.tags.BlockTags.STANDING_SIGNS).add(
                SIGN_BLOCKS_BY_COLOR
                        .values()
                        .stream()
                        .map(e -> e.standing().get())
                        .toArray(StandingSignBlock[]::new)
        );
        tag(net.minecraft.tags.BlockTags.WALL_SIGNS).add(
                SIGN_BLOCKS_BY_COLOR
                        .values()
                        .stream()
                        .map(e -> e.wall().get())
                        .toArray(WallSignBlock[]::new)
        );
    }
}
