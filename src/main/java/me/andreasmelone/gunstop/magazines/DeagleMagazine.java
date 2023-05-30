package me.andreasmelone.gunstop.magazines;

import me.andreasmelone.gunstop.GunStop;
import org.bukkit.Material;

public class DeagleMagazine extends Magazine {
    public DeagleMagazine(GunStop plugin) {
        super(plugin);
    }

    @Override
    int getMaximumBullets() {
        return 6;
    }

    @Override
    int getMaximumMagazineReloadTime() {
        return 15;
    }

    @Override
    int getMaximumInMagazineReloadTime() {
        return 0;
    }

    @Override
    Material getGunItem() {
        return Material.STONE_SPADE;
    }
}
