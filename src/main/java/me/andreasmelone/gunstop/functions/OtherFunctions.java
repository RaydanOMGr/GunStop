package me.andreasmelone.gunstop.functions;

import me.andreasmelone.gunstop.GunStop;
import me.andreasmelone.gunstop.magazines.Magazine;
import org.apache.logging.log4j.Logger;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class OtherFunctions {
    GunStop plugin;
    Logger logger;
    public OtherFunctions(GunStop plugin) {
        this.plugin = plugin;
        this.logger = plugin.LOGGER;
    }
    public boolean matchesConditionsToShowExpBar(Magazine magazine, Player player, ItemStack newItem) {
        return magazine.matchesConditionsToShowXBBar(player, newItem) && magazine.hasBullets(player);
    }
}
