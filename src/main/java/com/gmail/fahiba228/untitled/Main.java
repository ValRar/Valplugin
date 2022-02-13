package com.gmail.fahiba228.untitled;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.charset.Charset;
public class Main extends JavaPlugin {
    public static String charset;
    public static boolean BroadcastJoinMessage;
    public Configuration config = this.getConfig();
    @Override
    public void onEnable() {
        charset = getCharset();
        getCommand("cords").setExecutor(new cords());
        getCommand("warning").setExecutor(new warning());
        getCommand("note").setExecutor(new note());
        getCommand("shownote").setExecutor(new shownote());
        getCommand("delnote").setExecutor(new delnote());
        getCommand("test").setExecutor(new reload());

        loadConfiguration();
        if (config.getBoolean("ShowKillCords")) {
            getServer().getPluginManager().registerEvents(new Killcords(),this);
        }
        BroadcastJoinMessage = config.getBoolean("BroadcastJoinMessage");
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getLogger().info("Plugin started!");
    }

     private void loadConfiguration() {
        config.addDefault("ShowKillCords", true);
        config.addDefault("Charset", "windows-1251");
        config.addDefault("BroadcastJoinMessage", "true");
        config.options().copyDefaults(true);
        this.saveConfig();
    }
    public String getCharset() {
        Charset charsetName;
        try {
            charsetName = Charset.forName(config.getString("Charset"));
        } catch (IllegalArgumentException e) {
            getLogger().info(ChatColor.RED + "Invalid Charset name! Change it in the config file!");
            config.set("Charset", "windows-1251");
            getLogger().info("Using default charset - windows-1251");
            return "windows-1251";
        }
        return charsetName.toString();
    }

}
