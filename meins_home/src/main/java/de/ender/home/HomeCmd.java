package de.ender.home;

import de.ender.core.CConfig;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class HomeCmd implements @Nullable CommandExecutor {
    private static String ErrorWrongUse = ChatColor.RED + "Please use /home [name]|new -|[name]";

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

        if(args[0].equals("new")) {
            if (args[1].isEmpty()) {
                p.sendMessage(ErrorWrongUse);
                return false;
            }
            config.set("home." + uuid +"."+ args[1], plocation);
            cconfig.save();
            p.sendMessage(ChatColor.GREEN +"Successfully made new home called "+ args[0]+ "!");
        } else  {
            if (args[0].isEmpty()) {
                p.sendMessage(ErrorWrongUse);
                return false;
            }
            Location newlocation = (Location) config.get("home." + uuid +"."+ args[0]);
            if (newlocation != null) {
                p.teleport(newlocation);
                p.sendMessage(ChatColor.GREEN +"Successfully teleported you to "+ args[0]+ "!");
            } else {
                p.sendMessage(ChatColor.RED + "Home "+ args[0]+ "doesn't exist!");
            }
        }

        return false;
    }
}
