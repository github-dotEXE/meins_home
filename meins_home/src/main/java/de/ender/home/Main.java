package de.ender.home;

import de.ender.core.CConfig;
import de.ender.core.MCore;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    public static Main plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        new MCore().log(ChatColor.AQUA + "Enabling Meins Home...");
        plugin = this;

        getCommand("home").setExecutor(new HomeCmd());
        new CConfig("homes", plugin);

    }

    @Override
    public void onDisable() {
        new MCore().log(ChatColor.GREEN + "Disabling Meins Home");
    }

    public static Main getPlugin() {
        return plugin;

    }
}
