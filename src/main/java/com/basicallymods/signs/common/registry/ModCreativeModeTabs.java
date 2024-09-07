package com.basicallymods.signs.common.registry;

import com.basicallymods.signs.BasicallySigns;
import com.basicallymods.signs.common.data.SignColor;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.basicallymods.signs.BasicallySigns.MOD_ID;
import static com.basicallymods.signs.BasicallySigns.registerer;

public class ModCreativeModeTabs {
    public static DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    public static RegistryObject<CreativeModeTab> TAB = REGISTRY.register("signs_tab", () -> CreativeModeTab.builder()
        .title(Component.translatable("item_group." + MOD_ID))
        // Set icon of creative tab
        .icon(() -> new ItemStack(registerer.SIGN_ITEMS_BY_SIGN_COLOR.get(SignColor.AMBER).get()))
        .withBackgroundLocation(new ResourceLocation("basically_signs", "textures/gui/creative_inventory/signs_tab.png"))
        // Add default items to tab
        .withSearchBar(80)
        .displayItems((eparams, out) -> {
            BasicallySigns.registerer.itemsRegistry.getEntries().forEach(e -> out.accept(e.get()));
        })
        .build()
    );

}
