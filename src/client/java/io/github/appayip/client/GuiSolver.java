package io.github.appayip.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;

public final class GuiSolver {

    public static void execute(Minecraft client) {
        Player player = client.player;

        if (player == null) return;


        Screen screen = client.gui.screen();

        if (!(screen instanceof AbstractContainerScreen<?> containerScreen)) return;
        if (!(containerScreen.getMenu() instanceof ChestMenu chest)) return;


        //player.sendSystemMessage(Component.literal("Solving gui..."));



        for (Map.Entry<Integer, ItemStack> entry : mapChestItems(chest).entrySet()) {
            Integer slot = entry.getKey();
            ItemStack stack = entry.getValue();

            //Check for specific hotbar swap item.
            OptionalInt hotbarSlot = GuiActionResolver.resolveHotbarSlot(stack);
            if (hotbarSlot.isPresent()) {
                GuiActionExecutor.executeHotbarSwap(
                        client,
                        chest,
                        slot,
                        hotbarSlot.getAsInt()
                );

                return;
            }

            // Regular Actions
            Optional<GuiAction> action = GuiActionResolver.resolve(stack);
            if (action.isEmpty()) continue;

            GuiActionExecutor.execute(client, chest, slot, action.get());
            break; // Only one action per execute call.
        }
    }

    /**
     * Identifies the type of Milwer GUI.
     *
     * @param screen The client's screen.
     * @return The GUI type.
     */
    private static GuiType identifyGui(AbstractContainerScreen<?> screen) {
        if (!(screen.getMenu() instanceof ChestMenu)) return GuiType.UNKNOWN;

        String title = screen.getTitle().getString();

        if (title.equalsIgnoreCase("Crafting Minigame")) {
            return GuiType.CRAFTER;
        }


        return GuiType.UNKNOWN;
    }


    /**
     * Maps a chest inventories slots to its contents.
     *
     * @param chest The chest.
     * @return Map, slot -> ItemStack
     */
    private static Map<Integer, ItemStack> mapChestItems(ChestMenu chest) {
        Map<Integer, ItemStack> chestItems = new HashMap<>();


        Container container = chest.getContainer();

        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);

            if (stack.isEmpty()) continue;

            chestItems.put(i, stack);
        }

        return chestItems;
    }
}
