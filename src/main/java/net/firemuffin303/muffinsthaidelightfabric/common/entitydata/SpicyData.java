package net.firemuffin303.muffinsthaidelightfabric.common.entitydata;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.firemuffin303.muffinsthaidelightfabric.ThaiDelight;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class SpicyData {
    public final String SPICY_NBT = "spicy_level";
    public final int MAX_SPICY  = 100;
    public final int MAX_SPICY_COOLDOWN_TICK = 15*20;
    public int spicyLevel = 0;
    private int lastSentLevel = 0;
    public int spicy_cooldown_tick = 0;

    private final LivingEntity livingEntity;

    public SpicyData(LivingEntity livingEntity){
        this.livingEntity = livingEntity;
    }

    public void mobTick(){
        if(!this.livingEntity.level().isClientSide){

            int timeTick = livingEntity.hasEffect(MobEffects.FIRE_RESISTANCE) ? 1 : 10;

            if(this.livingEntity.tickCount % timeTick == 0){
                this.addSpicyLevel(-1);
                //livingEntity.level().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE,livingEntity.getEyePosition().x(),livingEntity.getEyePosition().y() +1.0,livingEntity.getEyePosition().z(),0.0,0.0,0.0);
            }

            if(this.spicyLevel != this.lastSentLevel && this.livingEntity instanceof ServerPlayer serverPlayer){
                FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
                buf.writeInt(this.spicyLevel);
                ServerPlayNetworking.send(serverPlayer, ThaiDelight.SPICY_PAYLOAD_ID,buf);
                this.lastSentLevel = this.spicyLevel;
            }
        }
    }

    public void reset(){
        this.spicyLevel = 0;
        this.spicy_cooldown_tick = 0;
    }

    public void setSpicyLevel(int value){
        this.spicyLevel = Mth.clamp(value,0,MAX_SPICY);
    }

    public void addSpicyLevel(int value){
        this.spicyLevel = Mth.clamp(this.spicyLevel+value,0,MAX_SPICY);
    }

    public void setSpicyCooldownLevel(int value){
        this.spicy_cooldown_tick = Mth.clamp(value,0,MAX_SPICY_COOLDOWN_TICK);
    }

    public float getPercent(){
        return (float) Math.min(this.spicyLevel, this.MAX_SPICY) /this.MAX_SPICY;
    }

    //---- NBT TAG ---
    public void load(CompoundTag compoundTag){
        if(compoundTag.contains(SPICY_NBT)){
            this.spicyLevel = compoundTag.getInt(SPICY_NBT);
        }
    }

    public void save(CompoundTag compoundTag){
        compoundTag.putInt(SPICY_NBT,this.spicyLevel);
    }
    // -------------


    public int getSpicyLevel() {
        return spicyLevel;
    }

    public interface SpicyAccessor{
        SpicyData muffinsThaiDelight$access();
    }
}
