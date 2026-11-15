package io.github.appayip.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Inventory;
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

        ContainerInput input;
        int button;

        switch (guiAction) {
            case LEFT_CLICK -> {
                input = ContainerInput.PICKUP;
                button = 0;
            }
            case RIGHT_CLICK -> {
                input = ContainerInput.PICKUP;
                button = 1;
            }
            case DROP -> {
                input = ContainerInput.THROW;
                button = 0;
            }
            case SWAP_OFFHAND -> {
                input = ContainerInput.SWAP;
                button = Inventory.SLOT_OFFHAND;
            }
            default -> {
                return;
            }
        }

        client.gameMode.handleContainerInput(
                menu.containerId,
                slot,
                button,
                input,
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
