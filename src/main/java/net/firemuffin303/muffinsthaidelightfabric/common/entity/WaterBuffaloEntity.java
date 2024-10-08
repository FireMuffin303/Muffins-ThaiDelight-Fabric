package net.firemuffin303.muffinsthaidelightfabric.common.entity;

import net.firemuffin303.muffinsthaidelightfabric.registry.ModEntityTypes;
import net.firemuffin303.muffinsthaidelightfabric.registry.ModTags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WaterBuffaloEntity extends Animal implements ContainerListener,Saddleable,PlayerRideableJumping {
    protected SimpleContainer inventory;
    private final int INVENTORY_SIZE = 2;
    protected float playerJumpPendingScale;

    public WaterBuffaloEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1.0f);

        SimpleContainer simpleContainer = this.inventory;
        this.inventory = new SimpleContainer(INVENTORY_SIZE);
        if(simpleContainer != null){
            simpleContainer.removeListener(this);
            int i = Math.min(simpleContainer.getContainerSize(), this.inventory.getContainerSize());

            for(int j = 0; j < i; ++j) {
                ItemStack itemStack = simpleContainer.getItem(j);
                if (!itemStack.isEmpty()) {
                    this.inventory.setItem(j, itemStack.copy());
                }
            }
        }

        this.inventory.addListener(this);


    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.0));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25, Ingredient.of(ModTags.WATER_BUFFALO_FOOD), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0).add(Attributes.MOVEMENT_SPEED, 0.20000000298023224);
    }

    public InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (itemStack.is(Items.BUCKET) && !this.isBaby()) {
            player.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
            ItemStack itemStack2 = ItemUtils.createFilledResult(itemStack, player, Items.MILK_BUCKET.getDefaultInstance());
            player.setItemInHand(interactionHand, itemStack2);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else {
            return super.mobInteract(player, interactionHand);
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        if(!this.inventory.getItem(0).isEmpty()){
            compoundTag.put("SaddleItem",this.inventory.getItem(0).save(new CompoundTag()));
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        if (compoundTag.contains("SaddleItem", 10)) {
            ItemStack itemStack = ItemStack.of(compoundTag.getCompound("SaddleItem"));
            if (itemStack.is(Items.SADDLE)) {
                this.inventory.setItem(0, itemStack);
            }
        }
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    @Override
    protected void tickRidden(Player player, Vec3 vec3) {
        super.tickRidden(player, vec3);
    }

    @Override
    protected Vec3 getRiddenInput(Player player, Vec3 vec3) {
        if (this.onGround() && this.playerJumpPendingScale == 0.0F) {
            return Vec3.ZERO;
        } else {
            float f = player.xxa * 0.5F;
            float g = player.zza;
            if (g <= 0.0F) {
                g *= 0.25F;
            }

            return new Vec3(f, 0.0, g);
        }
    }

    @Override
    @Nullable
    public LivingEntity getControllingPassenger() {
        Entity var3 = this.getFirstPassenger();
        if (var3 instanceof Mob mob) {
            return mob;
        } else {
            if (this.isSaddled()) {
                var3 = this.getFirstPassenger();
                if (var3 instanceof Player player) {
                    return player;
                }
            }

            return null;
        }
    }

    //---- Saddle ----
    @Override
    public boolean isSaddleable() {
        return this.isAlive() && !this.isBaby();
    }

    @Override
    public void equipSaddle(@Nullable SoundSource soundSource) {
        this.inventory.setItem(0,new ItemStack(Items.SADDLE));
    }

    @Override
    public boolean isSaddled() {
        return false;
    }

    //---- Jumping ----
    @Override
    public void onPlayerJump(int i) {

    }

    @Override
    public boolean canJump() {
        return false;
    }

    @Override
    public void handleStartJump(int i) {

    }

    @Override
    public void handleStopJump() {

    }

    //--- Container Changed---
    @Override
    public void containerChanged(@NotNull Container container) {
        boolean bl = this.isSaddled();
        if (this.tickCount > 20 && !bl && this.isSaddled()) {
            this.playSound(this.getSaddleSoundEvent(), 0.5F, 1.0F);
        }
    }
}
