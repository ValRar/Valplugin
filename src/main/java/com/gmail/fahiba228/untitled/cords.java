package com.gmail.fahiba228.untitled;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Location;

public class cords implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length == 1) {
            Player target = (Player) sender.getServer().getPlayer(args[0]);
            if (target != null) {
                Location l = target.getLocation();
                sender.sendMessage(ChatColor.YELLOW + "X:" + (l.getBlockX()) + " " + "Y:" + (l.getBlockY()) + " " + "Z:" + (l.getBlockZ()));
                return true;
            }
        }
        sender.sendMessage(ChatColor.RED + "Игрок не найден!");
        return false;
    }
}
