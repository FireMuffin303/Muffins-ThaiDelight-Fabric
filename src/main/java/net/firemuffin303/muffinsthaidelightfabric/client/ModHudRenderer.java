package net.firemuffin303.muffinsthaidelightfabric.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.firemuffin303.muffinsthaidelightfabric.ThaiDelight;
import net.firemuffin303.muffinsthaidelightfabric.common.entitydata.SpicyData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class ModHudRenderer {
    private static final ResourceLocation SPICY_OUTLINE_RESOURCE = new ResourceLocation(ThaiDelight.MOD_ID,"textures/misc/spicy_outline.png");

    public static void init(GuiGraphics guiGraphics, float deltaTracker){
        spicyOutline(guiGraphics,deltaTracker);
    }

    public static void spicyOutline(GuiGraphics guiGraphics,float deltaTracker){
        Minecraft minecraft = Minecraft.getInstance();
        SpicyData spicyData = ((SpicyData.SpicyAccessor) minecraft.player).muffinsThaiDelight$access();
        if(spicyData.getSpicyLevel() > 0){
            renderTextureOverlay(guiGraphics, SPICY_OUTLINE_RESOURCE,spicyData.getPercent());
        }
    }

    private static void renderTextureOverlay(GuiGraphics guiGraphics, ResourceLocation resourceLocation, float f) {
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, f);
        guiGraphics.blit(resourceLocation, 0, 0, -90, 0.0F, 0.0F, guiGraphics.guiWidth(), guiGraphics.guiHeight(), guiGraphics.guiWidth(), guiGraphics.guiHeight());
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
