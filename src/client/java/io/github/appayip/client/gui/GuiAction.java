package io.github.appayip.client.gui;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerInput;

public enum GuiAction {
    LEFT_CLICK(ContainerInput.PICKUP, 0),
    RIGHT_CLICK(ContainerInput.PICKUP, 1),

    SHIFT_LEFT_CLICK(ContainerInput.QUICK_MOVE, 0),
    SHIFT_RIGHT_CLICK(ContainerInput.QUICK_MOVE, 1),

    DROP(ContainerInput.THROW, 0),
    SWAP_OFFHAND(ContainerInput.SWAP, Inventory.SLOT_OFFHAND);


    private final ContainerInput input;
    private final int button;

    GuiAction(ContainerInput input, int button) {
        this.input = input;
        this.button = button;
    }


    public ContainerInput getInput() {
        return input;
    }

    public int getButton() {
        return button;
    }
}
