package me.andreasmelone.gunstop;

import org.apache.logging.log4j.Logger;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class GunEvents implements Listener {
    private final GunStop plugin;
    private final Logger logger;

    public GunEvents(GunStop gunStop) {
        plugin = gunStop;
        logger = plugin.LOGGER;
    }

    @EventHandler
    public void onPlayerShoot(PlayerInteractEvent event) {
        if (!plugin.config.getString("whitelist").contains(event.getPlayer().getWorld().getName())) {
            return;
        }

        Player player = event.getPlayer();

        if (event.getItem() != null) {
            ItemStack item = event.getItem();
            //plugin.LOGGER.info("Player " + player.getName() + " tried to shoot with " + item.getType().name() + " in world " + player.getWorld().getName() + ".");

            if(!event.getAction().name().equals("RIGHT_CLICK_AIR") || !event.getAction().name().equals("RIGHT_CLICK_BLOCK")) return;

            Material itemType = item.getType();

            if (itemType == Material.STONE_SPADE) {
                //plugin.LOGGER.info("Player " + player.getName() + " shot with " + item.getType().name() + " in world " + player.getWorld().getName() + ".");
                // Check if the player is reloading
                if (plugin.magazine.isReloading(player)) {
                    player.sendMessage("Reloading " + plugin.magazine.getReloadTime(player) + "... Please wait.");
                    event.setCancelled(true);
                    return;
                }

                // Start the reload
                plugin.magazine.shoot(player);

                // Shoot the projectile
                Arrow arrow = player.launchProjectile(Arrow.class);

                arrow.setVelocity(player.getLocation().getDirection().multiply(12));
                arrow.setCritical(true);
                arrow.setShooter(player);

                arrow.setMetadata("isBullet", new FixedMetadataValue(plugin, "true"));
                arrow.setMetadata("damage", new FixedMetadataValue(plugin, "15"));

                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent event) {
        //Player player = event.getPlayer();
        Entity item = event.getItem();

        if (!plugin.config.getString("whitelist").contains(event.getPlayer().getWorld().getName())) {
            return;
        }

        if(event.getItem() instanceof Arrow) {
            Arrow arrow = (Arrow) item;
            if(arrow.hasMetadata("isBullet")) {
                event.getItem().getItemStack().setAmount(0);
            }
        }

        if(event.getItem() instanceof ExperienceOrb) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        //Entity entity = event.getEntity();
        Entity damager = event.getDamager();

        if (!plugin.config.getString("whitelist").contains(damager.getWorld().getName())) {
            return;
        }

        if(damager instanceof Arrow) {
            Arrow arrow = (Arrow) damager;
            if(arrow.hasMetadata("isBullet")) {
                if(!arrow.hasMetadata("damage")) return;
                event.setDamage(Double.parseDouble(arrow.getMetadata("damage").get(0).asString()));
            }
        }

        if(damager instanceof Player) {
            Player player = (Player) damager;
            if(player.getInventory().getItemInHand().getType() == Material.STONE_SPADE) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (!plugin.config.getString("whitelist").contains(event.getEntity().getWorld().getName())) {
            return;
        }

        event.setDroppedExp(0);
        event.getDrops().clear();
    }
}