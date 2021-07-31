package com.rainchat.soulpvp;

import com.rainchat.soulpvp.data.configs.Language;
import com.rainchat.soulpvp.data.database.SqlLite;
import com.rainchat.soulpvp.hook.PlaceholderApiCompatibility;
import com.rainchat.soulpvp.hook.VaultCompatibility;
import com.rainchat.soulpvp.managers.CarmaManager;
import com.rainchat.soulpvp.managers.FileManager;
import com.rainchat.soulpvp.managers.NametagManager;
import com.rainchat.soulpvp.resourse.commands.CommandTab;
import com.rainchat.soulpvp.resourse.commands.Commands;
import com.rainchat.soulpvp.resourse.listeners.DeathDropListener;
import com.rainchat.soulpvp.resourse.listeners.PlayerListener;
import com.rainchat.soulpvp.resourse.listeners.PvpListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class SoulCarma extends JavaPlugin {

    private static SoulCarma pluginInstance;

    private final FileManager fileManager = FileManager.getInstance();
    private final SqlLite sqlLite = SqlLite.getInstance();
    private final NametagManager nametagManager = NametagManager.getInstance();
    private CarmaManager carmaManager;

    public static SoulCarma getPluginInstance() {
        return pluginInstance;
    }

    private static void setPluginInstance(SoulCarma pluginInstance) {
        SoulCarma.pluginInstance = pluginInstance;
    }

    @Override
    public void onEnable() {
        setPluginInstance(this);


        String languages = "/Languages";
        fileManager.logInfo(true)
                .registerCustomFilesFolder("/Languages")
                .registerDefaultGenerateFiles("Ru_ru.yml", "/Languages", languages)
                .setup(getPluginInstance());

        this.carmaManager = new CarmaManager();
        this.nametagManager.registerNametags();

        sqlLite.onLoad();
        this.getCommand("soulcarma").setExecutor(new Commands(carmaManager));
        this.getCommand("soulcarma").setTabCompleter(new CommandTab());
        Language.addMissingMessages();

        //hooks setup
        VaultCompatibility.vaultSetup();
        PlaceholderApiCompatibility.PlaceholderSetup(carmaManager);

        this.getServer().getPluginManager().registerEvents(new DeathDropListener(carmaManager), this);
        this.getServer().getPluginManager().registerEvents(new PlayerListener(carmaManager), this);
        this.getServer().getPluginManager().registerEvents(new PvpListener(carmaManager), this);

        for (Player player : Bukkit.getOnlinePlayers()) {
            carmaManager.loadCarmaData(player);
        }
    }

    @Override
    public void onDisable() {

        for (Player player : Bukkit.getOnlinePlayers()) {
            carmaManager.unLoadCarmaData(player);
        }

        try {
            sqlLite.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        nametagManager.removeAll();
        // Plugin shutdown logic
    }


}
