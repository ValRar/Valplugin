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
import java.util.logging.Logger;

public class Backup implements CommandExecutor {
    private final Main plugin;
    private final File backupDir;
    private final Logger logger;

    public Backup(Main plugin, File backupDir) {
        this.plugin = plugin;
        this.backupDir = backupDir;
        logger = plugin.getLogger();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Bukkit.broadcastMessage(ChatColor.BOLD + "Start making backup...");
        String worldName = Bukkit.getWorlds().get(0).getName();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'_'HH-mm");
        Date date = new Date(System.currentTimeMillis());
        String zipName = worldName + "_" + formatter.format(date) + ".zip";
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                try {
                    Zipper zipper = new Zipper("./" + backupDir.getName() + "/" + zipName, logger);
                    zipper.addDirectory(new File(worldName));
                    zipper.addDirectory(new File(worldName + "_nether"));
                    zipper.addDirectory(new File(worldName + "_the_end"));
                    zipper.close();
                    logger.info(ChatColor.GREEN + "Backup of the world was created successfully.");
                    commandSender.sendMessage(ChatColor.GREEN + "Backup of the world was created successfully.");
                } catch (IOException e) {
                    commandSender.sendMessage(ChatColor.RED + "Failed to create a backup of the world!");
                    logger.info(ChatColor.RED + "Failed to create a backup of the world!");
                    e.printStackTrace();
                }
            }
        });
        return true;
    }
}
