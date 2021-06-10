package dev.odd.wynncraftessentials.modules.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;
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

    public static void nextRenderMode() {
        currentRenderMode = currentRenderMode.next();
        addCycleChatMessage();
    }

    public static void previousRenderMode() {
        currentRenderMode = currentRenderMode.previous();
        addCycleChatMessage();
    }

    private static void addCycleChatMessage() {
        MinecraftClient.getInstance()
                .inGameHud.getChatHud()
                .addMessage(new LiteralText("[")
                        .append(new TranslatableText("wcessentials.prefix"))
                        .append("]")
                        .append(" ")
                        .append(new TranslatableText("wcessentials.rendermode.cycle", HidePlayers.getRenderMode()))
                );
    }
}