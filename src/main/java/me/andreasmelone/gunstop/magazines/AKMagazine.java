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
    long getMaximumMagazineReloadTime() {
        return 20 * 20;
    }

    @Override
    long getMaximumInMagazineReloadTime() {
        return 2;
    }

    @Override
    public Material getGunItem() {
        return Material.WOOD_HOE;
    }

    @Override
    public String getGunID() {
        return "ak_47";
    }

    @Override
    public String getGunName() {
        return "AK 47";
    }
}
