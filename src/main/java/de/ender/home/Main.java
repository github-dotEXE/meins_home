package de.ender.home;

import de.ender.core.CConfig;
import de.ender.core.Log;
import de.ender.core.UpdateChecker;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    public static Main plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        Log.log(ChatColor.AQUA + "Enabling Meins Home...");
        plugin = this;

        getCommand("home").setExecutor(new HomeCmd());
        getCommand("home").setTabCompleter(new HomeCmd());

        getCommand("sethome").setExecutor(new HomeCmdAlias());
        getCommand("sethome").setTabCompleter(new HomeCmdAlias());
        getCommand("delhome").setExecutor(new HomeCmdAlias());
        getCommand("delhome").setTabCompleter(new HomeCmdAlias());
        getCommand("listhome").setExecutor(new HomeCmdAlias());
        getCommand("listhome").setTabCompleter(new HomeCmdAlias());
        getCommand("gethome").setExecutor(new HomeCmdAlias());
        getCommand("gethome").setTabCompleter(new HomeCmdAlias());

        new CConfig("homes", plugin);

        UpdateChecker.check("1.2", "github-dotEXE", "meins_home");

    }

    @Override
    public void onDisable() {
        Log.log(ChatColor.GREEN + "Disabling Meins Home");
    }

    public static Main getPlugin() {
        return plugin;

    }
}
