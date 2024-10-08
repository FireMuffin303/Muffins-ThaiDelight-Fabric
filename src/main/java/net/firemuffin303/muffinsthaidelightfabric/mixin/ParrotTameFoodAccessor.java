package net.firemuffin303.muffinsthaidelightfabric.mixin;

import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Set;

@Mixin(Parrot.class)
public interface ParrotTameFoodAccessor {
    @Accessor("TAME_FOOD")
    static Set<Item> getTameFood(){
        throw new AssertionError();
    }

}
