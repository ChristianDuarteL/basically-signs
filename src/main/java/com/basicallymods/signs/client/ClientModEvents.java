package com.basicallymods.signs.client;

import com.basicallymods.signs.client.renderer.ColoredSignRenderer;
import com.basicallymods.signs.client.screens.SignWorkbenchScreen;
import com.basicallymods.signs.common.registry.ModBlockEntities;
import com.basicallymods.signs.common.registry.ModMenus;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static com.basicallymods.signs.BasicallySigns.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        BlockEntityRenderers.register(ModBlockEntities.SIGN_BLOCK_ENTITIES.get(), ColoredSignRenderer::new);

        event.enqueueWork(
            () -> MenuScreens.register(ModMenus.SIGN_WORKBENCH_MENU.get(), SignWorkbenchScreen::new)
        );
    }
}
