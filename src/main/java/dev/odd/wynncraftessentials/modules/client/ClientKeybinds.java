package dev.odd.wynncraftessentials.modules.client;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.player.PlayerInventory;
import org.lwjgl.glfw.GLFW;

import dev.odd.wynncraftessentials.modules.render.HidePlayers;
import dev.odd.wynncraftessentials.utils.ClientUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;

public class ClientKeybinds {

	private static final String GlobalCategory = "key.categories.wcessentials";
	private static final String RenderCategory = "key.categories.wcessentials.render";

	public static KeyBinding nextPlayerRenderMode;
	public static KeyBinding lastPlayerRenderMode;
	public static KeyBinding skillOne;
	public static KeyBinding skillTwo;
	public static KeyBinding skillThree;
	public static KeyBinding skillFour;
	public static KeyBinding characterSheet;
	public static KeyBinding questbook;
	public static KeyBinding autowalk;

	public static void Register() {
		nextPlayerRenderMode = KeyBindingHelper.registerKeyBinding(
				new KeyBinding(
						"key.wcessentials.nextplayerrendermode",
						InputUtil.Type.KEYSYM,
						GLFW.GLFW_KEY_RIGHT_BRACKET,
						RenderCategory
				));

		lastPlayerRenderMode = KeyBindingHelper.registerKeyBinding(
				new KeyBinding(
						"key.wcessentials.lastplayerrendermode",
						InputUtil.Type.KEYSYM,
						GLFW.GLFW_KEY_LEFT_BRACKET,
						RenderCategory
				));

		skillOne = KeyBindingHelper.registerKeyBinding(
				new KeyBinding(
						"key.wcessentials.skillone",
						InputUtil.Type.KEYSYM,
						InputUtil.UNKNOWN_KEY.getCode(),
						GlobalCategory
				));

		skillTwo = KeyBindingHelper.registerKeyBinding(
				new KeyBinding(
						"key.wcessentials.skilltwo",
						InputUtil.Type.KEYSYM,
						InputUtil.UNKNOWN_KEY.getCode(),
						GlobalCategory
				));

		skillThree = KeyBindingHelper.registerKeyBinding(
				new KeyBinding(
						"key.wcessentials.skillthree",
						InputUtil.Type.KEYSYM,
						InputUtil.UNKNOWN_KEY.getCode(),
						GlobalCategory
				));

		skillFour = KeyBindingHelper.registerKeyBinding(
				new KeyBinding(
						"key.wcessentials.skillfour",
						InputUtil.Type.KEYSYM,
						InputUtil.UNKNOWN_KEY.getCode(),
						GlobalCategory
				));

		characterSheet = KeyBindingHelper.registerKeyBinding(
				new KeyBinding(
						"key.wcessentials.charactersheet",
						InputUtil.Type.KEYSYM,
						GLFW.GLFW_KEY_C,
						GlobalCategory
				));

		questbook = KeyBindingHelper.registerKeyBinding(
				new KeyBinding(
						"key.wcessentials.questbook",
						InputUtil.Type.KEYSYM,
						GLFW.GLFW_KEY_H,
						GlobalCategory
				));

		autowalk = KeyBindingHelper.registerKeyBinding(
				new KeyBinding(
						"key.wcessentials.autowalk",
						InputUtil.Type.KEYSYM,
						GLFW.GLFW_KEY_B,
						GlobalCategory
				));
	}

	public static void Process() {
		/*
		 * Execute Changing Player Render Mode
		 */
		if (ClientKeybinds.nextPlayerRenderMode.wasPressed()) {
			HidePlayers.nextRenderMode();
		}

		if (ClientKeybinds.lastPlayerRenderMode.wasPressed()) {
			HidePlayers.previousRenderMode();
		}

		if (ClientKeybinds.questbook.wasPressed()) {
			swapSelectInventorySlot(7);
		}

		if (ClientKeybinds.characterSheet.wasPressed()) {
			swapSelectInventorySlot(6);
		}

		if (ClientKeybinds.autowalk.wasPressed()) {
			ClientUtils.autoWalk = !ClientUtils.autoWalk;
		}
	}

	private static void swapSelectInventorySlot(int desiredSlot) {
		MinecraftClient client = MinecraftClient.getInstance();
		if (client.player != null) {
			assert client.interactionManager != null;
			PlayerInventory inventory = client.player.getInventory();
			int lastSlot = inventory.selectedSlot;
			inventory.selectedSlot = desiredSlot;
			client.interactionManager.interactItem(client.player, client.world, Hand.MAIN_HAND);
			inventory.selectedSlot = lastSlot;
		}
	}
}