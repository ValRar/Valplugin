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

public class DelNote implements CommandExecutor {
    private final String notesPath;
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        String deletedLine;
        if (args.length == 1){
            StringBuilder path = new StringBuilder();
            StringBuilder pathCache = new StringBuilder();
            path.append(notesPath).append("/notes/").append(commandSender.getServer().getPlayer(commandSender.getName()).getUniqueId()).append(".txt");
            pathCache.append(notesPath).append("/notes/").append(commandSender.getServer().getPlayer(commandSender.getName()).getUniqueId()).append(".tmp.txt");
            List<String> lines = new ArrayList();
            File sourceFile = new File(path.toString());
            File outputFile = new File(pathCache.toString());
            try {
                BufferedReader reader = Files.newBufferedReader(Paths.get(path.toString()), Charset.forName(Main.charset));
                BufferedWriter writer = new BufferedWriter(new FileWriter(pathCache.toString()));
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
                commandSender.sendMessage(ChatColor.GREEN + Main.localeRes.getString("note_deleted_success") + deletedLine);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return true;
            } catch (NumberFormatException e){
                commandSender.sendMessage(ChatColor.RED + Main.localeRes.getString("arg_need_num_err_mess"));
                return false;
            } catch (IndexOutOfBoundsException e){
                commandSender.sendMessage(ChatColor.RED + Main.localeRes.getString("invalid_note_index"));
                return false;
            }
        }
        commandSender.sendMessage(ChatColor.RED + Main.localeRes.getString("invalid_note_index"));
        return false;
    }
    public DelNote(String notesPath) {
        this.notesPath = notesPath;
    }
}
