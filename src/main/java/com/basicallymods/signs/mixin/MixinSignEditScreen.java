package com.basicallymods.signs.mixin;

import com.basicallymods.signs.common.block.state.ColoredSign;
import com.basicallymods.signs.common.data.ISignColor;
import com.basicallymods.signs.common.registry.ModAtlases;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.SignEditScreen;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SignEditScreen.class)
public class MixinSignEditScreen {
    @Unique
    protected ISignColor basically_signs$color = null;
    @Unique
    protected boolean qfs$isFrontText;

    @Shadow
    private SignRenderer.SignModel signModel;

    @Inject(method = "<init>", at = @At("RETURN"))
    public void qfs$constructor(SignBlockEntity signBlockEntity, boolean isFront, boolean isTextFilteringEnabled, CallbackInfo ci){
        Block block = signBlockEntity.getBlockState().getBlock();
        if (block instanceof ColoredSign sign) {
            this.basically_signs$color = sign.getColorSign();
        }
        this.qfs$isFrontText = isFront;
    }

    @Inject(method = "renderSignBackground", at = @At("HEAD"), cancellable = true)
    protected void qfs$renderSignBackground(GuiGraphics guiGraphics, BlockState blockState, CallbackInfo ci) {
        if (this.signModel != null && basically_signs$color != null) {
            boolean flag = blockState.getBlock() instanceof StandingSignBlock;
            guiGraphics.pose().translate(0.0F, 31.0F, 0.0F);
            guiGraphics.pose().scale(62.500004F, 62.500004F, -62.500004F);
            Material material = ModAtlases.getSignMaterial(this.basically_signs$color);
            VertexConsumer vertexconsumer = material.buffer(guiGraphics.bufferSource(), this.signModel::renderType);
            this.signModel.stick.visible = flag;
            this.signModel.root.render(guiGraphics.pose(), vertexconsumer, 15728880, OverlayTexture.NO_OVERLAY);
            ci.cancel();
        }
    }
}
