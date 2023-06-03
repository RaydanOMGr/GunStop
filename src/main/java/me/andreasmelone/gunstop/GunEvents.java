package me.andreasmelone.gunstop;

import me.andreasmelone.gunstop.magazines.*;
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
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GunEvents implements Listener {
    private final GunStop plugin;
    private final Logger logger;

    DeagleMagazine deagle;
    RPGMagazine rpg;
    AKMagazine ak_47;
    FourShotRPGMagazine fourShotRpg;
    AWPMagazine awp;
    ShotGunMagazine shotgun;

    public GunEvents(GunStop gunStop) {
        plugin = gunStop;
        logger = plugin.LOGGER;

        deagle = plugin.deagle;
        rpg = plugin.rpg;
        ak_47 = plugin.ak_47;
        fourShotRpg = plugin.fourShotRpg;
        awp = plugin.awp;
        shotgun = plugin.shotgun;
    }

    @EventHandler
    public void onPlayerShoot(PlayerInteractEvent event) {
        if (!plugin.config.getList("whitelist").contains(event.getPlayer().getWorld().getName())) {
            return;
        }

        Player player = event.getPlayer();

        event.setCancelled(true);

        if (event.getItem() != null) {
            ItemStack item = event.getItem();
            //plugin.LOGGER.info("Player " + player.getName() + " tried to shoot with " + item.getType().name() + " in world " + player.getWorld().getName() + ".");

            //if(!event.getAction().name().equals("RIGHT_CLICK_AIR") || !event.getAction().name().equals("RIGHT_CLICK_BLOCK")) return;
            if(!event.getAction().name().contains("RIGHT_CLICK")) return;

            Material itemType = item.getType();

            if (itemType == deagle.getGunItem()) {
                //plugin.LOGGER.info("Player " + player.getName() + " shot with " + item.getType().name() + " in world " + player.getWorld().getName() + ".");
                // Check if the player is reloading
                if (deagle.isReloading(player)) {
                    player.sendMessage(plugin.mf.getReloadMessage(deagle, player));
                    return;
                }

                // Start the reload
                deagle.shoot(player);

                // Shoot the projectile
                Arrow arrow = player.launchProjectile(Arrow.class);

                arrow.setVelocity(player.getEyeLocation().getDirection().multiply(4));
                arrow.setCritical(true);
                arrow.setShooter(player);

                arrow.setMetadata("isBullet", new FixedMetadataValue(plugin, "true"));
                arrow.setMetadata("damage", new FixedMetadataValue(plugin, "10"));
            } else if(itemType == rpg.getGunItem()) {
                //plugin.LOGGER.info("Player " + player.getName() + " shot with " + item.getType().name() + " in world " + player.getWorld().getName() + ".");
                // Check if the player is reloading
                if (rpg.isReloading(player)) {
                    player.sendMessage(plugin.mf.getReloadMessage(rpg, player));
                    return;
                }

                // Start the reload
                rpg.shoot(player);

                Vector v = player.getEyeLocation().getDirection().multiply(2.75);

                // Shoot the projectile
                TNTPrimed tnt = player.getWorld().spawn(player.getEyeLocation(), TNTPrimed.class);
                tnt.setVelocity(v);
                tnt.setFuseTicks(60);

                tnt.setMetadata("isBullet", new FixedMetadataValue(plugin, "true"));
                tnt.setMetadata("damage", new FixedMetadataValue(plugin, "40"));

                AtomicInteger counter = new AtomicInteger(60);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if(tnt.isDead()) {
                            cancel();
                            return;
                        }
                        if(tnt.isOnGround()) {
                            tnt.setVelocity(v.zero());
                            cancel();
                            return;
                        }

                        if(counter.get() <= 0) cancel();
                        counter.getAndDecrement();

                        tnt.setVelocity(v);
                    }
                }.runTaskTimer(plugin, 0, 1);
            } else if(itemType == ak_47.getGunItem()) {
                //plugin.LOGGER.info("Player " + player.getName() + " shot with " + item.getType().name() + " in world " + player.getWorld().getName() + ".");
                // Check if the player is reloading
                if (ak_47.isReloading(player)) {
                    player.sendMessage(plugin.mf.getReloadMessage(ak_47, player));
                    return;
                }

                // Start the reload
                ak_47.shoot(player);

                // Shoot the projectile
                Arrow arrow = player.launchProjectile(Arrow.class);

                arrow.setVelocity(player.getEyeLocation().getDirection().multiply(3.5));
                arrow.setCritical(true);
                arrow.setShooter(player);

                arrow.setMetadata("isBullet", new FixedMetadataValue(plugin, "true"));
                arrow.setMetadata("damage", new FixedMetadataValue(plugin, "3"));
            } else if(itemType == fourShotRpg.getGunItem()) {
                //plugin.LOGGER.info("Player " + player.getName() + " shot with " + item.getType().name() + " in world " + player.getWorld().getName() + ".");
                // Check if the player is reloading
                if (fourShotRpg.isReloading(player)) {
                    player.sendMessage(plugin.mf.getReloadMessage(fourShotRpg, player));
                    return;
                }

                // Start the reload
                fourShotRpg.shoot(player);

                // Shoot the projectile
                TNTPrimed tnt = player.getWorld().spawn(player.getEyeLocation(), TNTPrimed.class);
                tnt.setVelocity(player.getEyeLocation().getDirection().multiply(2.25));
                tnt.setFuseTicks(60);


                tnt.setMetadata("isBullet", new FixedMetadataValue(plugin, "true"));
                tnt.setMetadata("damage", new FixedMetadataValue(plugin, "25"));

                AtomicInteger counter = new AtomicInteger(60);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if(tnt.isDead()) {
                            cancel();
                            return;
                        }
                        if(tnt.isOnGround()) {
                            tnt.setVelocity(new Vector().zero());
                            cancel();
                            return;
                        }

                        if(counter.get() <= 0) cancel();
                        counter.getAndDecrement();
                    }
                }.runTaskTimer(plugin, 0, 1);
            } else if(itemType == awp.getGunItem()) {
                // Check if the player is reloading
                if (awp.isReloading(player)) {
                    player.sendMessage(plugin.mf.getReloadMessage(awp, player));
                    return;
                }

                // Start the reload
                awp.shoot(player);

                // Shoot the projectile
                Arrow arrow = player.launchProjectile(Arrow.class);
                Vector v = player.getEyeLocation().getDirection().multiply(4);

                arrow.setVelocity(v);
                arrow.setCritical(true);
                arrow.setShooter(player);

                arrow.setMetadata("isBullet", new FixedMetadataValue(plugin, "true"));
                arrow.setMetadata("damage", new FixedMetadataValue(plugin, "35"));

                AtomicInteger loopCounter = new AtomicInteger(200);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if(loopCounter.get() <= 0) cancel();
                        loopCounter.getAndDecrement();

                        if(!arrow.isOnGround()) {
                            arrow.setVelocity(v);
                        } else {
                            loopCounter.set(0);
                        }
                    }
                }.runTaskTimer(plugin, 0, 1);
            } else if(itemType == shotgun.getGunItem()) {
                // Check if the player is reloading
                if (shotgun.isReloading(player)) {
                    player.sendMessage(plugin.mf.getReloadMessage(shotgun, player));
                    return;
                }

                // Start the reload
                shotgun.shoot(player);


                // Shoot the projectile
                List<Vector> vs = plugin.of.getAngledVector(player.getEyeLocation().getDirection(), 8.0, 4.0, 0);
                logger.info(vs.size());
                for(Vector v : vs) {
                    Arrow arrow = player.launchProjectile(Arrow.class);

                    arrow.setVelocity(v);
                    arrow.setCritical(true);
                    arrow.setShooter(player);

                    arrow.setMetadata("isBullet", new FixedMetadataValue(plugin, "true"));
                    arrow.setMetadata("damage", new FixedMetadataValue(plugin, "5.5"));
                }
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
            if(plugin.of.matchesConditionsToShowExpBar(deagle, player, newItem)) {
                if(deagle.isReloading(player)) {
                    deagle.showReloadTimeOnXPBar(player);
                } else {
                    deagle.showBulletsOnXPBar(player);
                }
            } else if(plugin.of.matchesConditionsToShowExpBar(rpg, player, newItem)) {
                if(rpg.isReloading(player)) {
                    rpg.showReloadTimeOnXPBar(player);
                } else {
                    rpg.showBulletsOnXPBar(player);
                }
            } else if(plugin.of.matchesConditionsToShowExpBar(ak_47, player, newItem)) {
                if(ak_47.isReloading(player)) {
                    ak_47.showReloadTimeOnXPBar(player);
                } else {
                    ak_47.showBulletsOnXPBar(player);
                }
            } else if(plugin.of.matchesConditionsToShowExpBar(fourShotRpg, player, newItem)) {
                if(fourShotRpg.isReloading(player)) {
                    fourShotRpg.showReloadTimeOnXPBar(player);
                } else {
                    fourShotRpg.showBulletsOnXPBar(player);
                }
            } else if(plugin.of.matchesConditionsToShowExpBar(awp, player, newItem)) {
                if(awp.isReloading(player)) {
                    awp.showReloadTimeOnXPBar(player);
                } else {
                    awp.showBulletsOnXPBar(player);
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