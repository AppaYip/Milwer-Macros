package io.github.appayip.client;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.ColorCollection;

import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class GuiActionResolver {

    private record ButtonKey(Item item, String name) {
        private ButtonKey {
            name = name.toLowerCase();
        }

        static ButtonKey of(ItemStack itemStack) {
            return new ButtonKey(
                    itemStack.getItem(),
                    itemStack.getHoverName().getString()
            );
        }
    }

    private static final ColorCollection<Item> STAINED_GLASS_PANE = Items.STAINED_GLASS_PANE;

    private static final Map<ButtonKey, GuiAction> ACTIONS = Map.of(
            new ButtonKey(STAINED_GLASS_PANE.lightBlue(), "Left Click!"), GuiAction.LEFT_CLICK,
            new ButtonKey(STAINED_GLASS_PANE.lime(), "Right Click!"), GuiAction.RIGHT_CLICK,
            new ButtonKey(STAINED_GLASS_PANE.yellow(), "Drop Item!"), GuiAction.DROP,
            new ButtonKey(STAINED_GLASS_PANE.magenta(), "Swap Hands!"), GuiAction.SWAP_OFFHAND
    );

    public static Optional<GuiAction> resolve(ItemStack itemStack) {
        if (itemStack.isEmpty()) return Optional.empty();

        return Optional.ofNullable(ACTIONS.get(ButtonKey.of(itemStack)));
    }

    private static final Pattern HOTBAR_BUTTON = Pattern.compile("^Press\\s+([1-9])\\s*!$");

    public static OptionalInt resolveHotbarSlot(ItemStack itemStack) {
        if (itemStack.isEmpty()) return OptionalInt.empty();
        if (itemStack.getItem() != Items.STAINED_GLASS_PANE.pick(DyeColor.WHITE)) return OptionalInt.empty();

        String name = itemStack.getHoverName().getString();
        Matcher matcher = HOTBAR_BUTTON.matcher(name);

        if (!matcher.matches()) return OptionalInt.empty();

        // Mc hotbar index starts at 0 so we subtract 1.
        return OptionalInt.of(Integer.parseInt(
                matcher.group(1)) - 1
        );
    }
}
