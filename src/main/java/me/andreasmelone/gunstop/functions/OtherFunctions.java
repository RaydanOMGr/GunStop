package me.andreasmelone.gunstop.functions;

import me.andreasmelone.gunstop.magazines.Magazine;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class OtherFunctions {
    public OtherFunctions() {}
    public void showExpBarForMagazine(Magazine magazine, Player player, ItemStack newItem) {
        if(magazine.matchesConditionsToShowXBBar(player, newItem)) {
            if (magazine.hasBullets(player)) {
                if (magazine.isReloading(player)) {
                    magazine.showReloadTimeOnXPBar(player);
                } else {
                    magazine.showBulletsOnXPBar(player);
                }
            }
        } else {
            player.setExp(0);
            player.setLevel(0);
        }
    }
}
