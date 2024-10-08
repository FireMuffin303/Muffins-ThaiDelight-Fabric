package net.firemuffin303.muffinsthaidelightfabric.mixin;

import net.firemuffin303.muffinsthaidelightfabric.registry.ModMobEffects;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerEntityMixin {

    @Inject(method = "canEat",at= @At("TAIL"), cancellable = true)
    public void muffinsThaiDelight$canEat(boolean bl, CallbackInfoReturnable<Boolean> cir){
        if(((Player)(Object)this).hasEffect(ModMobEffects.GLUTTONY)){
            cir.setReturnValue(true);
        }

    }
}
