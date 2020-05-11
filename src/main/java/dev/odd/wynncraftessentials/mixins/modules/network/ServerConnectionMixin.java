package dev.odd.wynncraftessentials.mixins.modules.network;

import org.apache.logging.log4j.LogManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import dev.odd.wynncraftessentials.utils.ClientUtils;
import net.minecraft.client.MinecraftClient;


@Mixin(targets = "net/minecraft/client/gui/screen/ConnectScreen$1")
public abstract class ServerConnectionMixin {
    
    @Shadow
    @Final
    private String field_2414;

    @Shadow
    @Final
    private int field_2415;

    @Inject(
            method = "run",
            at = @At(
                    value = "INVOKE",
                    target = "net/minecraft/network/ClientConnection.send(Lnet/minecraft/network/Packet;)V",
                    ordinal = 1,
                    shift = Shift.AFTER
            )
    )
    public void onServerConnected(final CallbackInfo ci) {
        LogManager.getLogger().info("Connected to {}, {}", field_2414, field_2415);
        if (field_2414.toLowerCase().contains("wynncraft")) {
            ClientUtils.connected = true;
            ClientUtils.playerName = MinecraftClient.getInstance().player.getEntityName();
        }
    }
}
