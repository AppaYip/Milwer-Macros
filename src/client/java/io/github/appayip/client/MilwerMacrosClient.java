package io.github.appayip.client;

import io.github.appayip.client.keymapping.ModKeyMappings;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenKeyboardEvents;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;

public class MilwerMacrosClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ModKeyMappings.register();

		ScreenEvents.BEFORE_INIT.register((client, screen, _, _) -> {
			if (!(screen instanceof AbstractContainerScreen<?>))
				return;

			ScreenKeyboardEvents.beforeKeyPress(screen).register((_,keyEvent) -> {
				if (!ModKeyMappings.GUI_SOLVER.matches(keyEvent))
					return;

				GuiSolver.execute(client);
			});
		});
	}
}