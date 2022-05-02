package com.gmail.fahiba228.untitled;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;

public class JoinListener implements Listener {
    private final String notesPath;
    @EventHandler
    void onJoin(PlayerJoinEvent event){
        StringBuilder path = new StringBuilder();
        if (Main.BroadcastJoinMessage) {
            event.setJoinMessage(ChatColor.YELLOW + event.getPlayer().getName() + " Зашел на сервер!");
        }
        path.append(notesPath).append("/notes/").append(event.getPlayer().getUniqueId().toString()).append(".txt");
        try {
            PrintWriter writer = new PrintWriter((new FileWriter(String.valueOf(path), Charset.forName(Main.charset), true)));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public JoinListener(String notesPath) {
        this.notesPath = notesPath;
    }
}
