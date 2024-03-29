package com.gmail.fahiba228.untitled;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Warning implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length > 0) {
            StringBuilder text = new StringBuilder();
            for (String str : args)
                text.append(str).append(" ");
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendTitle(ChatColor.RED + text.toString(), "", 10, 70, 20);
            }
            sender.sendMessage( ChatColor.GREEN + Main.localeRes.getString("success"));
            return true;
        }
        sender.sendMessage(ChatColor.RED + Main.localeRes.getString("incorr_comm_input"));
        return false;
    }
}
