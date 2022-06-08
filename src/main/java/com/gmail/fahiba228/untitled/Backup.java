package com.gmail.fahiba228.untitled;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.io.IOException;

public class Backup implements CommandExecutor {
    private final Main plugin;

    public Backup(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            @SuppressWarnings("")
            @Override
            public void run() {
                String worldName = Bukkit.getWorlds().get(0).getName();
                File backupDir = new File("/worldBackups");
                if (!backupDir.exists()) backupDir.mkdir();
                try {
                    Zipper zipper = new Zipper("/worldBackups/latest.zip");
                    zipper.addDirectory(new File(worldName));
                    zipper.addDirectory(new File(worldName + "_nether"));
                    zipper.addDirectory(new File(worldName + "_the_end"));
                    zipper.close();
                    Bukkit.broadcastMessage(ChatColor.GREEN + "Backup of the world was created successfully.");
                } catch (IOException e) {
                    Bukkit.broadcastMessage(ChatColor.RED + "Failed to create a backup of the world!");
                    e.printStackTrace();
                }
            }
        });
        return true;
    }
}
