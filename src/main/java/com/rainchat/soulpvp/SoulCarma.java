package com.rainchat.soulpvp;

import com.rainchat.soulpvp.managers.CarmaManager;
import com.rainchat.soulpvp.managers.FileManager;
import com.rainchat.soulpvp.data.configs.Language;
import com.rainchat.soulpvp.resourse.commands.CommandTab;
import com.rainchat.soulpvp.resourse.commands.Commands;
import com.rainchat.soulpvp.data.database.SqlLite;
import com.rainchat.soulpvp.hook.PlaceholderApiCompatibility;
import com.rainchat.soulpvp.hook.VaultCompatibility;
import com.rainchat.soulpvp.resourse.listeners.PlayerListener;
import com.rainchat.soulpvp.resourse.listeners.DeathDropListener;
import com.rainchat.soulpvp.resourse.listeners.PvpListener;
import com.rainchat.soulpvp.managers.NametagManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class SoulCarma extends JavaPlugin {

    private static SoulCarma pluginInstance;

    private FileManager fileManager = FileManager.getInstance();
    private SqlLite sqlLite = SqlLite.getInstance();
    private NametagManager nametagManager = NametagManager.getInstance();
    private CarmaManager carmaManager;

    @Override
    public void onEnable() {
        setPluginInstance(this);


        String languages = "/Languages";
        fileManager.logInfo(true)
                .registerCustomFilesFolder("/Languages")
                .registerDefaultGenerateFiles("Ru_ru.yml", "/Languages", languages)
                .setup(getPluginInstance());


        sqlLite.onLoad();
        this.getCommand("soulcarma").setExecutor(new Commands(carmaManager));
        this.getCommand("soulcarma").setTabCompleter(new CommandTab());
        Language.addMissingMessages();

        this.nametagManager.registerNametags();
        this.carmaManager = new CarmaManager();


        //hooks setup
        VaultCompatibility.vaultSetup();
        PlaceholderApiCompatibility.PlaceholderSetup();

        this.getServer().getPluginManager().registerEvents(new DeathDropListener(carmaManager), this);
        this.getServer().getPluginManager().registerEvents(new PlayerListener(carmaManager), this);
        this.getServer().getPluginManager().registerEvents(new PvpListener(carmaManager), this);

    }

    @Override
    public void onDisable() {

        try {
            sqlLite.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        for (Player player: Bukkit.getOnlinePlayers()) {
            carmaManager.unLoadCarmaData(player);
        }
        nametagManager.removeAll();
        // Plugin shutdown logic
    }


    public static SoulCarma getPluginInstance() {
        return pluginInstance;
    }

    private static void setPluginInstance(SoulCarma pluginInstance) {
        SoulCarma.pluginInstance = pluginInstance;
    }


}
