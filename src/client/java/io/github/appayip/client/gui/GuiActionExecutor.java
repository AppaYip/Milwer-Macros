package io.github.appayip.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerInput;

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

    public static void executeHotbarSwap(
            Minecraft client,
            AbstractContainerMenu menu,
            int menuSlot,
            int hotbarSlot
    ) {
        if (client.player == null || client.gameMode == null) return;
        if (hotbarSlot < 0 || hotbarSlot > 8) return;

        client.gameMode.handleContainerInput(
                menu.containerId,
                menuSlot,
                hotbarSlot,
                ContainerInput.SWAP,
                client.player
        );
    }
}
