package dev.odd.wynncraftessentials;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dev.odd.wynncraftessentials.modules.client.ClientKeybinds;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.minecraft.client.MinecraftClient;

public class WynncraftEssentials implements ClientModInitializer {
	public static Logger WCLogger = LogManager.getFormatterLogger("Wynncraft-Essentials");

	@Override
	public void onInitializeClient() {
		/**
		 * Register Keybinds
		 */
		ClientKeybinds.Register();

		/**
		 * Register Callbacks
		 */
		ClientTickCallback.EVENT.register(this::onTick);
		HudRenderCallback.EVENT.register(this::onRenderHud);

		WCLogger.info("Wynncraft-Essentials loaded");
	}

	public void onTick(MinecraftClient client) {
		ClientKeybinds.Process();
	}

	public void onRenderHud(float delta) {
		//TODO: Create custom overlays for questbook and character sheet
	}
}
