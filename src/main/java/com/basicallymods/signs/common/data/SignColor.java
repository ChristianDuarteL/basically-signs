package com.basicallymods.signs.common.data;

import net.minecraft.world.item.DyeColor;

public enum SignColor {
    AMBER("amber"),
    APRICOT("apricot"),
    AQUA("aqua"),
    BUBBLEGUM("bubblegum"),
    CITRINE("citrine"),
    CORAL("coral"),
    CREAM("cream"),
    CRIMSON("crimson"),
    EMERALD("emerald"),
    ICE_BLUE("ice_blue"),
    MINT_GREEN("mint_green"),
    PERIWINKLE("periwinkle"),
    ROSE("rose"),
    ROYAL_BLUE("royal_blue"),
    SEAGREEN("seagreen"),
    SIENNA("sienna"),
    SLATE_BLUE("slate_blue"),
    SPOOKY_GREEN("spooky_green"),
    CUCURUCHO("cucurucho"),
    ENAIRA("enaira"),
    NYX("nyx"),
    SCARLET("scarlet"),
    OCEKI("oceki"),
    WALT("walt"),
    BLUEBERRIES("blueberries"),
    CHERRIES("cherries"),
    CITRUS("citrus"),
    CLOUDS("clouds"),
    COTTON_CANDY("cotton_candy"),
    GOOSEBERRIES("gooseberries"),
    GREEN_BLOSSOM("green_blossom"),
    KIWI_TARGET("kiwi_target"),
    LAVENDER_TARGET("lavender_target"),
    MAGENTA_BLOSSOM("magenta_blossom"),
    MERMAID("mermaid"),
    OCEANIC("oceanic"),
    ROYAL_BLOSSOM("royal_blossom"),
    SUNSET("sunset"),
    TANGERINE_TARGET("tangerine_target"),
    TOM("tom"),
    YELLOW_BLOSSOM("yellow_blossom"),

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

    public String getName(){
        return name;
    }
}