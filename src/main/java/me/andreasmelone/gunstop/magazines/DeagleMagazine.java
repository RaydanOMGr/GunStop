package me.andreasmelone.gunstop.magazines;

import me.andreasmelone.gunstop.GunStop;
import org.bukkit.Material;

public class DeagleMagazine extends Magazine {
    public DeagleMagazine(GunStop plugin) {
        super(plugin);
    }

    @Override
    int getMaximumBullets() {
        return 4;
    }

    @Override
    long getMaximumMagazineReloadTime() {
        return 15 * 20;
    }

    @Override
    long getMaximumInMagazineReloadTime() {
        return 3 * 20;
    }

    @Override
    public Material getGunItem() {
        return Material.STONE_SPADE;
    }
}
