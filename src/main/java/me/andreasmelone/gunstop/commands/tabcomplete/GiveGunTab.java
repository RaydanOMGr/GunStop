package me.andreasmelone.gunstop.commands.tabcomplete;

import me.andreasmelone.gunstop.GunStop;
import me.andreasmelone.gunstop.functions.CommandFunctions;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class GiveGunTab implements TabCompleter {
    GunStop plugin;
    CommandFunctions cf;

    public GiveGunTab(GunStop plugin) {
        this.plugin = plugin;
        cf = plugin.cf;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length == 1) {
            String[] players = {};
            for(Player p : Bukkit.getOnlinePlayers()) {
                players = Arrays.copyOf(players, players.length + 1);
                players[players.length - 1] = p.getName();
            }
            return Arrays.asList(players);
        } else if(strings.length == 2) {
            String[] guns = {plugin.deagle.getGunID(), plugin.rpg.getGunID(), plugin.ak_47.getGunID(), plugin.fourShotRpg.getGunID()};
            return Arrays.asList(guns);
        } else {
            return null;
        }
    }
}
