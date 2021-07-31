package com.rainchat.soulpvp.data.carma;

import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerCarma {

    private final UUID playerUUID;
    private double carma;

    public PlayerCarma(Player player, double carma) {
        this.playerUUID = player.getUniqueId();
        this.carma = carma;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public double getCarma() {
        return carma;
    }

    public void setCarma(double carma) {
        this.carma = carma;
    }

    public void addCarma(double value) {
        this.carma = this.carma + value;
    }
}
