package me.andreasmelone.gunstop.commands;

import me.andreasmelone.gunstop.GunStop;
import me.andreasmelone.gunstop.commands.tabcomplete.GiveGunTab;

public class RegisterCommands {
    GunStop plugin;
    public RegisterCommands(GunStop plugin) {
        this.plugin = plugin;
    }

    public void registerCommands() {
        plugin.getCommand("givegun").setExecutor(new GiveGun(plugin));
        plugin.getCommand("givegun").setTabCompleter(new GiveGunTab(plugin));
    }
}