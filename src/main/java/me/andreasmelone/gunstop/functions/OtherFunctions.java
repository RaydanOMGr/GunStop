package me.andreasmelone.gunstop.functions;

import me.andreasmelone.gunstop.GunStop;
import me.andreasmelone.gunstop.magazines.Magazine;
import org.apache.logging.log4j.Logger;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class OtherFunctions {
    GunStop plugin;
    Logger logger;
    public OtherFunctions(GunStop plugin) {
        this.plugin = plugin;
        this.logger = plugin.LOGGER;
    }
    public boolean matchesConditionsToShowExpBar(Magazine magazine, Player player, ItemStack newItem) {
        return magazine.matchesConditionsToShowXBBar(player, newItem) && magazine.hasBullets(player);
    }

    public List<Vector> getAngledVector(Vector direction, double spreadAngle, double angleIncrement, int amount) {
        List<Vector> vectors = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            double angle = Math.toRadians(spreadAngle);

            // Calculate the new direction vector based on the angle
            double x = direction.getX();
            double z = direction.getZ();
            double cosAngle = Math.cos(angle);
            double sinAngle = Math.sin(angle);
            direction.setX(x * cosAngle - z * sinAngle);
            direction.setZ(x * sinAngle + z * cosAngle);
            vectors.add(direction.clone());

            // Update the spread angle for the next arrow
            if (i == 0) {
                spreadAngle += angleIncrement;
            } else {
                spreadAngle -= angleIncrement;
            }
        }

        return vectors;
    }
}
