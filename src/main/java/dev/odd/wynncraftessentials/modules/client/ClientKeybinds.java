package dev.odd.wynncraftessentials.modules.client;

import org.lwjgl.glfw.GLFW;

import dev.odd.wynncraftessentials.modules.render.HidePlayers;
import net.fabricmc.fabric.api.client.keybinding.FabricKeyBinding;
import net.fabricmc.fabric.api.client.keybinding.KeyBindingRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;

public class ClientKeybinds {

	private static final String GlobalCategory = "key.categories.wcessentials";
	private static final String RenderCategory = "key.categories.wcessentials.render";

	public static final FabricKeyBinding nextPlayerRenderMode = FabricKeyBinding.Builder
			.create(new Identifier("wcessentials", "nextplayerrendermode"), InputUtil.Type.KEYSYM,
					GLFW.GLFW_KEY_RIGHT_BRACKET, RenderCategory)
			.build();

	public static final FabricKeyBinding lastPlayerRenderMode = FabricKeyBinding.Builder
			.create(new Identifier("wcessentials", "lastplayerrendermode"), InputUtil.Type.KEYSYM,
					GLFW.GLFW_KEY_LEFT_BRACKET, RenderCategory)
			.build();

	public static final FabricKeyBinding skillOne = FabricKeyBinding.Builder
			.create(new Identifier("wcessentials", "skillone"), InputUtil.Type.KEYSYM,
					InputUtil.UNKNOWN_KEYCODE.getKeyCode(), GlobalCategory)
			.build();

	public static final FabricKeyBinding skillTwo = FabricKeyBinding.Builder
			.create(new Identifier("wcessentials", "skilltwo"), InputUtil.Type.KEYSYM,
					InputUtil.UNKNOWN_KEYCODE.getKeyCode(), GlobalCategory)
			.build();

	public static final FabricKeyBinding skillThree = FabricKeyBinding.Builder
			.create(new Identifier("wcessentials", "skillthree"), InputUtil.Type.KEYSYM,
					InputUtil.UNKNOWN_KEYCODE.getKeyCode(), GlobalCategory)
			.build();

	public static final FabricKeyBinding skillFour = FabricKeyBinding.Builder
			.create(new Identifier("wcessentials", "skillfour"), InputUtil.Type.KEYSYM,
					InputUtil.UNKNOWN_KEYCODE.getKeyCode(), GlobalCategory)
			.build();

	public static final FabricKeyBinding characterSheet = FabricKeyBinding.Builder
			.create(new Identifier("wcessentials", "charactersheet"), InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_C,
					GlobalCategory)
			.build();

	public static final FabricKeyBinding questbook = FabricKeyBinding.Builder
			.create(new Identifier("wcessentials", "questbook"), InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_H, GlobalCategory)
			.build();

	public static void Register() {
		KeyBindingRegistry.INSTANCE.addCategory(GlobalCategory);
		KeyBindingRegistry.INSTANCE.addCategory(RenderCategory);

		// Hide Player Mode
		KeyBindingRegistry.INSTANCE.register(ClientKeybinds.nextPlayerRenderMode);
		KeyBindingRegistry.INSTANCE.register(ClientKeybinds.lastPlayerRenderMode);

		// Rebind Skills
		KeyBindingRegistry.INSTANCE.register(ClientKeybinds.skillOne);
		KeyBindingRegistry.INSTANCE.register(ClientKeybinds.skillTwo);
		KeyBindingRegistry.INSTANCE.register(ClientKeybinds.skillThree);
		KeyBindingRegistry.INSTANCE.register(ClientKeybinds.skillFour);

		// Questbook and Character Sheet Hotkey
		KeyBindingRegistry.INSTANCE.register(ClientKeybinds.questbook);
		KeyBindingRegistry.INSTANCE.register(ClientKeybinds.characterSheet);
	}

	public static void Process() {
		/**
		 * Execute Changing Player Render Mode
		 */
		if (nextPlayerRenderMode.wasPressed()) {
			HidePlayers.nextRenderMode();
		}

		if (lastPlayerRenderMode.wasPressed()) {
			HidePlayers.previousRenderMode();
		}

		if (questbook.wasPressed()) {
			MinecraftClient client = MinecraftClient.getInstance();
			int lastSlot = client.player.inventory.selectedSlot;
			client.player.inventory.selectedSlot = 7;
			client.interactionManager.interactItem(client.player, client.world.getWorld(), Hand.MAIN_HAND);
			client.player.inventory.selectedSlot = lastSlot;
		}

		if (characterSheet.wasPressed()) {
			MinecraftClient client = MinecraftClient.getInstance();
			int lastSlot = client.player.inventory.selectedSlot;
			client.player.inventory.selectedSlot = 6;
			client.interactionManager.interactItem(client.player, client.world.getWorld(), Hand.MAIN_HAND);
			client.player.inventory.selectedSlot = lastSlot;
		}
	}
}