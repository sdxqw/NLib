package io.github.xnotro.utils;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("ALL")
public class Formatter {
    /**
     * Formatter message with minecraft color codes.
     * @param message The string message.
     * @return The message translated.
     */
    @Contract("_ -> new")
    public static @NotNull String formatColor(String message) {
        return ChatColor.translateAlternateColorCodes( '&', message );
    }
}
