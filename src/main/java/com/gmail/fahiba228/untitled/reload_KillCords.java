package com.gmail.fahiba228.untitled;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.event.entity.PlayerDeathEvent;


public class reload_KillCords implements CommandExecutor {
    private final Main plugin;
    Killcords killcords;

    public reload_KillCords(Main plugin, Killcords listener) {
        this.plugin = plugin; // Store the plugin in situations where you need it.
        killcords = listener;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Configuration config = this.plugin.getConfig();
        if (PlayerDeathEvent.getHandlerList().getRegisteredListeners().length > 0) {
            PlayerDeathEvent.getHandlerList().unregisterAll();
        }
        if (config.getBoolean("ShowKillCords")) {
            this.plugin.getServer().getPluginManager().registerEvents(killcords, this.plugin);
        }
        commandSender.sendMessage(ChatColor.GREEN + "Плагин перезагружен!");
        return true;
    }
}
