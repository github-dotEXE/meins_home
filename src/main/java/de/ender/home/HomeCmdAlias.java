package de.ender.home;

import de.ender.core.CConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class HomeCmdAlias implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            String arg = "";
            if(args.length != 0) arg = args[0];
            player.performCommand("home "+command.getName().replace("home","")+" "+arg);
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Main plugin = Main.getPlugin();
        CConfig cconfig = new CConfig("homes", plugin);
        FileConfiguration config = cconfig.getCustomConfig();

        List<String> completions = new ArrayList<>();
        List<String> commands = new ArrayList<>();

        Player p = (Player) sender;
        UUID uuid = p.getUniqueId();

        ConfigurationSection configurationset = config.getConfigurationSection("home." + uuid);

        if(configurationset == null) return completions;

        List<String> list = new ArrayList<>(configurationset.getKeys(true));
        for(int i = 0; i <= list.size()-1; i++){
            String key = list.get(i);
            completions.add(key);
        }

        StringUtil.copyPartialMatches(args[args.length-1], commands, completions); //copy matches of first argument
        Collections.sort(completions);//sort the list
        return completions;
    }
}
