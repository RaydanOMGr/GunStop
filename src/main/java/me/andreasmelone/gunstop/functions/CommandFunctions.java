package me.andreasmelone.gunstop.functions;

import me.andreasmelone.gunstop.GunStop;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandFunctions {
    GunStop plugin;
    public CommandFunctions(GunStop plugin) {
        this.plugin = plugin;
    }
    public Player getTarget(CommandSender sender, String[] args) {
        if (args.length > 0) {
            Player target = Bukkit.getPlayer(sender.getName());
            if(target != null) {
                return target;
            }
        }

        if(sender instanceof Player) {
            return (Player) sender;
        } else {
            return null;
        }
    }
}
