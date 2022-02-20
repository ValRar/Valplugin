package com.gmail.fahiba228.untitled;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.List;


public class SwitchVar implements CommandExecutor {
    private final Main plugin;
    Killcords killcords;

    public SwitchVar(Main plugin, Killcords killcords) {
        this.plugin = plugin; // Store the plugin in situations where you need it.
        this.killcords = killcords;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Configuration config = this.plugin.getConfig();
        if (config.isBoolean(strings[0])) {
            boolean var = config.getBoolean(strings[0]);
            switch (strings[0]) {
                case "ShowKillCords":
                    if (var) {
                        var = false;
                        PlayerDeathEvent.getHandlerList().unregisterAll();
                    } else {
                        var = true;
                        this.plugin.getServer().getPluginManager().registerEvents(killcords, this.plugin);
                    }
                    break;
                case "BroadcastJoinMessage":
                    Main.BroadcastJoinMessage = !var;
                    var = !var;
                    break;
            }
            Bukkit.broadcastMessage(ChatColor.ITALIC + "variable " + strings[0] + " now set to: " + var);
            config.set(strings[0], var);
            this.plugin.saveConfig();
            return true;
        }
        return false;
    }
}
