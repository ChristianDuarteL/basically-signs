package com.basicallymods.signs;

import com.basicallymods.signs.api.SignsRegisterer;
import com.basicallymods.signs.client.renderer.ColoredSignRenderer;
import com.basicallymods.signs.client.screens.SignWorkbenchScreen;
import com.basicallymods.signs.common.data.SignColor;
import com.basicallymods.signs.common.registry.*;
import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.StonecutterBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import static com.basicallymods.signs.common.registry.ModItems.SIGN_ITEMS_BY_SIGN_COLOR;

@Mod(BasicallySigns.MOD_ID)
public class BasicallySigns {
    public static final String MOD_ID = "basically_signs";
    public static SignsRegisterer registerer;

    public BasicallySigns() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        registerer = new SignsRegisterer(MOD_ID, ModBlocks.REGISTRY, ModItems.REGISTRY);

        for (SignColor color : SignColor.values()){
            registerer.registerBasic(color);
        }

        ModBlockEntities.REGISTRY.register(modEventBus);
        ModBlocks.REGISTRY.register(modEventBus);
        ModItems.REGISTRY.register(modEventBus);
        ModCreativeModeTabs.REGISTRY.register(modEventBus);
        ModMenus.REGISTRY.register(modEventBus);
        ModRecipeTypes.REGISTRY.register(modEventBus);
        ModRecipeSerializers.REGISTRY.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) { }

}
