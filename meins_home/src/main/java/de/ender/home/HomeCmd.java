package de.ender.home;

import de.ender.core.CConfig;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class HomeCmd implements @Nullable CommandExecutor, @Nullable TabCompleter {
    private static String ErrorWrongUse = ChatColor.RED + "Please use /home [name]|new/set|list|del/delete/remove|get -|[name]|-|-|[name]";

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Main plugin = Main.getPlugin();

        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This Command is only for Players!");
            return false;
        }
        if (args.length >= 3) {
            sender.sendMessage(ErrorWrongUse);
            return false;
        }

        Player p = (Player) sender;
        Location plocation = p.getLocation();
        UUID uuid = p.getUniqueId();
        CConfig cconfig = new CConfig("homes", plugin);
        FileConfiguration config = cconfig.getCustomConfig();

        switch (args[0]) {
            case "set":
            case "new":
                if (args[1].isEmpty()) {
                    p.sendMessage(ErrorWrongUse);
                    return false;
                }
                config.set("home." + uuid + "." + args[1], plocation);
                cconfig.save();
                p.sendMessage(ChatColor.GREEN + "Successfully made new home called " + args[1] + "!");
                break;

            case "list":
                p.sendMessage(ChatColor.DARK_PURPLE + "List of your homes: " + config.getConfigurationSection("home." + uuid).getKeys(false) + "!");
                break;

            case "remove":
            case "delete":
            case "del":
                config.set("home." + uuid + "." + args[1],null);
                p.sendMessage(ChatColor.GOLD +"Home "+ args[1] + " was removed!");
                break;
                
            case "get":    
            default:
                int a = args.length-1;
                if (args[a].isEmpty()) {
                    p.sendMessage(ErrorWrongUse);
                    return false;
                }
                Location newlocation = (Location) config.get("home." + uuid + "." + args[a]);
                if (newlocation != null) {
                    p.teleport(newlocation);
                    p.sendMessage(ChatColor.GREEN + "Successfully teleported you to " + args[a] + "!");
                } else {
                    p.sendMessage(ChatColor.RED + "Home " + args[a] + " doesn't exist!");
                }
                break;
        }

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();
        List<String> commands = new ArrayList<>();

        if(args.length == 1) {
            completions.add("new");
            completions.add("set");
            completions.add("list");
            completions.add("del");
            completions.add("delete");
            completions.add("remove");
            completions.add("get");
            completions.add("<name>");
        } else if (args.length == 2 && !(args[0].equals("list")))  completions.add("<name>");

        StringUtil.copyPartialMatches(args[args.length-1], commands, completions); //copy matches of first argument
        Collections.sort(completions);//sort the list
        return completions;
    }
}
