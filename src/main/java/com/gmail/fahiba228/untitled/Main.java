package com.gmail.fahiba228.untitled;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main extends JavaPlugin {
    public static String charset;
    private final Configuration config = this.getConfig();
    private final static Locale RUS_LOC = new Locale("ru", "RU");
    static ResourceBundle localeRes;
    @Override
    public void onEnable() {
        charset = getCharset();

        File notesDir = new File(Bukkit.getWorlds().get(0).getName() + "/notes");
        if (!notesDir.exists()) {
            notesDir.mkdir();
        }
        File backupDir = new File("./" + config.getString("BackupDirectory", "worldBackups") + "/");
        if (!backupDir.exists()) {
            backupDir.mkdirs();
        }
        if (config.getString("Language") != null) {
            if (Objects.equals(config.getString("Language"), "ru")) {
                localeRes = ResourceBundle.getBundle("locale", RUS_LOC);
            } else {
                localeRes = ResourceBundle.getBundle("locale", Locale.ENGLISH);
            }
        } else if (Locale.getDefault().equals(RUS_LOC)){
            localeRes = ResourceBundle.getBundle("locale", RUS_LOC);
            loadConfiguration("ru");
        } else if (Locale.getDefault().getLanguage().equals(Locale.ENGLISH)) {
            localeRes = ResourceBundle.getBundle("locale", Locale.ENGLISH);
            loadConfiguration("en");
        } else {
            getLogger().info("Unfortunately, your language is not supported. English is set by default.");
            localeRes = ResourceBundle.getBundle("locale", Locale.ENGLISH);
            loadConfiguration("en");
        }

        getCommand("cords").setExecutor(new Cords());
        getCommand("warning").setExecutor(new Warning());
        getCommand("note").setExecutor(new Note(Bukkit.getWorlds().get(0).getName()));
        getCommand("shownote").setExecutor(new ShowNote(Bukkit.getWorlds().get(0).getName()));
        getCommand("delnote").setExecutor(new DelNote(Bukkit.getWorlds().get(0).getName()));
        getCommand("backup").setExecutor(new Backup(this, backupDir));

        Killcords listener = new Killcords();
        if (config.getBoolean("ShowKillCords")) {
            getServer().getPluginManager().registerEvents(listener,this);
        }


        getServer().getPluginManager().registerEvents(new JoinListener(Bukkit.getWorlds().get(0).getName()), this);
        getLogger().info("Plugin started!");
    }

     private void loadConfiguration(String locale) {
        config.addDefault("ShowKillCords", true);
        config.addDefault("Charset", "UTF8");
        config.addDefault("BackupOnShutDown", true);
        config.addDefault("BackupDirectory", "worldBackups");
        config.addDefault("Language", locale);
        config.options().copyDefaults(true);
        this.saveConfig();
    }
    public String getCharset() {
        Charset charsetName;
        try {
            charsetName = Charset.forName(config.getString("Charset"));
        } catch (IllegalArgumentException e) {
            getLogger().info(ChatColor.RED + localeRes.getString("invalid_charset_message"));
            config.set("Charset", "UTF8");
            getLogger().info(localeRes.getString("using_def_charset_message"));
            return "UTF8";
        }
        return charsetName.toString();
    }

    @Override
    public void onDisable() {
        if (config.getBoolean("BackupOnShutDown")) {
            String worldName = Bukkit.getWorlds().get(0).getName();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'_'HH-mm");
            Date date = new Date(System.currentTimeMillis());
            String zipName = worldName + "_" + formatter.format(date) + ".zip";
            try {
                Zipper zipper = new Zipper("./worldBackups/" + zipName, getLogger());
                zipper.addDirectory(new File(worldName));
                zipper.addDirectory(new File(worldName + "_nether"));
                zipper.addDirectory(new File(worldName + "_the_end"));
                zipper.close();
                getLogger().info(ChatColor.GREEN + localeRes.getString("success_backup_message"));
            } catch (IOException e) {
                getLogger().info(ChatColor.RED + localeRes.getString("error_backup_message"));
                e.printStackTrace();
            }
        }
    }
}
