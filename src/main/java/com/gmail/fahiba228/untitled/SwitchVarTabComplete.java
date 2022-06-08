package com.gmail.fahiba228.untitled;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class SwitchVarTabComplete implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args){
        if (args.length == 1) {
            List<String> completer = new ArrayList<String>();
            completer.add("ShowKillCords");
            completer.add("BroadcastJoinMessage");
            return completer;
        }
        return null;
    }
}
