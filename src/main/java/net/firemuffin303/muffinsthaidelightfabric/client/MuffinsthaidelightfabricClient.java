package net.firemuffin303.muffinsthaidelightfabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.firemuffin303.muffinsthaidelightfabric.ThaiDelight;
import net.firemuffin303.muffinsthaidelightfabric.common.entitydata.SpicyData;
import net.firemuffin303.muffinsthaidelightfabric.registry.ModBlocks;
import net.minecraft.client.renderer.RenderType;

public class MuffinsthaidelightfabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(),
                ModBlocks.SOMTAM_FEAST,
                ModBlocks.LIME_SAPLING,
                ModBlocks.LIME_CROP,
                ModBlocks.WILD_PEPPER_CROP,
                ModBlocks.PEPPER_CROP,
                ModBlocks.PAPAYA,
                ModBlocks.PAPAYA_SAPLING,
                ModBlocks.CRAB_EGG,
                ModBlocks.PAPAYA_CROPS);

        HudRenderCallback.EVENT.register(ModHudRenderer::init);

        ClientPlayNetworking.registerGlobalReceiver(ThaiDelight.SPICY_PAYLOAD_ID,(minecraft, clientPacketListener, friendlyByteBuf, packetSender) -> {
            int spicyLevel = friendlyByteBuf.readInt();
            minecraft.execute(() ->{
                if(minecraft.player != null){
                    ((SpicyData.SpicyAccessor)minecraft.player).muffinsThaiDelight$access().spicyLevel = spicyLevel;
                }
            });
        });
    }
}
