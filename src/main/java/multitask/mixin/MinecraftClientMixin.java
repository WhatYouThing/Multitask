package multitask.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Shadow
    static MinecraftClient instance;

    @ModifyExpressionValue(method = "doItemUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerInteractionManager;isBreakingBlock()Z"))
    private boolean useIsBreakingBlock(boolean original) {
        return false;
    }

    @ModifyExpressionValue(method = "doItemUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isRiding()Z"))
    private boolean useIsRiding(boolean original) {
        return false;
    }

    @ModifyExpressionValue(method = "doAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isRiding()Z"))
    private boolean attackIsRiding(boolean original) {
        return false;
    }

    @ModifyExpressionValue(method = "handleBlockBreaking", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z"))
    private boolean handleBreakIsUsing(boolean original) {
        return false;
    }

    @ModifyExpressionValue(method = "handleInputEvents", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z", ordinal = 0))
    private boolean handleInputIsUsing(boolean original) {
        if (original && !instance.options.useKey.isPressed()) {
            instance.interactionManager.stopUsingItem(instance.player);
        }
        return false;
    }
}