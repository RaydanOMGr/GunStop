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
        return 50 * 20;
    }

    @Override
    long getMaximumInMagazineReloadTime() {
        return 15; // 0.75 seconds
    }

    @Override
    public Material getGunItem() {
        return Material.GOLD_SPADE;
    }

    @Override
    public String getGunID() {
        return "four_shot_rpg";
    }

    @Override
    public String getGunName() {
        return "Quad Rocket Launcher";
    }
}
