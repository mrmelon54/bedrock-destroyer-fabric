package net.onpointcoding.bedrockdestroyer.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.StorageMinecartEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.onpointcoding.bedrockdestroyer.BedrockDestroyer;
import net.onpointcoding.bedrockdestroyer.IMinecartEntitySize;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StorageMinecartEntity.class)
public abstract class MixinStorageMinecartEntity implements IMinecartEntitySize {
    @Shadow
    public abstract ItemStack getStack(int slot);

    @Shadow
    public abstract void setStack(int slot, ItemStack stack);

    @Inject(method = "interact", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;openHandledScreen(Lnet/minecraft/screen/NamedScreenHandlerFactory;)Ljava/util/OptionalInt;"))
    public void wrapInteract(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (BedrockDestroyer.getInstance().shouldIgnorePlayer(player)) return;

        for (int i = 0; i < getSize(); i++) {
            ItemStack stack = getStack(i);
            if (BedrockDestroyer.getInstance().shouldDestroyItemStack(stack)) {
                setStack(i, BedrockDestroyer.getInstance().createEmptyStack());
            }
        }
    }
}
