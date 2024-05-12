package com.basicallymods.signs.common.registry;

import com.basicallymods.signs.BasicallySigns;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.registries.DeferredRegister;

public class ModCreativeModeTabs {
    public static DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BasicallySigns.MOD_ID);
}
