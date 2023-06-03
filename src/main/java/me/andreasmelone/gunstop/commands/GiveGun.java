package me.andreasmelone.gunstop.commands;

import me.andreasmelone.gunstop.GunStop;
import me.andreasmelone.gunstop.functions.CommandFunctions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GiveGun implements CommandExecutor {
    GunStop plugin;
    CommandFunctions cf;

    public GiveGun(GunStop plugin) {
        this.plugin = plugin;
        cf = plugin.cf;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Player target = cf.getTarget(commandSender, args);
        if(target == null) {
            commandSender.sendMessage("Please specify the player you want to give the item to.");
            return true;
        }

        if(args.length < 2) {
            commandSender.sendMessage("Please specify the gun you want to give.");
            return true;
        }

        String gunID = args[1];

        if(gunID.equals(plugin.deagle.getGunID())) {
            ItemStack item = new ItemStack(plugin.deagle.getGunItem(), 1);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(plugin.deagle.getGunName());

            item.setItemMeta(meta);

            target.getInventory().addItem(item);
        } else if(gunID.equals(plugin.rpg.getGunID())) {
            ItemStack item = new ItemStack(plugin.rpg.getGunItem(), 1);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(plugin.rpg.getGunName());
            item.setItemMeta(meta);

            target.getInventory().addItem(item);
        } else if(gunID.equals(plugin.ak_47.getGunID())) {
            ItemStack item = new ItemStack(plugin.ak_47.getGunItem(), 1);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(plugin.ak_47.getGunName());
            item.setItemMeta(meta);

            target.getInventory().addItem(item);
        } else if(gunID.equals(plugin.fourShotRpg.getGunID())) {
            ItemStack item = new ItemStack(plugin.fourShotRpg.getGunItem(), 1);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(plugin.fourShotRpg.getGunName());
            item.setItemMeta(meta);

            target.getInventory().addItem(item);
        } else if(gunID.equals(plugin.awp.getGunID())) {
            ItemStack item = new ItemStack(plugin.awp.getGunItem(), 1);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(plugin.awp.getGunName());
            item.setItemMeta(meta);

            target.getInventory().addItem(item);
        } else if(gunID.equals(plugin.shotgun.getGunID())) {
            ItemStack item = new ItemStack(plugin.shotgun.getGunItem(), 1);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(plugin.shotgun.getGunName());
            item.setItemMeta(meta);

            target.getInventory().addItem(item);
        } else {
            commandSender.sendMessage("This item does not exist.");
            return true;
        }

        commandSender.sendMessage("Item given.");
        return true;
    }
}
