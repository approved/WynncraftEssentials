package dev.odd.wynncraftessentials.mixins.modules.network;

import net.minecraft.client.network.ServerAddress;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import dev.odd.wynncraftessentials.utils.ClientUtils;
import net.minecraft.client.MinecraftClient;

import static dev.odd.wynncraftessentials.WynncraftEssentials.WCLogger;


@Mixin(targets = "net/minecraft/client/gui/screen/ConnectScreen$1")
public abstract class ServerConnectionMixin {
    
    @Shadow
    @Final
    private ServerAddress field_33737;

    @Inject(
            method = "run()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/network/ClientConnection;send(Lnet/minecraft/network/Packet;)V",
                    ordinal = 1,
                    shift = Shift.AFTER
            )
    )
    public void onServerConnected(final CallbackInfo ci) {
        String address = field_33737.getAddress();
        WCLogger.info(String.format("Connected to %s:%s", address, field_33737.getPort()));
        if (address.toLowerCase().contains("wynncraft")) {
            ClientUtils.connected = true;
        }
    }
}
