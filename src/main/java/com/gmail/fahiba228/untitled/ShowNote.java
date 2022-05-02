package com.gmail.fahiba228.untitled;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ShowNote implements CommandExecutor {
    private final String notesPath;
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        try {
            StringBuilder path = new StringBuilder();
            path.append(notesPath).append("/notes/").append(commandSender.getServer().getPlayer(commandSender.getName()).getUniqueId().toString()).append(".txt");
            int i = 0;
            BufferedReader reader = Files.newBufferedReader(Paths.get(path.toString()), Charset.forName(Main.charset));
            String line;
            while ((line = reader.readLine()) != null) {
                i++;
                commandSender.sendMessage(ChatColor.ITALIC + Integer.toString(i) + ". " + line);
            }
            if (i == 0)
                commandSender.sendMessage(ChatColor.RED + "Ни одной записи не найдено!");
        }
        catch (IOException e) {
            commandSender.sendMessage( ChatColor.RED + "Произошла неизвестная ошибка!");
        }
        return true;
    }
    public ShowNote(String notesPath) {
        this.notesPath = notesPath;
    }
}