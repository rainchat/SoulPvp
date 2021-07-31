package com.rainchat.soulpvp.data.database;


import com.rainchat.soulpvp.data.configs.ConfigSettings;
import com.rainchat.soulpvp.utils.ServerLog;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public class CarmaDate {
    private static final SqlLite database = SqlLite.getInstance();


    public static void addCampFire(Player player) {
        if (isExist(player)) {
            return;
        }
        String syntax;
        try {
            syntax = "INSERT INTO soul_carma (UUID, point) VALUES('"
                    + player.getUniqueId().toString() + "', '"
                    + ConfigSettings.CARMA_MAX_CARMA + "' );";
            PreparedStatement preparedStatement = database.getDatabaseConnection().prepareStatement(syntax);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            ServerLog.log(Level.WARNING, "There was an issue saving the soul_carma to the database (" + e.getMessage() + ").");
        }
    }

    public static void update(Player player, double value) {
        String syntax;
        try {
            syntax = "UPDATE soul_carma Set point='" + value + "' WHERE UUID='" + player.getUniqueId().toString() + "';";
            PreparedStatement preparedStatement = database.getDatabaseConnection().prepareStatement(syntax);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            ServerLog.log(Level.WARNING, "There was an issue saving the soul_carma to the database (" + e.getMessage() + ").");
        }
    }

    public static void addPoint(Player player, double value) {
        String syntax;
        if (getPoint(player) + value > ConfigSettings.CARMA_MAX_CARMA) {
            update(player, ConfigSettings.CARMA_MAX_CARMA);
            return;
        } else if (getPoint(player) + value < 0) {
            update(player, 0);
            return;
        }

        try {
            syntax = "UPDATE soul_carma Set point=point+" + value + " WHERE UUID='" + player.getUniqueId().toString() + "';";
            PreparedStatement preparedStatement = database.getDatabaseConnection().prepareStatement(syntax);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            ServerLog.log(Level.WARNING, "There was an issue saving the soul_carma to the database (" + e.getMessage() + ").");
        }
    }

    public static boolean isExist(Player player) {

        String syntax;
        try {
            syntax = "SELECT * FROM soul_carma WHERE UUID ='"
                    + player.getUniqueId().toString() + "';";
            PreparedStatement preparedStatement = database.getDatabaseConnection().prepareStatement(syntax);
            preparedStatement.execute();
            ResultSet results = preparedStatement.executeQuery();
            if (results.next()) {
                return true;
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            ServerLog.log(Level.WARNING, "There was an issue saving the soul_carma to the database (" + e.getMessage() + ").");
        }
        return false;
    }

    public static int getPoint(Player player) {

        String syntax;
        try {
            syntax = "SELECT * FROM soul_carma WHERE UUID ='"
                    + player.getUniqueId().toString() + "';";
            PreparedStatement preparedStatement = database.getDatabaseConnection().prepareStatement(syntax);
            preparedStatement.execute();
            ResultSet results = preparedStatement.executeQuery();
            if (results.next()) {
                return results.getInt("point");
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            ServerLog.log(Level.WARNING, "There was an issue saving the soul_carma to the database (" + e.getMessage() + ").");
        }
        return 0;
    }

    public static String getTopCarma(int x) {
        String s = null;

        int z = 0;
        String syntax;
        try {
            syntax = "SELECT * FROM soul_carma ORDER BY point ASC LIMIT 10;";
            PreparedStatement preparedStatement = database.getDatabaseConnection().prepareStatement(syntax);
            preparedStatement.execute();
            ResultSet results = preparedStatement.executeQuery();
            while (results.next()) {
                z++;
                if (z == x) {
                    s = results.getString("UUID");
                }
                //return Bukkit.getPlayer(results.getString("UUID")).getName();
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            ServerLog.log(Level.WARNING, "There was an issue saving the soul_carma to the database (" + e.getMessage() + ").");
        }


        return s;
    }


}
