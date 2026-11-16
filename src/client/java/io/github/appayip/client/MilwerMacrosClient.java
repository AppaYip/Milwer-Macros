package io.github.appayip.client;

import io.github.appayip.client.characters.CharacterLookup;
import io.github.appayip.client.gui.GuiSolver;
import io.github.appayip.client.keymapping.ModKeyMappings;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenKeyboardEvents;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;

public class MilwerMacrosClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ModKeyMappings.register();

		CharacterLookup.register();

		ScreenEvents.BEFORE_INIT.register((client, screen, _, _) -> {
			if (!(screen instanceof AbstractContainerScreen<?>))
				return;

			ScreenKeyboardEvents.beforeKeyPress(screen).register((_,keyEvent) -> {
				if (!ModKeyMappings.GUI_SOLVER.matches(keyEvent))
					return;

				GuiSolver.execute(client);
			});
		});

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (ModKeyMappings.CHARACTER_MACRO.consumeClick()) {
				if (client.player == null) continue;

				CharacterLookup.find(client);
			}
		});
	}
}