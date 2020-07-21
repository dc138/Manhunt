/*
   Manhunt plugin by DarthChungo
   MIT License
   Copyright (c) 2020 Antonio de Haro
*/

package me.DarthChungo.Manhunt;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ManhuntCommand implements TabExecutor {
    private final Manhunt plugin;
    private final ItemStack compass;

    ManhuntCommand(Manhunt p) {
        plugin = p;

        Objects.requireNonNull(this.plugin.getCommand("hunt")).setExecutor(this);

        compass = new ItemStack(Material.COMPASS, 1);
        compass.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 10);
        Objects.requireNonNull(compass.getItemMeta()).setDisplayName(ChatColor.GREEN + "" + ChatColor.GREEN + "Tracking compass");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "This command cannot be run by the console.");
            return true;
        }

        if(strings.length == 1) {
            if(strings[0].equals("stop")) {
                if(this.plugin.mt.players.containsKey((Player)commandSender)) {
                    commandSender.sendMessage(ChatColor.GREEN + "Stopped hunting " + ChatColor.BOLD + this.plugin.mt.players.get((Player)commandSender).getDisplayName() + ChatColor.RESET + "" + ChatColor.GREEN + ".");

                    if(((Player) commandSender).getInventory().contains(compass)) {
                        ((Player) commandSender).getInventory().removeItem(compass);
                    }

                    this.plugin.mt.players.remove((Player)commandSender);

                } else {
                    commandSender.sendMessage(ChatColor.RED + "Not hunting anyone.");
                }

            } else {
                Player target = this.plugin.getServer().getPlayer(strings[0]);

                if(target == null) {
                    commandSender.sendMessage(ChatColor.RED + "Couldn't find player " + strings[0] + ".");

                } else if((Player)commandSender == target) {
                    commandSender.sendMessage(ChatColor.RED + "You can't hunt yourself!");

                } else {
                    commandSender.sendMessage(ChatColor.GREEN + "Started hunting " + ChatColor.BOLD + target.getDisplayName() + ChatColor.RESET + "" + ChatColor.GREEN + ".");

                    if(!(((Player) commandSender).getInventory().contains(compass))) {
                        ((Player) commandSender).getInventory().addItem(compass);
                    }

                    this.plugin.mt.players.remove((Player)commandSender);
                    this.plugin.mt.players.put((Player)commandSender, target);
                }
            }

        } else {
            commandSender.sendMessage(ChatColor.RED + "Invalid usage: /hunt <stop|player>");
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> options = new ArrayList<>();

        if(strings.length == 1) {
            for(Player p: this.plugin.getServer().getOnlinePlayers()) {
                options.add(p.getDisplayName());
            }

            options.add("stop");
        }

        return options;
    }
}
