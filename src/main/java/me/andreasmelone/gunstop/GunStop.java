package me.andreasmelone.gunstop;

import me.andreasmelone.gunstop.commands.RegisterCommands;
import me.andreasmelone.gunstop.functions.CommandFunctions;
import me.andreasmelone.gunstop.functions.MessageFunctions;
import me.andreasmelone.gunstop.functions.OtherFunctions;
import me.andreasmelone.gunstop.magazines.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class GunStop extends JavaPlugin {
    public final Logger LOGGER = LogManager.getLogger();
    public MessageFunctions mf;
    public OtherFunctions of;
    public CommandFunctions cf;

    public DeagleMagazine deagle;
    public RPGMagazine rpg;
    public AKMagazine ak_47;
    public FourShotRPGMagazine fourShotRpg;
    public AWPMagazine awp;
    public ShotGunMagazine shotgun;

    FileConfiguration config;

    @Override
    public void onEnable() {
        mf = new MessageFunctions();
        of = new OtherFunctions(this);
        cf = new CommandFunctions(this);

        deagle = new DeagleMagazine(this);
        rpg = new RPGMagazine(this);
        ak_47 = new AKMagazine(this);
        fourShotRpg = new FourShotRPGMagazine(this);
        awp = new AWPMagazine(this);
        shotgun = new ShotGunMagazine(this);

        // load the config, the file already exists
        saveDefaultConfig();
        config  = getConfig();
        // register the event listener
        getServer().getPluginManager().registerEvents(new GunEvents(this), this);
        getServer().getPluginManager().registerEvents(new GunStopResourcePackEvents(this), this);
        // register the commands
        new RegisterCommands(this).registerCommands();
    }

    @Override
    public void onDisable() {}
}
