package com.gmail.fahiba228.untitled;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class delnote implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        String deletedLine;
        if (args.length == 1){
            StringBuilder path = new StringBuilder();
            StringBuilder pathcache = new StringBuilder();
            path.append("./plugins/valplugin/").append(commandSender.getName()).append(".txt");
            pathcache.append("./plugins/valplugin/").append(commandSender.getName()).append("cache.txt");
            List<String> lines = new ArrayList();
            File sourceFile = new File(path.toString());
            File outputFile = new File(pathcache.toString());
            try {
                BufferedReader reader = Files.newBufferedReader(Paths.get(path.toString()), Charset.forName(Main.charset));
                BufferedWriter writer = new BufferedWriter(new FileWriter(pathcache.toString()));
                lines.add(reader.readLine());
                for (int i = 0;lines.get(i) != null;i++) {
                    lines.add(reader.readLine());
                }
                int index = Integer.parseInt(args[0]);
                deletedLine = lines.get(index - 1);
                lines.remove(index - 1);
                reader.close();
                for(int i = 0;i < lines.size() - 1;i++){
                    writer.write(lines.get(i));
                    writer.newLine();
                }
                writer.close();
                sourceFile.delete();
                outputFile.renameTo(sourceFile);
                commandSender.sendMessage(ChatColor.GREEN + "Запись успешно удалена: " + deletedLine);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return true;
            } catch (NumberFormatException e){
                commandSender.sendMessage(ChatColor.RED + "Аргумент должен быть числом!");
                return false;
            } catch (IndexOutOfBoundsException e){
                commandSender.sendMessage(ChatColor.RED + "Введите действительный номер заметки!");
                return false;
            }
        }
        commandSender.sendMessage(ChatColor.RED + "Введите номер заметки!");
        return false;
    }
}
