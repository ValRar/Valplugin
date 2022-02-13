package com.gmail.fahiba228.untitled;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.event.entity.PlayerDeathEvent;

public class reload implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Main m = new Main();
        Configuration config = m.getConfig();
        if (config.getBoolean("ShowKillCords")) {
            m.getServer().getPluginManager().registerEvents(new Killcords(), m);
        } else {
            PlayerDeathEvent.getHandlerList().unregisterAll();
        }
        return false;
    }
}
