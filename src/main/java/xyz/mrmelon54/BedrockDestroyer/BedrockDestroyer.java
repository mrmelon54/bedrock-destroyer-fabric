package xyz.mrmelon54.BedrockDestroyer;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;

public class BedrockDestroyer implements ModInitializer {
    private static BedrockDestroyer instance;

    @Override
    public void onInitialize() {
        instance = this;

        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            // Overrides non-spectator and non-creative players to always fail placing a block if its bedrock
            if (player.isSpectator() || instance.shouldIgnorePlayer(player)) return ActionResult.PASS;
            if (instance.shouldDestroyItemStack(player.getStackInHand(hand))) {
                player.setStackInHand(hand, createEmptyStack());
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        });
    }

    public static BedrockDestroyer getInstance() {
        return instance;
    }

    public boolean shouldDestroyItemStack(ItemStack stack) {
        return stack.getItem() == Items.BEDROCK;
    }

    public boolean shouldIgnorePlayer(PlayerEntity player) {
        return player.isCreative();
    }

    public ItemStack createEmptyStack() {
        return new ItemStack(Items.AIR);
    }
}
