package com.basicallymods.signs.common.data;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;

import static com.basicallymods.signs.BasicallySigns.MOD_ID;

public enum SignColor implements ISignColor {
    AMBER("amber"),
    APRICOT("apricot"),
    AQUA("aqua"),
    BLUEBERRY("blueberry"),
    BLUEHEART("blueheart"),
    BUBBLEGUM("bubblegum"),
    CHERRIES("cherries"),
    CITRINE("citrine"),
    CITRUS("citrus"),
    CLOUDS("clouds"),
    CORAL("coral"),
    COTTON_CANDY("cotton_candy"),
    CREAM("cream"),
    CRIMSON("crimson", DyeColor.WHITE),
    DUSTY("dusty"),
    EMERALD("emerald"),
    ENAIRA("enaira"),
    GOOSEBERRY("gooseberry"),
    GRAPE("grape", DyeColor.WHITE),
    GRASSY("grassy"),
    GREEN_BLOSSOM("green_blossom"),
    ICE_BLUE("ice_blue"),
    KIWI_TARGET("kiwi_target"),
    LAVENDER_TARGET("lavender_target"),
    LEMON("lemon"),
    MAGENTA_BLOSSOM("magenta_blossom"),
    MERMAID("mermaid"),
    MINT_GREEN("mint_green"),
    MINTHEART("mintheart"),
    MUCKY("mucky"),
    NYX("nyx"),
    OCEANIC("oceanic"),
    OCEKI("oceki"),
    ORANGEHEART("orangeheart"),
    PERIWINKLE("periwinkle"),
    PINKHEART("pinkheart"),
    PURPLEHEART("purpleheart"),
    ROSE("rose"),
    ROYAL_BLUE("royal_blue", DyeColor.WHITE),
    SANDY("sandy"),
    SCARLET("scarlet"),
    SEAGREEN("seagreen"),
    SIENNA("sienna", DyeColor.WHITE),
    SLATE_BLUE("slate_blue", DyeColor.WHITE),
    SNOWY("snowy"),
    SPOOKY_GREEN("spooky_green"),
    SUNSET("sunset"),
    TANGERINE_TARGET("tangerine_target"),
    TOM("tom"),
    VIA("via"),
    WALT("walt"),
    YELLOW_BLOSSOM("yellow_blossom"),
    YELLOWHEART("yellowheart"),
    PASTEL_YELLOW("pastel_yellow"),
    PASTEL_GREEN("pastel_green"),
    PASTEL_ORANGE("pastel_orange"),
    PASTEL_PINK("pastel_pink"),
    PASTEL_PURPLE("pastel_purple"),
    PASTEL_TEAL("pastel_teal"),
    SWIRL_TEAL("swirl_teal", DyeColor.WHITE),
    SWIRL_CYAN("swirl_cyan", DyeColor.WHITE),
    SWIRL_MAROON("swirl_maroon", DyeColor.WHITE),
    SWIRL_OLIVE("swirl_olive", DyeColor.WHITE),
    SWIRL_PINK("swirl_pink"),
    SWIRL_RED("swirl_red", DyeColor.WHITE),
    SWIRL_WALNUT("swirl_walnut", DyeColor.WHITE),
    SWIRL_PURPLE("swirl_purple"),
    RACCOON("raccoon"),
    HAZEL("hazel"),
    JAIME("jaime"),
    MOON("moon"),
    PICTURE("picture")
    ;

    private final String name;
    private final DyeColor color;

    SignColor(String name, DyeColor color){
        this.name = name;
        this.color = color;
    }

    SignColor(String name){
        this(name, DyeColor.BLACK);
    }

    public DyeColor getColor() {
        return color;
    }

    public String  getName(){
        return name;
    }

    @Override
    public ResourceLocation getEntityTexture() {
        return new ResourceLocation(MOD_ID, "entity/signs/" + this.getName());
    }
    @Override
    public ResourceLocation getItemTexture() {
        return new ResourceLocation(MOD_ID, "item/" + this.getName() + "_sign");
    }
}