package dev.odd.wynncraftessentials.utils;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.client.MinecraftClient;

public class SocialUtils {

    public static Set<String> friendsList = new HashSet<String>();
    public static Set<String> partyList = new HashSet<String>();

    public static void GetFriends() {
        MinecraftClient.getInstance().player.sendChatMessage("/friends list");
    }

    public static void GetParty() {
        MinecraftClient.getInstance().player.sendChatMessage("/party list");
    }
}