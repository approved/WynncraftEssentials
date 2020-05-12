package dev.odd.wynncraftessentials.mixins.modules.utils;

import java.util.regex.Pattern;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import dev.odd.wynncraftessentials.WynncraftEssentials;
import dev.odd.wynncraftessentials.utils.ClientUtils;
import dev.odd.wynncraftessentials.utils.SocialUtils;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.ChatMessageS2CPacket;
import net.minecraft.text.Text;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ChatMixin {

    Pattern friendsListPattern = Pattern.compile("^[a-zA-Z0-9_-]+'(s)? friends \\(\\d+\\):.*");
    Pattern partyPattern = Pattern.compile("^.*Party list:.*");

    @Inject(at = @At("HEAD"), method = "onChatMessage", cancellable = true)
    public void onChatMessageRecieved(ChatMessageS2CPacket packet, CallbackInfo ci) {
        if (ClientUtils.connected) {
            Text message = packet.getMessage();
            switch (packet.getLocation()) {
                case SYSTEM: {
                    /**
                     * Wynncraft uses siblings for server sent messages. Messages without siblings
                     * are used for notifying the client to input information.
                     * 
                     * If this observation is deemed untrue. Logic changes may need to happen.
                     */
                    if (message.getSiblings().size() == 0)
                        return;
                    String strMessage = message.getSiblings().get(0).asString();

                    WynncraftEssentials.WCLogger
                            .info("Recieved chat message: " + message.getSiblings().get(0).asString());

                    /**
                     * Matches against a server response to the friends/party list message to obtain
                     * members of each group
                     */

                    if (friendsListPattern.matcher(strMessage).matches()) {
                        WynncraftEssentials.WCLogger.info("Matched friends list");
                        String[] friends = message.getSiblings().get(1).asString().split(",");
                        for (String friend : friends) {
                            friend = friend.toLowerCase().trim();
                            if (!SocialUtils.friendsList.contains(friend)) {
                                SocialUtils.friendsList.add(friend);
                            }
                        }
                        ci.cancel();
                    }

                    if (partyPattern.matcher(strMessage).matches()) {
                        WynncraftEssentials.WCLogger.info("Matched party list");
                        String[] partyMembers = message.getSiblings().get(1).asString().split(",");
                        for (String member : partyMembers) {
                            member = member.toLowerCase().trim();
                            if (!SocialUtils.partyList.contains(member)) {
                                SocialUtils.partyList.add(member);
                            }
                        }
                        ci.cancel();
                    }
                    break;
                }

                default:
                    break;
            }
        }
    }
}