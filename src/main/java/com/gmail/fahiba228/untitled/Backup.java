package com.gmail.fahiba228.untitled;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Backup implements CommandExecutor {
    private final Main plugin;
    private final File backupDir;

    public Backup(Main plugin, File backupDir) {
        this.plugin = plugin;
        this.backupDir = backupDir;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        String worldName = Bukkit.getWorlds().get(0).getName();
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd'_'HH-mm");
        Date date = new Date(System.currentTimeMillis());
        String zipName = worldName + "_" + formatter.format(date) + ".zip";
        Bukkit.broadcastMessage(zipName);
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                try {
                    Zipper zipper = new Zipper("./worldBackups/" + zipName);
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
