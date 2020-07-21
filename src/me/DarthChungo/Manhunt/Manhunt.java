/*
   Manhunt plugin by DarthChungo
   MIT License
   Copyright (c) 2020 Antonio de Haro
*/

package me.DarthChungo.Manhunt;

import org.bukkit.plugin.java.JavaPlugin;

public class Manhunt extends JavaPlugin {
    public ManhuntCommand mc;
    public ManhuntTask mt;

    @Override
    public void onEnable() {
        this.mc = new ManhuntCommand(this);

        this.mt = new ManhuntTask();
        this.mt.runTaskTimer(this, 0, 4);

        getLogger().info("Manhunt plugin by DarthChungo enabled.");
    }

     @Override
    public void onDisable() {
        getLogger().info("Manhunt plugin by DarthChungo disabled.");
     }

}
