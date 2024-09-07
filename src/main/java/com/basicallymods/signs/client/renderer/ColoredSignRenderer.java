package com.basicallymods.signs.client.renderer;

import com.basicallymods.signs.common.block.state.ColoredSign;
import com.basicallymods.signs.common.data.ISignColor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.util.FastColor;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.SignText;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

import static com.basicallymods.signs.common.registry.ModAtlases.COLORED_SIGN_MATERIALS;

@OnlyIn(Dist.CLIENT)
public class ColoredSignRenderer extends SignRenderer
{
    private static final int OUTLINE_RENDER_DISTANCE = Mth.square(16);
    private static final Vec3 TEXT_OFFSET = new Vec3(0.0D, 0.33333334F, 0.046666667F);

    private final SignModel signModel;
    private final Font font;

    public ColoredSignRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
        this.signModel = new SignModel(context.bakeLayer(ModelLayers.createSignModelName(WoodType.SPRUCE)));
        this.font = context.getFont();
    }

    @Override
    public void render(SignBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        BlockState blockstate = pBlockEntity.getBlockState();
        SignBlock signblock = (SignBlock)blockstate.getBlock();
        WoodType woodtype = SignBlock.getWoodType(signblock);
        SignModel signrenderer$signmodel = this.signModel;
        signrenderer$signmodel.stick.visible = blockstate.getBlock() instanceof StandingSignBlock;
        this.renderSignWithText(pBlockEntity, pPoseStack, pBuffer, pPackedLight, pPackedOverlay, blockstate, signblock, woodtype, signrenderer$signmodel);
    }

    void renderSignWithText(SignBlockEntity pSignEntity, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay, BlockState pState, SignBlock pSignBlock, WoodType pWoodType, Model pModel) {
        pPoseStack.pushPose();
        this.translateSign(pPoseStack, -pSignBlock.getYRotationDegrees(pState), pState);
        this.renderSign(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, pWoodType, pModel, pSignEntity);
        this.renderSignText(pSignEntity.getBlockPos(), pSignEntity.getFrontText(), pPoseStack, pBuffer, pPackedLight, pSignEntity.getTextLineHeight(), pSignEntity.getMaxTextLineWidth(), true, getCustomOffset(pSignEntity));
        this.renderSignText(pSignEntity.getBlockPos(), pSignEntity.getBackText(), pPoseStack, pBuffer, pPackedLight, pSignEntity.getTextLineHeight(), pSignEntity.getMaxTextLineWidth(), false, getCustomOffset(pSignEntity));
        pPoseStack.popPose();
    }

    public int getCustomOffset(SignBlockEntity entity) {
        if(entity.getBlockState().getBlock() instanceof ColoredSign sign){
            return sign.getColorSign().getYOffset();
        }
        return 0;
    }

    void translateSign(PoseStack pPoseStack, float pYRot, BlockState pState) {
        pPoseStack.translate(0.5F, 0.75F * this.getSignModelRenderScale(), 0.5F);
        pPoseStack.mulPose(Axis.YP.rotationDegrees(pYRot));
        if (!(pState.getBlock() instanceof StandingSignBlock)) {
            pPoseStack.translate(0.0F, -0.3125F, -0.4375F);
        }

    }

    void renderSign(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay, WoodType pWoodType, Model pModel, SignBlockEntity pSignEntity) {
        pPoseStack.pushPose();
        float f = this.getSignModelRenderScale();
        pPoseStack.scale(f, -f, -f);
        Material material = this.getSignMaterial(pWoodType, pSignEntity);
        VertexConsumer vertexconsumer = material.buffer(pBuffer, pModel::renderType);
        this.renderSignModel(pPoseStack, pPackedLight, pPackedOverlay, pModel, vertexconsumer);
        pPoseStack.popPose();
    }

    void renderSignModel(PoseStack pPoseStack, int pPackedLight, int pPackedOverlay, Model pModel, VertexConsumer pVertexConsumer) {
        SignModel signrenderer$signmodel = (SignModel)pModel;
        signrenderer$signmodel.root.render(pPoseStack, pVertexConsumer, pPackedLight, pPackedOverlay);
    }

    Material getSignMaterial(WoodType pWoodType, SignBlockEntity pSignEntity) {
        if(pSignEntity.getBlockState().getBlock() instanceof ColoredSign sign){
            return getMaterial(sign.getColorSign());
        }
        return Sheets.getSignMaterial(pWoodType);
    }

    void renderSignText(BlockPos pPos, SignText pText, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pLineHeight, int pMaxWidth, boolean pIsFrontText, int yOffset) {
        pPoseStack.pushPose();
        this.translateSignText(pPoseStack, pIsFrontText, this.getTextOffset());
        int i = getDarkColor(pText);
        int j = 2 * pLineHeight + yOffset;
        FormattedCharSequence[] aformattedcharsequence = pText.getRenderMessages(Minecraft.getInstance().isTextFilteringEnabled(), (p_277227_) -> {
            List<FormattedCharSequence> list = this.font.split(p_277227_, pMaxWidth);
            return list.isEmpty() ? FormattedCharSequence.EMPTY : list.get(0);
        });
        int k;
        boolean flag;
        int l;
        if (pText.hasGlowingText()) {
            k = pText.getColor().getTextColor();
            flag = isOutlineVisible(pPos, k);
            l = 15728880;
        } else {
            k = i;
            flag = false;
            l = pPackedLight;
        }

        for(int i1 = 0; i1 < 4; ++i1) {
            FormattedCharSequence formattedcharsequence = aformattedcharsequence[i1];
            float f = (float)(-this.font.width(formattedcharsequence) / 2);
            float y = (float)(i1 * pLineHeight - j);
            if (flag) {
                this.font.drawInBatch8xOutline(formattedcharsequence, f, y, k, i, pPoseStack.last().pose(), pBuffer, l);
            } else {
                this.font.drawInBatch(formattedcharsequence, f, y, k, false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, l);
            }
        }

        pPoseStack.popPose();
    }

    private void translateSignText(PoseStack pPoseStack, boolean pIsFrontText, Vec3 pOffset) {
        if (!pIsFrontText) {
            pPoseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        }

        float f = 0.015625F * this.getSignTextRenderScale();
        pPoseStack.translate(pOffset.x, pOffset.y, pOffset.z);
        pPoseStack.scale(f, -f, f);
    }

    Vec3 getTextOffset() {
        return TEXT_OFFSET;
    }

    static boolean isOutlineVisible(BlockPos pPos, int pTextColor) {
        if (pTextColor == DyeColor.BLACK.getTextColor()) {
            return true;
        } else {
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer localplayer = minecraft.player;
            if (localplayer != null && minecraft.options.getCameraType().isFirstPerson() && localplayer.isScoping()) {
                return true;
            } else {
                Entity entity = minecraft.getCameraEntity();
                return entity != null && entity.distanceToSqr(Vec3.atCenterOf(pPos)) < (double)OUTLINE_RENDER_DISTANCE;
            }
        }
    }

    static int getDarkColor(SignText pSignText) {
        int i = pSignText.getColor().getTextColor();
        if (i == DyeColor.BLACK.getTextColor() && pSignText.hasGlowingText()) {
            return -988212;
        } else if(i == DyeColor.WHITE.getTextColor() && !pSignText.hasGlowingText()) {
            return i;
        } else {
            double d0 = 0.4D;
            int j = (int)((double) FastColor.ARGB32.red(i) * d0);
            int k = (int)((double)FastColor.ARGB32.green(i) * d0);
            int l = (int)((double)FastColor.ARGB32.blue(i) * d0);
            return FastColor.ARGB32.color(0, j, k, l);
        }
    }

    public static Material getMaterial(ISignColor signColor) {
        return COLORED_SIGN_MATERIALS.get(signColor);
    }
}