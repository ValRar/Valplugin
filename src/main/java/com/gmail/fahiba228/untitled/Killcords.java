package com.gmail.fahiba228.untitled;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;


public class Killcords implements Listener {
    @EventHandler (priority = EventPriority.NORMAL)
    public void onKill (PlayerDeathEvent event){
        Location l = event.getEntity().getLocation();
        event.getEntity().sendMessage(ChatColor.GOLD + "Вы умерли на координатах: " + l.getBlockX() + " " + l.getBlockY() + " " + l.getBlockZ());
    }
}
