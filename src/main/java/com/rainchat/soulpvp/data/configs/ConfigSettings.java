package com.rainchat.soulpvp.data.configs;


import com.rainchat.soulpvp.SoulCarma;
import com.rainchat.soulpvp.managers.FileManager;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ConfigSettings {


    public static String LANGUAGE = "Ru_ru";
    public static double CARMA_MAX_CARMA = 1000;
    public static double CARMA_DROP_CHANCE = 50;
    public static double CARMA_WHEN_HIT = -25;
    public static double CARMA_WHEN_KILL = -250;
    public static int CARMA_PVP_MODE = 15;
    public static String CARMA_IS_NONPVP = "";
    public static String CARMA_IS_PVP = "&e";

    public static List<Integer> ITEMS_NO_DROP;



    ConfigSettings() {
        FileConfiguration config = FileManager.Files.CONFIG.getFile();
        LANGUAGE = config.getString("Settings.language", "Ru_ru");
        createLanguage();

        CARMA_MAX_CARMA = config.getDouble("WorldGuard.ignores-regions", 1000);
        CARMA_DROP_CHANCE = config.getDouble("Settings-Claim.enabled-worlds", 50);
        CARMA_WHEN_HIT = config.getDouble("Settings-Claim.default-title-description", -25);
        CARMA_WHEN_KILL = config.getDouble("Settings-Claim.expiration.time", 5);

        CARMA_PVP_MODE = config.getInt("WorldGuard.ignores-regions");
        CARMA_IS_NONPVP = config.getString("Settings-Claim.enabled-worlds");
        CARMA_IS_PVP = config.getString("Settings-Claim.default-title-description", "A peaceful settlement.");
    }


    private static void createLanguage() {
        File file = new File(SoulCarma.getPluginInstance().getDataFolder(), "language" + File.separator + LANGUAGE + ".yml");
        try {
            if (file.createNewFile()) {
                System.out.println(LANGUAGE + ".yml" + " was successfully created!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
