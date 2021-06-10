package dev.odd.wynncraftessentials;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.client.util.math.MatrixStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dev.odd.wynncraftessentials.modules.client.ClientKeybinds;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;

public class WynncraftEssentials implements ClientModInitializer {
	public static Logger WCLogger = LogManager.getFormatterLogger("Wynncraft-Essentials");

	@Override
	public void onInitializeClient() {
		/*
		* Register Key-binds
		 */
		ClientKeybinds.Register();

		/*
		 * Register Callbacks
		 */
		ClientTickEvents.START_CLIENT_TICK.register(this::onTick);
		HudRenderCallback.EVENT.register(this::onRenderHud);

		WCLogger.info("Wynncraft-Essentials loaded");
	}

	public void onTick(MinecraftClient client) {
		ClientKeybinds.Process();
	}

	public void onRenderHud(MatrixStack matrixStack, float delta) {
		//TODO: Create custom overlays for questbook and character sheet
	}
}
