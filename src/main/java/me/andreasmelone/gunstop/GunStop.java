package me.andreasmelone.gunstop;

import me.andreasmelone.gunstop.functions.MessageFunctions;
import me.andreasmelone.gunstop.functions.OtherFunctions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class GunStop extends JavaPlugin {
    public final Logger LOGGER = LogManager.getLogger();
    public MessageFunctions mf;
    public OtherFunctions of;
    FileConfiguration config;

    @Override
    public void onEnable() {
        mf = new MessageFunctions();
        of = new OtherFunctions(this);
        // load the config, the file already exists
        saveDefaultConfig();
        config  = getConfig();
        // register the event listener
        getServer().getPluginManager().registerEvents(new GunEvents(this), this);
        getServer().getPluginManager().registerEvents(new GunStopResourcePackEvents(this), this);
    }

    @Override
    public void onDisable() {}
}
