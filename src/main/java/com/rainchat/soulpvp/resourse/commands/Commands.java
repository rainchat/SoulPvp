package com.rainchat.soulpvp.resourse.commands;


import com.rainchat.soulpvp.data.carma.PlayerCarma;
import com.rainchat.soulpvp.data.configs.Language;
import com.rainchat.soulpvp.data.database.CarmaDate;
import com.rainchat.soulpvp.data.database.SqlLite;
import com.rainchat.soulpvp.managers.CarmaManager;
import com.rainchat.soulpvp.managers.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.UUID;

public class Commands implements CommandExecutor {
    public final CarmaManager carmaManager;
    private final FileManager fileManager = FileManager.getInstance();
    private final SqlLite sqlLite = SqlLite.getInstance();

    public Commands(CarmaManager carmaManager) {
        this.carmaManager = carmaManager;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            if (args[0].equalsIgnoreCase("reload")) {
                if (args.length > 1) {
                    System.out.println(Language.RELOAD.getmessage(true));
                    return true;
                }
                try {
                    reload();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

            return true;
        }
        if (args.length == 0) {
            return true;
        }
        Player p = (Player) sender;
        if (args[0].equalsIgnoreCase("reload")) {

            if (args.length > 1) {
                return false;
            }
            if (p.hasPermission("soulcamp.admin.reload")) {
                try {
                    reload();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                FileManager.Files.CONFIG.relaodFile();
                p.sendMessage(Language.RELOAD.getmessage(true));
            } else {
                p.sendMessage(Language.NO_PERMISSION.getmessage(true));
            }
        }


        if (args[0].equals("carma")) {
            try {
                return commandCarma(args, p);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return true;
    }

    public boolean commandCarma(String[] args, Player player) throws SQLException {
        if (args.length == 2) {
            if (args[1].equals("check") && player.hasPermission("soulcamp.command.camp.check_other")) {
                PlayerCarma playerCarma = carmaManager.getPlayerCarma(player);
                player.sendMessage(Language.CARMA_CHECK_PLAYER.getmessage(player, true, player.getName(), String.valueOf(playerCarma.getCarma())));
            }
            if (args[1].equals("top") && player.hasPermission("soulcamp.command.camp.top_bad")) {
                player.sendMessage(Language.CARMA_TOP_LINE.getmessage(false));
                for (int i = 1; i < 11; i++) {
                    String s = CarmaDate.getTopCarma(i);
                    if (s == null) {
                        player.sendMessage(Language.CARMA_TOP_BAD.getmessage(player, false, i + "", "null", "null"));
                    } else {
                        OfflinePlayer player1 = Bukkit.getOfflinePlayer(UUID.fromString(s));
                        player.sendMessage(Language.CARMA_TOP_BAD.getmessage(player1.getPlayer(), false, i + "", player1.getName(), String.valueOf(CarmaDate.getPoint(player1.getPlayer()))));
                    }
                }
                return true;
            }
        }


        if (args.length == 3) {
            if (args[1].equals("check") && Bukkit.getPlayer(args[2]) != null && player.hasPermission("soulcamp.command.camp.check_other")) {
                Player player1 = Bukkit.getPlayer(args[2]);
                PlayerCarma playerCarma = carmaManager.getPlayerCarma(player1);

                player.sendMessage(Language.CARMA_CHECK_OTHER_PLAYER.getmessage(player, true, args[2], String.valueOf(playerCarma.getCarma())));
                return true;
            }
        }

        if (args.length == 4) {
            if (args[1].equals("add") && Bukkit.getPlayer(args[2]) != null && player.hasPermission("soulcamp.command.camp.add")) {
                Player player1 = Bukkit.getPlayer(args[2]);
                PlayerCarma playerCarma = carmaManager.getPlayerCarma(player1);
                playerCarma.addCarma(Integer.parseInt(args[3]));
                player.sendMessage(Language.CARMA_ADD_PLAYER.getmessage(player, true, args[2], args[3]));
                return true;
            }
            if (args[1].equals("set") && Bukkit.getPlayer(args[2]) != null && player.hasPermission("soulcamp.command.camp.set")) {
                Player player1 = Bukkit.getPlayer(args[2]);
                PlayerCarma playerCarma = carmaManager.getPlayerCarma(player1);
                playerCarma.setCarma(Integer.parseInt(args[3]));
                player.sendMessage(Language.CARMA_SET_PLAYER.getmessage(player, true, args[2], args[3]));
                return true;
            }
        }

        return true;
    }


    public void reload() throws SQLException {
        sqlLite.reloadconnection();

        fileManager.reloadAllFiles();
    }
}
