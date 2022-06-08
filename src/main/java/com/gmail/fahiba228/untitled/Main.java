package com.gmail.fahiba228.untitled;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.charset.Charset;

public class Main extends JavaPlugin {
    public static String charset;
    public static boolean BroadcastJoinMessage;
    private final Configuration config = this.getConfig();
    @Override
    public void onEnable() {
        charset = getCharset();

        File notesDir = new File(Bukkit.getWorlds().get(0).getName() + "/notes");
        if (!notesDir.exists()) {
            notesDir.mkdir();
        }


        getCommand("cords").setExecutor(new Cords());
        getCommand("warning").setExecutor(new Warning());
        getCommand("note").setExecutor(new Note(Bukkit.getWorlds().get(0).getName()));
        getCommand("shownote").setExecutor(new ShowNote(Bukkit.getWorlds().get(0).getName()));
        getCommand("delnote").setExecutor(new DelNote(Bukkit.getWorlds().get(0).getName()));

        loadConfiguration();
        Killcords listener = new Killcords();
        if (config.getBoolean("ShowKillCords")) {
            getServer().getPluginManager().registerEvents(listener,this);
        }
        BroadcastJoinMessage = config.getBoolean("BroadcastJoinMessage");
      
        getCommand("switch").setExecutor(new SwitchVar(this, listener));
        getCommand("switch").setTabCompleter(new SwitchVarTabComplete());


        getServer().getPluginManager().registerEvents(new JoinListener(Bukkit.getWorlds().get(0).getName()), this);
        getLogger().info("Plugin started!");
    }

     private void loadConfiguration() {
        config.addDefault("ShowKillCords", true);
        config.addDefault("Charset", "UTF8");
        config.addDefault("BroadcastJoinMessage", true);
        config.options().copyDefaults(true);
        this.saveConfig();
    }
    public String getCharset() {
        Charset charsetName;
        try {
            charsetName = Charset.forName(config.getString("Charset"));
        } catch (IllegalArgumentException e) {
            getLogger().info(ChatColor.RED + "Invalid Charset name! Change it in the config file!");
            config.set("Charset", "UTF8");
            getLogger().info("Using default charset - UTF-8");
            return "UTF8";
        }
        return charsetName.toString();
    }

    @Override
    public void onDisable() {
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
        }
        super.onDisable();
    }
}
