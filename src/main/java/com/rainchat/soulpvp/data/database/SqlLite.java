package com.rainchat.soulpvp.data.database;


import com.rainchat.soulpvp.SoulCarma;
import com.rainchat.soulpvp.managers.FileManager;
import com.rainchat.soulpvp.utils.ServerLog;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

public class SqlLite {
    private static final SqlLite instance = new SqlLite();

    private Connection databaseConnection;
    private String carma;

    public static SqlLite getInstance() {
        return instance;
    }

    public void onLoad() {
        FileConfiguration file = FileManager.Files.CONFIG.getFile();


        long databaseStartTime = System.currentTimeMillis();


        carma = "create table if not exists soul_carma ( UUID varchar(100) PRIMARY KEY, point INTEGER)";


        setupDatabase(false);

        if (getDatabaseConnection() != null)
            ServerLog.log(Level.INFO, "Communication to the database was successful. (Took " + (System.currentTimeMillis() - databaseStartTime) + "ms)");
        else {
            ServerLog.log(Level.WARNING, "Communication to the database failed. (Took " + (System.currentTimeMillis() - databaseStartTime) + "ms)");
            SoulCarma.getPluginInstance().getServer().getPluginManager().disablePlugin(SoulCarma.getPluginInstance());
            return;
        }
    }

    private void setupDatabase(boolean useMySQL) {
        if (getDatabaseConnection() != null) return;


        try {
            Statement statement;
            if (!useMySQL) {
                Class.forName("org.sqlite.JDBC");
                setDatabaseConnection(DriverManager.getConnection("jdbc:sqlite:" + SoulCarma.getPluginInstance().getDataFolder() + "/Database.db"));
            }

            statement = getDatabaseConnection().createStatement();
            statement.executeUpdate(carma);

            statement.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            ServerLog.log(Level.WARNING, "There was an issue involving the SQL connection.");
        }
    }

    public void close() throws SQLException {
        if (getDatabaseConnection() != null) {
            getDatabaseConnection().close();
        }
        ServerLog.log(Level.WARNING, "SqlLite closed successfully.");
    }

    public void reloadconnection() throws SQLException {
        if (getDatabaseConnection() != null) {
            getDatabaseConnection().close();
        }
        setDatabaseConnection(DriverManager.getConnection("jdbc:sqlite:" + SoulCarma.getPluginInstance().getDataFolder() + "/Database.db"));
    }


    public Connection getDatabaseConnection() {
        return databaseConnection;
    }

    private void setDatabaseConnection(Connection connection) {
        this.databaseConnection = connection;
    }
}
