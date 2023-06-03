package me.andreasmelone.gunstop.magazines;

import me.andreasmelone.gunstop.GunStop;
import org.bukkit.Material;

public class RPGMagazine extends Magazine {
    public RPGMagazine(GunStop plugin) {
        super(plugin);
    }

    @Override
    int getMaximumBullets() {
        return 1;
    }

    @Override
    long getMaximumMagazineReloadTime() {
        return 15 * 20;
    }

    @Override
    long getMaximumInMagazineReloadTime() {
        return 0;
    }

    @Override
    public Material getGunItem() {
        return Material.WOOD_SPADE;
    }

    @Override
    public String getGunID() {
        return "rpg";
    }

    @Override
    public String getGunName() {
        return "Rocket Launcher";
    }
}
