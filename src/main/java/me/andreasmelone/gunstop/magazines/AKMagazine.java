package me.andreasmelone.gunstop.magazines;

import me.andreasmelone.gunstop.GunStop;
import org.bukkit.Material;

public class AKMagazine extends Magazine {
    public AKMagazine(GunStop plugin) {
        super(plugin);
    }

    @Override
    int getMaximumBullets() {
        return 75;
    }

    @Override
    int getMaximumMagazineReloadTime() {
        return 20;
    }

    @Override
    int getMaximumInMagazineReloadTime() {
        return 0;
    }

    @Override
    public Material getGunItem() {
        return Material.WOOD_HOE;
    }
}
