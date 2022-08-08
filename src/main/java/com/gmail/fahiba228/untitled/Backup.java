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
        Bukkit.broadcastMessage(ChatColor.BOLD + Main.localeRes.getString("begin_backup"));
        String worldName = Bukkit.getWorlds().get(0).getName();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'_'HH-mm");
        Date date = new Date(System.currentTimeMillis());
        String zipName = worldName + "_" + formatter.format(date) + ".zip";
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                Zipper zipper = new Zipper("./" + backupDir.getName() + "/" + zipName, logger);
                zipper.addDirectory(new File(worldName));
                zipper.addDirectory(new File(worldName + "_nether"));
                zipper.addDirectory(new File(worldName + "_the_end"));
                zipper.close();
                logger.info(ChatColor.GREEN + Main.localeRes.getString("success_backup_message"));
                commandSender.sendMessage(ChatColor.GREEN + Main.localeRes.getString("success_backup_message"));
            } catch (NullPointerException e){
                commandSender.sendMessage(ChatColor.RED + Main.localeRes.getString("error_world_folder"));
                logger.info(ChatColor.RED + Main.localeRes.getString("error_world_folder"));
            } catch (IOException e) {
                commandSender.sendMessage(ChatColor.RED + Main.localeRes.getString("error_backup_message"));
                logger.info(ChatColor.RED + Main.localeRes.getString("error_backup_message"));
                e.printStackTrace();
            }
        });
        return true;
    }
}
