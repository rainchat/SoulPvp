//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.rainchat.soulpvp.managers;

import com.rainchat.soulpvp.SoulCarma;
import com.rainchat.soulpvp.utils.ServerLog;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;


import com.rainchat.soulpvp.utils.objects.Nametag;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class NametagManager {
    private static NametagManager nametagManager = new NametagManager();
    private final List<Nametag> nametags = new LinkedList();
    private int refreshTask;

    public NametagManager() {
    }

    public static NametagManager getInstance() {
        return nametagManager;
    }

    public void registerNametags() {

        Bukkit.getScheduler().cancelTask(this.refreshTask);
        for (Player player: Bukkit.getOnlinePlayers()){
            addPlayer(player);
        }



        int refreshRate = 40;
        ServerLog.log(Level.INFO, "Refreshing nametags every '" + String.valueOf(refreshRate) + "'. ticks.");
        this.refreshTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(SoulCarma.getPluginInstance(),
                this::refreshForAll,
                (long)refreshRate, (long)refreshRate);
    }

    public void refreshForAll() {
        for (Nametag tag: nametags){
            tag.update();
        }
    }

    public void update(Player player) {
        for (Nametag tag: nametags){
            if (tag.getName().equals(player.getName())){
                tag.update();
            }
        }
    }

    public void addPlayer(Player player){
        nametags.add(new Nametag(player,"test",player.getScoreboard()));
    }

    public void removePlayer(Player player){
        nametags.removeIf(nametag -> nametag.getName().equals(player.getName()));
    }


    public void removeAll() {
        for (Nametag nametag : nametags){
            nametags.remove(nametag);
        }
    }
}
