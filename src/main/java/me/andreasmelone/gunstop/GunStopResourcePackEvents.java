package me.andreasmelone.gunstop;

import org.apache.logging.log4j.Logger;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class GunStopResourcePackEvents implements Listener {
    private final GunStop plugin;
    private final Logger logger;

    public GunStopResourcePackEvents(GunStop gunStop) {
        plugin = gunStop;
        logger = plugin.LOGGER;
    }

    @EventHandler
    public void onPlayedJoinWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
//        if (!plugin.config.getString("whitelist").contains(player.getWorld().getName())) {
//            return;
//        }

        logger.info("Player " + player.getName() + " joined world " + player.getWorld().getName() + ".");
        System.out.println("Player " + player.getName() + " joined world " + player.getWorld().getName() + ".");
        player.setResourcePack("http://dono-02.danbot.host:25268/gunstoppack.zip");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
//        if (!plugin.config.getString("whitelist").contains(player.getWorld().getName())) {
//            return;
//        }
        logger.info("Player " + player.getName() + " joined world " + player.getWorld().getName() + ".");
        System.out.println("Player " + player.getName() + " joined world " + player.getWorld().getName() + ".");
        player.setResourcePack("http://dono-02.danbot.host:25268/gunstoppack.zip");
    }
}
