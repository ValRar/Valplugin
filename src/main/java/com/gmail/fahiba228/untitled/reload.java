package com.gmail.fahiba228.untitled;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.checkerframework.checker.units.qual.K;

public class reload implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Main m = new Main();
        Killcords listener = new Killcords();
        if (m.config.getBoolean("ShowKillCords")) {
            m.getServer().getPluginManager().registerEvents(new Killcords(), m);
        } else {
            HandlerList.unregisterAll(listener);
        }
        return true;
    }
}
