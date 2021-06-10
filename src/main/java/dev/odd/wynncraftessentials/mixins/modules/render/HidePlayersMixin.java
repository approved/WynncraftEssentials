package dev.odd.wynncraftessentials.mixins.modules.render;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import dev.odd.wynncraftessentials.modules.render.HidePlayers;
import dev.odd.wynncraftessentials.utils.ClientUtils;
import dev.odd.wynncraftessentials.utils.SocialUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;

@Mixin(PlayerEntityRenderer.class)
public abstract class HidePlayersMixin {

    /*
     * While in multiplayer, does not render players. The scoreboard check is to
     * prevent any "natural" PlayerEntity from being unrendered. The EntityName
     * check is to prevent npcs using player models from being unrendered.
     */
    @Inject(at = @At("HEAD"), method = "render", cancellable = true)
    public void OnRender(AbstractClientPlayerEntity playerEntity, float f, float g, MatrixStack matrixStack,
                         VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {

        MinecraftClient client = MinecraftClient.getInstance();

        //TODO: Disable when outside of a town
        if (!client.isInSingleplayer()
                && ClientUtils.connected
                && playerEntity.getScoreboard() != null
                && !playerEntity.getEntityName().startsWith("§")
                && playerEntity != client.player) {
            switch (HidePlayers.getRenderMode()) {
                case ALL:
                    return;

                case FRIENDS:
                    if (SocialUtils.friendsList.contains(playerEntity.getEntityName().toLowerCase())) {
                        return;
                    }
                    ci.cancel();
                    break;

                case PARTY:
                    if (SocialUtils.partyList.contains(playerEntity.getEntityName().toLowerCase().trim())) {
                        return;
                    }
                    ci.cancel();
                    break;

                case NONE:
                default:
                    ci.cancel();
            }
        }
    }
}