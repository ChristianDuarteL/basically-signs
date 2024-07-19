package com.basicallymods.signs.common.registry;

import com.basicallymods.signs.BasicallySigns;
import com.basicallymods.signs.common.block.SignWorkbenchBlock;
import com.basicallymods.signs.common.block.StandingSignBlock;
import com.basicallymods.signs.common.block.WallSignBlock;
import com.basicallymods.signs.common.data.ISignColor;
import com.basicallymods.signs.common.data.SignColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ModBlocks {
    public record SignBlock(RegistryObject<? extends net.minecraft.world.level.block.StandingSignBlock> standing, RegistryObject<? extends net.minecraft.world.level.block.WallSignBlock> wall) { }
    public static DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, BasicallySigns.MOD_ID);

    public static final Map<ISignColor, SignBlock> SIGN_BLOCKS_BY_COLOR = new HashMap<>();

    public static final RegistryObject<SignWorkbenchBlock> SIGN_WORKBENCH = REGISTRY.register("workbench", () -> new SignWorkbenchBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASS).strength(2.5F).sound(SoundType.WOOD).ignitedByLava()));
}
