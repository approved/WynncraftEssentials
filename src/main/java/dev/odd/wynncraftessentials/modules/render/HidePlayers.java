package dev.odd.wynncraftessentials.modules.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.TranslatableText;

public class HidePlayers {

    public enum PlayerRenderMode {
        FRIENDS{
            @Override
            public PlayerRenderMode previous() {
                return NONE;
            }
        },
        PARTY,
        ALL,
        NONE {
            @Override
            public PlayerRenderMode next() {
                return FRIENDS;
            };
        };

        public PlayerRenderMode next() {
            return values()[ordinal() + 1];
        }

        public PlayerRenderMode previous() {
            return values()[ordinal() -1];
        }
    }

    private static PlayerRenderMode currentRenderMode = PlayerRenderMode.NONE;
    
    public static PlayerRenderMode getRenderMode() {
        return currentRenderMode;
    }

    public static PlayerRenderMode nextRenderMode() {
        currentRenderMode = currentRenderMode.next();
        MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(new TranslatableText("wcessentials.prefix").append(" ").append(new TranslatableText("wcessentials.rendermode_cycle", HidePlayers.getRenderMode())));
        return currentRenderMode;
    }

    public static PlayerRenderMode previousRenderMode() {
        currentRenderMode = currentRenderMode.previous();
        MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(new TranslatableText("wcessentials.prefix").append(" ").append(new TranslatableText("wcessentials.rendermode_cycle", HidePlayers.getRenderMode())));
        return currentRenderMode;
    }
}