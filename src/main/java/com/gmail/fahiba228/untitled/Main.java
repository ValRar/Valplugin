package com.gmail.fahiba228.untitled;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.nio.charset.Charset;

public class Main extends JavaPlugin {
    public static String charset;
    public static boolean BroadcastJoinMessage;
    private final Configuration config = this.getConfig();
    @Override
    public void onEnable() {
        charset = getCharset();

        File notesDir = new File(Bukkit.getWorlds().get(0).getName() + "/notes");
        if (!notesDir.exists()){
            notesDir.mkdir();
        }


        getCommand("cords").setExecutor(new cords());
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
        config.addDefault("Charset", "windows-1251");
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
            return "windows-1251";
        }
        return charsetName.toString();
    }

}
