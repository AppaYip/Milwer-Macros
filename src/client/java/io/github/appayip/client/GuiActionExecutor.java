package io.github.appayip.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.inventory.AbstractContainerMenu;

public final class GuiActionExecutor {
    public static void execute(
            Minecraft client,
            AbstractContainerMenu menu,
            int slot,
            GuiAction guiAction
    ) {
        if (client.player == null || client.gameMode == null) return;


        client.gameMode.handleContainerInput(
                menu.containerId,
                slot,
                guiAction.getButton(),
                guiAction.getInput(),
                client.player
        );
    }
}
