package dev.odd.wynncraftessentials.mixins.modules.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import dev.odd.wynncraftessentials.utils.ClientUtils;
import net.minecraft.client.input.KeyboardInput;

@Mixin(KeyboardInput.class)
public abstract class AutowalkMixin {
    @Inject(at = @At("TAIL"), method = "tick")
    public void onTick(boolean sprinting, CallbackInfo ci) {
		if(ClientUtils.autoWalk) {
            KeyboardInput input = ((KeyboardInput)(Object)this);
            input.pressingForward = true;
            input.movementForward = input.pressingForward == input.pressingBack ? 0.0F : (input.pressingForward ? 1.0F : -1.0F);
            if (sprinting) {
                input.movementForward = (float)((double)input.movementForward * 0.3D);
            }
		}
    }
}   