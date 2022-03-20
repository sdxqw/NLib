package com.github.xnotro.utils;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

@SuppressWarnings("ALL")
public class Messages {

    /**
     * Log a message.
     * @param plugin Your plugin instance.
     * @param level One of the message level identifiers, e.g, Level.INFO.
     * @param message The string message.
     * @see Level
     */
    public static void sendLog(@NotNull JavaPlugin plugin, Level level, String message) {
        plugin.getLogger().log( level, Formatter.formatColor( message ) );
    }

    /**
     * Send an info.
     * @param plugin Your plugin instance.
     * @param message The string message.
     */
    public static void sendInfo(@NotNull JavaPlugin plugin, String message) {
        plugin.getLogger().info( Formatter.formatColor( message ) );
    }

    /**
     * Send a warning.
     * @param plugin Your plugin instance..
     * @param message The string message.
     */
    public static void sendWarning(@NotNull JavaPlugin plugin, String message) {
        plugin.getLogger().warning( Formatter.formatColor( message ) );
    }


    /**
     * Send a message from console.
     * @param sender Your plugin instance.
     * @param message The string message.
     */
    public static void sendMessageFromConsole(@NotNull CommandSender sender, String message) {
        sender.sendMessage( Formatter.formatColor( message ) );
    }
}
