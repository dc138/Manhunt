/*
   Manhunt plugin by DarthChungo
   MIT License
   Copyright (c) 2020 Antonio de Haro
*/

package me.DarthChungo.Manhunt;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class ManhuntTask extends BukkitRunnable {
    public final HashMap<Player, Player> players = new HashMap<>();

    @Override
    public void run() {
        for(Player p : players.keySet()) {
            p.setCompassTarget(players.get(p).getLocation());
        }
    }
}
