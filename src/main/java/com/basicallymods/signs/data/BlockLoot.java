package com.basicallymods.signs.data;

import com.basicallymods.signs.BasicallySigns;
import com.basicallymods.signs.api.DataGenerators;
import com.google.common.collect.ImmutableList;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.basicallymods.signs.common.registry.ModBlocks.SIGN_WORKBENCH;

public class BlockLoot extends LootTableProvider{

    static final List<LootTableProvider.SubProviderEntry> PROVIDERS = ImmutableList.of(
        new LootTableProvider.SubProviderEntry(BlockLootProvider::new, LootContextParamSets.BLOCK),
        DataGenerators.getLootProvider(BasicallySigns.registerer)
    );

    public BlockLoot(PackOutput pOutput) {
        super(pOutput, Collections.emptySet(), PROVIDERS);
    }

    static class BlockLootProvider extends BlockLootSubProvider {

        public BlockLootProvider() {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags());
        }

        @Override
        protected void generate() {
            dropSelf(SIGN_WORKBENCH.get());
        }

        @Override
        protected @NotNull Iterable<Block> getKnownBlocks() {
            return List.of(SIGN_WORKBENCH.get());
        }
    }
}
