package net.firemuffin303.muffinsthaidelightfabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class ModDataGen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(ModelDataGen::new);
        pack.addProvider(ModBlockLootTableData::new);
        pack.addProvider(ModBlockLootTableData.ModEntityLootTableData::new);
        pack.addProvider(ModBlockLootTableData.ModChestLootTableData::new);
        pack.addProvider(AdvancementDataGen::new);
        pack.addProvider(LangDataGen::new);
        pack.addProvider(LangDataGen.ThaiLangData::new);
        pack.addProvider(ModTagDataGen.ModBlockTagDataGen::new);
        pack.addProvider(ModTagDataGen.ModItemTagDataGen::new);
        pack.addProvider(ModTagDataGen.ModEntityTypesTagDataGen::new);
        pack.addProvider(ModRecipeDataGen::new);

    }
}
