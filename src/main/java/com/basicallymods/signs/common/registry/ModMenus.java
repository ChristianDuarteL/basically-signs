package com.basicallymods.signs.common.registry;

import com.basicallymods.signs.BasicallySigns;
import com.basicallymods.signs.common.menu.SignWorkbenchMenu;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenus {
    public static DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.MENU_TYPES, BasicallySigns.MOD_ID);
    public static RegistryObject<MenuType<SignWorkbenchMenu>> SIGN_WORKBENCH_MENU = REGISTRY.register("sign_workbench", () -> new MenuType<>(SignWorkbenchMenu::new, FeatureFlagSet.of()));
}
