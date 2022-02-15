package com.gmail.fahiba228.untitled;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.event.entity.PlayerDeathEvent;


public class killcords_switch implements CommandExecutor {
    private boolean var;
    private final Main plugin;
    Killcords killcords;

    public killcords_switch(Main plugin, Killcords listener) {
        this.plugin = plugin; // Store the plugin in situations where you need it.
        killcords = listener;
        var = this.plugin.getConfig().getBoolean("ShowKillCords");
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Configuration config = this.plugin.getConfig();
        if (var){
            var = false;
            PlayerDeathEvent.getHandlerList().unregisterAll();
        } else{
            var = true;
            this.plugin.getServer().getPluginManager().registerEvents(new Killcords(), this.plugin);
        }
        config.set("ShowKillCords", var);
        Bukkit.broadcastMessage(ChatColor.ITALIC + "variable ShowKillCords now set to: " + var);
        return true;
    }
}
