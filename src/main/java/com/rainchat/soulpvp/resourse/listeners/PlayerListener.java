package com.rainchat.soulpvp.resourse.listeners;

import com.rainchat.soulpvp.managers.CarmaManager;
import com.rainchat.soulpvp.managers.NametagManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    public final CarmaManager carmaManager;

    public PlayerListener(CarmaManager carmaManager) {
        this.carmaManager = carmaManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        Player player = e.getPlayer();
        carmaManager.loadCarmaData(player);
        NametagManager.getInstance().addPlayer(player);

    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        carmaManager.unLoadCarmaData(player);
        NametagManager.getInstance().removePlayer(player);
    }


}
