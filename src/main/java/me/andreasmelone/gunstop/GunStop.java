package me.andreasmelone.gunstop;

import me.andreasmelone.gunstop.functions.Magazine;
import me.andreasmelone.gunstop.functions.ResourcePackManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class GunStop extends JavaPlugin {
    public final Logger LOGGER = LogManager.getLogger();
    public Magazine magazine = new Magazine(this);
    public ResourcePackManager resourcePackManager = new ResourcePackManager(this);
    FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        // load the config, the file already exists
        saveDefaultConfig();
        // register the event listener
        getServer().getPluginManager().registerEvents(new GunEvents(this), this);
        getServer().getPluginManager().registerEvents(new GunStopResourcePackEvents(this), this);
    }

    @Override
    public void onDisable() {}
}
