package net.firemuffin303.muffinsthaidelightfabric.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.firemuffin303.muffinsthaidelightfabric.common.entitydata.SpicyData;
import net.firemuffin303.muffinsthaidelightfabric.registry.ModTags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.CombatTracker;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin implements SpicyData.SpicyAccessor {
    @Shadow
    @Final
    private CombatTracker combatTracker;
    @Unique
    public final SpicyData muffins_thaidelight$spicyData = new SpicyData(((LivingEntity)(Object)this));

    @Inject(method = "aiStep",at = @At("HEAD"),remap = false)
    public void muffins_thaidelight$aiStep(CallbackInfo ci){
        muffins_thaidelight$spicyData.mobTick();
    }

    @Inject(method = "canFreeze",at = @At("HEAD"), cancellable = true)
    public void muffins_thaidelight$canFreeze(CallbackInfoReturnable<Boolean> cir){
        if(this.muffins_thaidelight$spicyData.getSpicyLevel() > 0){
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "readAdditionalSaveData",at = @At("TAIL"),remap = false)
    public void muffins_thaidelight$load(CompoundTag compoundTag, CallbackInfo ci){
        muffins_thaidelight$spicyData.load(compoundTag);
    }

    @Inject(method = "addAdditionalSaveData",at = @At("TAIL"),remap = false)
    public void muffins_thaidelight$save(CompoundTag compoundTag, CallbackInfo ci){
        muffins_thaidelight$spicyData.save(compoundTag);
    }

    @Inject(method = "eat", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;addEatEffect(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;)V"),remap = false)
    public void muffins_thaidelight$eat(Level level, ItemStack itemStack, CallbackInfoReturnable<ItemStack> cir){
        if(itemStack.is(ModTags.SPICY_LEVEL_5)){
            this.muffins_thaidelight$spicyData.addSpicyLevel(5);
            this.muffins_thaidelight$spicyData.setSpicyCooldownLevel(15*20);
        }else if(itemStack.is(ModTags.SPICY_LEVEL_10)){
            this.muffins_thaidelight$spicyData.addSpicyLevel(10);
            this.muffins_thaidelight$spicyData.setSpicyCooldownLevel(15*20);
        } else if (itemStack.is(ModTags.SPICY_LEVEL_50)) {
            this.muffins_thaidelight$spicyData.addSpicyLevel(50);
            this.muffins_thaidelight$spicyData.setSpicyCooldownLevel(15*20);
        } else if (itemStack.is(Items.MILK_BUCKET)) {
            this.muffins_thaidelight$spicyData.reset();
        }
    }

    @Inject(method = "hurt",at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/WalkAnimationState;setSpeed(F)V"),remap = false)
    public void muffins_thaidelight$hurt(DamageSource damageSource, float f, CallbackInfoReturnable<Boolean> cir,@Local(ordinal = 1) LocalFloatRef localRef){

        if(damageSource.is(DamageTypeTags.IS_FIRE) && this.muffins_thaidelight$spicyData.spicyLevel > 0){
            localRef.set(f*15.0f);
        }
    }

    @Override
    public SpicyData muffinsThaiDelight$access() {
        return this.muffins_thaidelight$spicyData;
    }
}
