package net.firemuffin303.muffinsthaidelightfabric;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.firemuffin303.muffinsthaidelightfabric.common.entitydata.SpicyData;
import net.firemuffin303.muffinsthaidelightfabric.registry.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThaiDelight implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger(ThaiDelight.MOD_ID);
    public static final String MOD_ID = "muffins_thaidelight";

    public static final ResourceLocation SPICY_PAYLOAD_ID = new ResourceLocation(MOD_ID,"spicypayload");

    public static final CreativeModeTab MOD_TAB = FabricItemGroup.builder()
            .title(Component.translatable("itemGroup."+ThaiDelight.MOD_ID+".main"))
            .icon(() -> new ItemStack(ModBlocks.MORTAR))
            .displayItems((itemDisplayParameters, output) -> {
                ModItems.ITEMS.forEach(output::accept);
            })
            .build();
    @Override
    public void onInitialize() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB,new ResourceLocation(MOD_ID,"main"),MOD_TAB);
        init();

        ServerPlayConnectionEvents.JOIN.register((serverGamePacketListener, packetSender, minecraftServer) -> minecraftServer.execute(() -> {
            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
            buf.writeInt(((SpicyData.SpicyAccessor)serverGamePacketListener.player).muffinsThaiDelight$access().spicyLevel);
            ServerPlayNetworking.send(serverGamePacketListener.player,ThaiDelight.SPICY_PAYLOAD_ID,buf);
        }));
    }

    private void init(){
        ModEntityTypes.init();
        ModBlocks.init();
        ModItems.init();
        ModRecipes.init();
        ModRecipes.ModSerializer.init();
        ModFeatures.init();
        ModMenuType.init();
        ModTreeDecoratorTypes.init();
    }
}
