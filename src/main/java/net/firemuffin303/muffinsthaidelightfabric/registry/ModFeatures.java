package net.firemuffin303.muffinsthaidelightfabric.registry;

import net.firemuffin303.muffinsthaidelightfabric.ThaiDelight;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModFeatures {
    public static final ResourceKey<ConfiguredFeature<?,?>> FEATURE_PATCH_LIME_BUSH;
    public static final ResourceKey<ConfiguredFeature<?,?>> FEATURE_PATCH_WILD_PEPPER;
    public static final ResourceKey<ConfiguredFeature<?, ?>> FEATURE_PAPAYA_TREE;
    public static final ResourceKey<PlacedFeature> PATCH_LIME_BUSH;
    public static final ResourceKey<PlacedFeature> PATCH_WILD_PEPPER;
    public static final ResourceKey<PlacedFeature> PAPAYA_TREE_CHECKED;



    public static void init(){}

    static {
        FEATURE_PATCH_LIME_BUSH = ResourceKey.create(Registries.CONFIGURED_FEATURE,new ResourceLocation(ThaiDelight.MOD_ID,"patch_lime_bush"));
        FEATURE_PATCH_WILD_PEPPER = ResourceKey.create(Registries.CONFIGURED_FEATURE,new ResourceLocation(ThaiDelight.MOD_ID,"patch_wild_pepper"));
        FEATURE_PAPAYA_TREE = ResourceKey.create(Registries.CONFIGURED_FEATURE,new ResourceLocation(ThaiDelight.MOD_ID,"papaya_tree"));

        PATCH_LIME_BUSH = ResourceKey.create(Registries.PLACED_FEATURE,new ResourceLocation(ThaiDelight.MOD_ID,"patch_lime_bush"));
        PATCH_WILD_PEPPER = ResourceKey.create(Registries.PLACED_FEATURE,new ResourceLocation(ThaiDelight.MOD_ID,"patch_wild_pepper"));
        PAPAYA_TREE_CHECKED = ResourceKey.create(Registries.PLACED_FEATURE,new ResourceLocation(ThaiDelight.MOD_ID,"papaya_tree_checked"));


    }
}
