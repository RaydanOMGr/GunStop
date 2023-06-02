package me.andreasmelone.gunstop.magazines;

import me.andreasmelone.gunstop.GunStop;
import org.bukkit.Material;

public class FourShotRPGMagazine extends Magazine {
    public FourShotRPGMagazine(GunStop plugin) {
        super(plugin);
    }

    @Override
    int getMaximumBullets() {
        return 4;
    }

    @Override
    long getMaximumMagazineReloadTime() {
        return 50;
    }

    @Override
    long getMaximumInMagazineReloadTime() {
        return 30; // 1.5 seconds
    }

    @Override
    public Material getGunItem() {
        return Material.GOLD_SPADE;
    }
}
