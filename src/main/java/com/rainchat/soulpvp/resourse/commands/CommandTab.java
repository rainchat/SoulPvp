package com.rainchat.soulpvp.resourse.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class CommandTab implements TabCompleter {

    List<String> arguments3 = new ArrayList<String>();

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        List<String> commands = new ArrayList<>();
        if (args.length == 1) {
            commands.add("reload");
            commands.add("carma");
            StringUtil.copyPartialMatches(args[0], commands, completions);
        } else if (args.length == 2) {
            if (args[0].equals("carma")) {
                commands.add("add");
                commands.add("top");
                commands.add("check");
                commands.add("set");
            }
            StringUtil.copyPartialMatches(args[1], commands, completions);
        } else if (args.length == 3) {
            if (args[0].equals("carma")) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    commands.add(player.getName());
                }
                StringUtil.copyPartialMatches(args[2], commands, completions);
            }
        } else if (args.length == 4) {
            if (args[0].equals("carma")) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    commands.add(player.getName());
                }
                StringUtil.copyPartialMatches(args[3], commands, completions);
            }
        }

        return completions;
    }
}
