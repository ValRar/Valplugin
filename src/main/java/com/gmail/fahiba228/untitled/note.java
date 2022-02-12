package com.gmail.fahiba228.untitled;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;

public class note implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        StringBuilder text = new StringBuilder("");
        StringBuilder path = new StringBuilder();
        path.append("./plugins/valplugin/").append(commandSender.getName()).append(".txt");
        for (String str : args) {
            text.append(str).append(" ");
        }
        write(String.valueOf(text), path.toString());
        commandSender.sendMessage(ChatColor.GREEN + "Запись создана успешно: " + text);
        return true;
    }
    private static void write(String str, String path) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter((new FileWriter(path, Charset.forName(Main.charset), true)));
            writer.println(str);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

