package io.github.appayip.client.characters;

import io.github.appayip.MilwerMacros;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import java.util.Optional;

public final class CharacterLookup {
    private static boolean awaitingResponse;
    private static int timeout;

    public static void register() {
        // Server messages
        ClientReceiveMessageEvents.GAME.register(
                (message, _) -> handleMessage(message)
        );

        // Timeout
        ClientTickEvents.END_CLIENT_TICK.register(_ -> {
            if (!awaitingResponse) return;

            if (--timeout <= 0) {
                awaitingResponse = false;
                MilwerMacros.LOGGER.warn("Character lookup timed out!");
            }

        });
    }

    public static void find(Minecraft client) {
        if (client.player == null || client.getConnection() == null) return;
        if (awaitingResponse) return;


        awaitingResponse = true;
        timeout = 100; // 5 Seconds at 20TPS.

        client.getConnection().sendCommand("find");
    }

    private static Optional<String> parseCurrentKit(Component message) {
        String text = message.getString();

        // Ensure this is output from /find
        if (!text.contains("You are currently playing on:")) return Optional.empty();

        for (String line : text.lines().toList()) {
            String marker = "Current Kit:";
            int index = line.indexOf(marker);

            if (index == -1) continue;

            String kit = line.substring(index + marker.length()).trim();

            if (!kit.isEmpty()) return Optional.of(kit);
        }

        return Optional.empty();
    }

    private static void handleMessage(Component message) {
        if (!awaitingResponse) return;

        Optional<String> kit = parseCurrentKit(message);

        if (kit.isEmpty()) return;

        String currentKit = kit.get();

        MilwerMacros.LOGGER.info("Current Kit: {}", currentKit);


        awaitingResponse = false;
        timeout =0;
    }
}
