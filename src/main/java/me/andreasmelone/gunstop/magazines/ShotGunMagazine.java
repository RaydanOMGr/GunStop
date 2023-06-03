package me.andreasmelone.gunstop.magazines;

import me.andreasmelone.gunstop.GunStop;
import org.bukkit.Material;

public class ShotGunMagazine extends Magazine {
    public ShotGunMagazine(GunStop plugin) {
        super(plugin);
    }

    @Override
    int getMaximumBullets() {
        return 4;
    }

    @Override
    long getMaximumMagazineReloadTime() {
        return 15;
    }

    @Override
    long getMaximumInMagazineReloadTime() {
        return 0;
    }

    @Override
    public Material getGunItem() {
        return Material.WOOD_AXE;
    }

    @Override
    public String getGunID() {
        return "shotgun";
    }

    @Override
    public String getGunName() {
        return "Shotgun";
    }
}
