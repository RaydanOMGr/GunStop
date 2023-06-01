package me.andreasmelone.gunstop.magazines;

import me.andreasmelone.gunstop.GunStop;
import org.apache.logging.log4j.Logger;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public abstract class Magazine {
    private final GunStop plugin;
    private final Logger logger;
    private final Map<String, Integer> bulletsMap;
    private final Map<String, Integer> reloadTimeMap;

    public Magazine(GunStop plugin) {
        this.plugin = plugin;
        this.logger = plugin.LOGGER;
        this.bulletsMap = new HashMap<>();
        this.reloadTimeMap = new HashMap<>();
    }

    public boolean hasBullets(Player player) {
        return getBullets(player) > 0;
    }

    public void setBullets(Player player, int bullets) {
        bulletsMap.put(player.getName(), bullets);
    }

    public int getBullets(Player player) {
        return bulletsMap.getOrDefault(player.getName(), 0);
    }

    public void setReloadTime(Player player, int reloadTimeSeconds) {
        reloadTimeMap.put(player.getName(), reloadTimeSeconds);
    }

    public int getReloadTime(Player player) {
        return reloadTimeMap.getOrDefault(player.getName(), 0);
    }

    public boolean isReloading(Player player) {
        return getReloadTime(player) > 0;
    }

    public void shoot(Player player) {
        int bullets = getBullets(player);
        int minBullets = 1;
        //logger.info("Reloading " + player.getName() + " with " + bullets + " bullets");

        int reloadTime = bullets > minBullets ? getMaximumInMagazineReloadTime() : getMaximumMagazineReloadTime();
        //logger.info("Reload time: " + reloadTime);
        setReloadTime(player, reloadTime);
        //logger.info("Reload time: " + reloadTime + " maximum in magazine: " + getMaximumInMagazineReloadTime() + " maximum magazine: " + getMaximumMagazineReloadTime());

        // Set the XP bar to show the reload time
        //logger.info("Showing reload time on XP bar");
        //logger.info("Player Item in Hand: " + player.getItemInHand().getType());
        if(player.getInventory().getItemInHand().getType() == getGunItem()) showReloadTimeOnXPBar(player);

        // Schedule the reload completion
        //logger.info("Scheduling reload completion");
        BukkitRunnable reloadTask = new BukkitRunnable() {
            @Override
            public void run() {
                Player updatedPlayer = player.getServer().getPlayer(player.getName());
                int remainingReloadTime = getReloadTime(player) - 1;

                //logger.info("Updated Player Item in Hand: " + updatedPlayer.getItemInHand().getType());

                // Set the XP bar to show the remaining reload time
                //logger.info("Showing remaining reload time on XP bar");
                if (updatedPlayer.getInventory().getItemInHand().getType() == getGunItem())
                    showReloadTimeOnXPBar(player);

                // Update the reload time
                //logger.info("Updating reload time");
                setReloadTime(player, remainingReloadTime);
                //logger.info("Reload time: " + remainingReloadTime);

                // Cancel the task if the reload time is over
                //logger.info("Checking if reload time is over");
                if (remainingReloadTime <= 0) {
                    //logger.info("Remaining reload time less than or equal 0, reload: " + reloadTime);
                    //logger.info("Reload time is over");
                    int bullets = getBullets(player);
                    //logger.info("Bullets: " + bullets);
                    if (bullets > minBullets) {
                        if (updatedPlayer.getInventory().getItemInHand().getType() == getGunItem())
                            showBulletsOnXPBar(player);
                        setBullets(player, bullets - 1);
                        cancel();
                    } else {
                        if (updatedPlayer.getInventory().getItemInHand().getType() == getGunItem())
                            showBulletsOnXPBar(player);
                        setBullets(player, getMaximumBullets());
                        cancel();
                    }
                }
            }
        };

        reloadTask.runTaskTimer(plugin, 20L, 20L);
    }

    public void showBulletsOnXPBar(Player player) {
        int bullets = getBullets(player);
        float progress = (float) bullets / getMaximumBullets();

        player.setLevel(bullets);
        player.setExp(progress);
    }

    public void showReloadTimeOnXPBar(Player player) {
        int reloadTime = getReloadTime(player);
        int maximumReload = getBullets(player) > 0 ? getMaximumInMagazineReloadTime() : getMaximumMagazineReloadTime();

        player.setLevel(reloadTime);
        player.setExp((float) reloadTime / maximumReload);
    }

    abstract int getMaximumBullets();

    abstract int getMaximumMagazineReloadTime();

    abstract int getMaximumInMagazineReloadTime();

    abstract Material getGunItem();
}