package net.firemuffin303.muffinsthaidelightfabric.mixin;

import net.firemuffin303.muffinsthaidelightfabric.ThaiDelight;
import net.firemuffin303.muffinsthaidelightfabric.common.entitydata.SpicyData;
import net.firemuffin303.muffinsthaidelightfabric.registry.ModTags;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(Item.class)
public abstract class ItemMixin {
    @Shadow @Final private static Logger LOGGER;

    @Inject(method = "appendHoverText", at = @At("HEAD"))
    public void muffinsThaiDelight$appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag, CallbackInfo ci){
        CompoundTag compoundTag = itemStack.getTag();
        if(compoundTag != null && compoundTag.contains(ThaiDelight.TASTY_NBT) && compoundTag.getBoolean(ThaiDelight.TASTY_NBT)){
            list.add(Component.translatable("item.muffins_thaidelight.tasty").append(CommonComponents.SPACE).withStyle(ChatFormatting.GOLD));
        }
    }
}
