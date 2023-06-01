package me.andreasmelone.gunstop;

import me.andreasmelone.gunstop.magazines.AKMagazine;
import me.andreasmelone.gunstop.magazines.DeagleMagazine;
import me.andreasmelone.gunstop.magazines.RPGMagazine;
import org.apache.logging.log4j.Logger;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class GunEvents implements Listener {
    private final GunStop plugin;
    private final Logger logger;

    DeagleMagazine deagle;
    RPGMagazine rpg_1;
    AKMagazine ak_47;

    public GunEvents(GunStop gunStop) {
        plugin = gunStop;
        logger = plugin.LOGGER;
        deagle = new DeagleMagazine(plugin);
        rpg_1 = new RPGMagazine(plugin);
        ak_47 = new AKMagazine(plugin);
    }

    @EventHandler
    public void onPlayerShoot(PlayerInteractEvent event) {
        if (!plugin.config.getList("whitelist").contains(event.getPlayer().getWorld().getName())) {
            return;
        }

        Player player = event.getPlayer();

        if (event.getItem() != null) {
            ItemStack item = event.getItem();
            //plugin.LOGGER.info("Player " + player.getName() + " tried to shoot with " + item.getType().name() + " in world " + player.getWorld().getName() + ".");

            //if(!event.getAction().name().equals("RIGHT_CLICK_AIR") || !event.getAction().name().equals("RIGHT_CLICK_BLOCK")) return;
            if(!event.getAction().name().contains("RIGHT_CLICK")) return;

            Material itemType = item.getType();

            if (itemType == deagle.getGunItem()) {
                event.setCancelled(true);
                //plugin.LOGGER.info("Player " + player.getName() + " shot with " + item.getType().name() + " in world " + player.getWorld().getName() + ".");
                // Check if the player is reloading
                if (deagle.isReloading(player)) {
                    player.sendMessage(plugin.mf.getReloadMessage(deagle, player));
                    event.setCancelled(true);
                    return;
                }

                // Start the reload
                deagle.shoot(player);

                // Shoot the projectile
                Arrow arrow = player.launchProjectile(Arrow.class);

                arrow.setVelocity(player.getLocation().getDirection().multiply(4));
                arrow.setCritical(true);
                arrow.setShooter(player);

                arrow.setMetadata("isBullet", new FixedMetadataValue(plugin, "true"));
                arrow.setMetadata("damage", new FixedMetadataValue(plugin, "10"));
            } else if(itemType == rpg_1.getGunItem()) {
                event.setCancelled(true);
                //plugin.LOGGER.info("Player " + player.getName() + " shot with " + item.getType().name() + " in world " + player.getWorld().getName() + ".");
                // Check if the player is reloading
                if (rpg_1.isReloading(player)) {
                    player.sendMessage(plugin.mf.getReloadMessage(rpg_1, player));
                    event.setCancelled(true);
                    return;
                }

                // Start the reload
                rpg_1.shoot(player);

                // Shoot the projectile
                TNTPrimed tnt = player.getWorld().spawn(player.getLocation(), TNTPrimed.class);
                tnt.setVelocity(player.getLocation().getDirection().multiply(2.75));
                tnt.setFuseTicks(60);

                tnt.setMetadata("isBullet", new FixedMetadataValue(plugin, "true"));
                tnt.setMetadata("damage", new FixedMetadataValue(plugin, "35"));

                event.setCancelled(true);
            } else if(itemType == ak_47.getGunItem()) {
                event.setCancelled(true);
                //plugin.LOGGER.info("Player " + player.getName() + " shot with " + item.getType().name() + " in world " + player.getWorld().getName() + ".");
                // Check if the player is reloading
                if (ak_47.isReloading(player)) {
                    player.sendMessage(plugin.mf.getReloadMessage(ak_47, player));
                    event.setCancelled(true);
                    return;
                }

                // Start the reload
                ak_47.shoot(player);

                // Shoot the projectile
                Arrow arrow = player.launchProjectile(Arrow.class);

                arrow.setVelocity(player.getLocation().getDirection().multiply(3.5));
                arrow.setCritical(true);
                arrow.setShooter(player);

                arrow.setMetadata("isBullet", new FixedMetadataValue(plugin, "true"));
                arrow.setMetadata("damage", new FixedMetadataValue(plugin, "3"));
            }
        }
    }

    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent event) {
        //Player player = event.getPlayer();
        Entity item = event.getItem();

        if (!plugin.config.getList("whitelist").contains(event.getPlayer().getWorld().getName())) {
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

        if (!plugin.config.getList("whitelist").contains(damager.getWorld().getName())) {
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

        if(damager instanceof TNTPrimed) {
            TNTPrimed tnt = (TNTPrimed) damager;
            if(tnt.hasMetadata("isBullet")) {
                if(!tnt.hasMetadata("damage")) return;
                event.setDamage(Double.parseDouble(tnt.getMetadata("damage").get(0).asString()));
            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (!plugin.config.getList("whitelist").contains(event.getEntity().getWorld().getName())) {
            return;
        }

        event.setDroppedExp(0);
        event.getDrops().clear();
    }

    @EventHandler
    public void onTntExplode(EntityExplodeEvent event) {
        if (!plugin.config.getList("whitelist").contains(event.getEntity().getWorld().getName())) {
            return;
        }

        if(event.getEntity() instanceof TNTPrimed) {
            if(event.getEntity().hasMetadata("isBullet")) {
                event.blockList().clear();
            }
        }
    }

    @EventHandler
    public void onSelectedSlotChange(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        ItemStack newItem = event.getPlayer().getInventory().getItem(event.getNewSlot());
        if (!plugin.config.getList("whitelist").contains(player.getWorld().getName())) {
            return;
        }

        if(newItem != null) {
            if(deagle.matchesConditionsToShowXBBar(player)) {
                if(deagle.hasBullets(player)) {
                    if(deagle.isReloading(player)) {
                        deagle.showReloadTimeOnXPBar(player);
                    } else {
                        deagle.showBulletsOnXPBar(player);
                    }
                }
            } else if(rpg_1.matchesConditionsToShowXBBar(player)) {
                if(rpg_1.hasBullets(player)) {
                    if(rpg_1.isReloading(player)) {
                        rpg_1.showReloadTimeOnXPBar(player);
                    } else {
                        rpg_1.showBulletsOnXPBar(player);
                    }
                }
            } else if(ak_47.matchesConditionsToShowXBBar(player)) {
                if(ak_47.hasBullets(player)) {
                    if(ak_47.isReloading(player)) {
                        ak_47.showReloadTimeOnXPBar(player);
                    } else {
                        ak_47.showBulletsOnXPBar(player);
                    }
                }
            } else {
                player.setExp(0);
                player.setLevel(0);
            }
        } else {
            player.setExp(0);
            player.setLevel(0);
        }
    }
}