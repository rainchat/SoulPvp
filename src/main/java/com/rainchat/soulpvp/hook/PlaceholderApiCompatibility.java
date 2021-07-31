package com.rainchat.soulpvp.hook;

import com.rainchat.soulpvp.API.PlayerPvpModeEnterEvent;
import com.rainchat.soulpvp.data.carma.PlayerCarma;
import com.rainchat.soulpvp.managers.CarmaManager;
import com.rainchat.soulpvp.managers.FileManager;
import com.rainchat.soulpvp.utils.ServerLog;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class PlaceholderApiCompatibility {

    public static boolean PLACEHOLDER_ENABLED = false;
    private static CarmaManager carmaManager;

    public static void PlaceholderSetup(CarmaManager manager) {
        carmaManager = manager;

        if (Bukkit.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            Bukkit.getLogger().info("PlaceholderAPI found and enable.");
            PLACEHOLDER_ENABLED = true;
        } else {
            ServerLog.log(Level.WARNING, "PlaceholderAPI not found.");
        }
    }

    public static String setPlaceholders(Player player, String text) {
        if (PLACEHOLDER_ENABLED) {
            text = myPlaceholder(text, player);
            return PlaceholderAPI.setPlaceholders(player, text);
        } else {
            text = myPlaceholder(text, player);
            return text;
        }
    }

    public static String myPlaceholder(String text, Player player) {
        PlayerCarma playerCarma = carmaManager.getPlayerCarma(player);

        text = text.replace("%scamp_pvp_teg%", PlayerPvpModeEnterEvent.getPvpTag(player));
        text = text.replace("%scamp_getcarma%", String.valueOf(playerCarma.getCarma()));

        return text;


    }

    public static String getTeg(Player player) {
        PlayerCarma playerCarma = carmaManager.getPlayerCarma(player);

        FileConfiguration file = FileManager.Files.CONFIG.getFile();
        for (String path : file.getConfigurationSection("Carma.point-tag").getKeys(false)) {
            if (file.getInt("Carma.point-tag." + path + ".points") <= playerCarma.getCarma()) {
                return file.getString("Carma.point-tag." + path + ".color");
            }
        }
        return "";
    }

}
