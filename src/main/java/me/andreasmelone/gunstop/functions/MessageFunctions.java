package me.andreasmelone.gunstop.functions;

import me.andreasmelone.gunstop.magazines.Magazine;
import org.bukkit.entity.Player;

public class MessageFunctions {
    public MessageFunctions() {}
    public String getReloadMessage(Magazine magazine, Player player) {
        long reloadTime = magazine.getReloadTime(player);
        String message;
        if(reloadTime < 20) {
            message = "Reloading " + (reloadTime / 20)  / 1000 + " milliseconds...";
        } else {
            message = "Reloading " + reloadTime / 20 + " seconds...";
        }

        return message;
    }
}
