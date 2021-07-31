package com.rainchat.soulpvp.managers;

import com.rainchat.soulpvp.data.carma.PlayerCarma;
import com.rainchat.soulpvp.data.database.CarmaDate;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class CarmaManager {

    private final Set<PlayerCarma> playerCarmaSet;

    public CarmaManager() {
        this.playerCarmaSet = new HashSet<>();
    }

    public void loadCarmaData(Player player) {
        PlayerCarma playerCarma = new PlayerCarma(player, 0);
        if (CarmaDate.isExist(player)) {
            playerCarma.setCarma(CarmaDate.getPoint(player));
        }
        addPlayerCarma(playerCarma);
    }

    public void unLoadCarmaData(Player player) {
        PlayerCarma playerCarma = getPlayerCarma(player);
        if (playerCarma != null) {
            CarmaDate.update(player,playerCarma.getCarma());
            removePlayerCarma(player);
        }
    }

    public void addPlayerCarma(PlayerCarma playerCarma) {
        playerCarmaSet.add(playerCarma);
    }

    public void removePlayerCarma(Player player) {
        for (PlayerCarma playerCarma: playerCarmaSet) {
            if (playerCarma.getPlayerUUID().equals(player.getUniqueId())) {
                playerCarmaSet.remove(playerCarma);
                return;
            }
        }
    }

    public PlayerCarma getPlayerCarma(Player player) {
        for (PlayerCarma playerCarma: playerCarmaSet) {
            if (playerCarma.getPlayerUUID().equals(player.getUniqueId())) {
                return playerCarma;
            }
        }
        return null;
    }

    public PlayerCarma getPlayerCarma(UUID uuid) {
        for (PlayerCarma playerCarma: playerCarmaSet) {
            if (playerCarma.getPlayerUUID().equals(uuid)) {
                return playerCarma;
            }
        }
        return null;
    }
}
