package dev.odd.wynncraftessentials.mixins.modules.network;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import dev.odd.wynncraftessentials.WynncraftEssentials;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.Packet;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class PacketListener {

    @Inject(at = @At("HEAD"), method = "sendPacket")
    public void onSendPacket(Packet<?> packet, CallbackInfo ci) {
        WynncraftEssentials.WCLogger.debug("Sending Packet: " + packet.getClass());
    }
}