package me.andreasmelone.gunstop.magazines;

import me.andreasmelone.gunstop.GunStop;
import org.bukkit.Material;

public class AWPMagazine extends Magazine {
    public AWPMagazine(GunStop plugin) {
        super(plugin);
    }

    @Override
    int getMaximumBullets() {
        return 1;
    }

    @Override
    long getMaximumMagazineReloadTime() {
        return 12 * 20 + 10;
    }

    @Override
    long getMaximumInMagazineReloadTime() {
        return 0;
    }

    @Override
    public Material getGunItem() {
        return Material.STONE_HOE;
    }

    @Override
    public String getGunID() {
        return "awp";
    }

    @Override
    public String getGunName() {
        return "AWP Sniper Rifle";
    }
}
