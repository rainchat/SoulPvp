package com.rainchat.soulpvp.data.configs;


import com.rainchat.soulpvp.hook.PlaceholderApiCompatibility;
import com.rainchat.soulpvp.managers.FileManager;
import com.rainchat.soulpvp.utils.Color;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;


public enum Language {

    PREFFIX("Preffix", "&f[&bSoulParkour&f] "),
    NO_PERMISSION("Message.global.No-Permissions", "&cУ вас нет прав чтобы использовать эту команду"),
    RELOAD("Message.global.Reload", "&7Все конфиги перезагружены.."),
    CARMA_CHECK_PLAYER("Message.carma.Check-self-carma", "&7Ваши очки кармы равны &e%arg2%&7!"),
    CARMA_CHECK_OTHER_PLAYER("Message.carma.Check-player-carma", "&7Очки порядочности у игрока &e%arg1% &7равны &e%arg2% "),
    CARMA_ADD_PLAYER("Message.carma.Add-player-carma", "&7Игроку &e%arg1% &7было добавлено &e%arg2% &7очков кармы"),
    CARMA_SET_PLAYER("Message.carma.Set-player-carma", "&7Игроку &e%arg1% &7были установлены очки кармы &e%arg2%!"),
    CARMA_TOP_LINE("Message.carma.carma-top-line", "&7------- &cТоп худших &7----------"),
    CARMA_TOP_BAD("Message.carma.carma-top-bad", "&7%arg1%. &f%arg2% - &c%arg3%&7 кармы!"),
    CARMA_JOIN_PVP("Message.carma.On-pvp-join", "&7Прекратите избивать людей, зачем вы это делаете!"),
    CARMA_LEAVE_PVP("Message.carma.On-pvp-leave", "&7Вы вышли из пвп, больше не деритесь");


    private final String path;
    private String defaultMessage;
    private List<String> defaultListMessage;

    Language(String path, String defaultMessage) {
        this.path = path;
        this.defaultMessage = defaultMessage;
    }

    Language(String path, List<String> defaultListMessage) {
        this.path = path;
        this.defaultListMessage = defaultListMessage;
    }

    public static String convertList(List<String> list) {
        String message = "";
        for (String line : list) {
            message += Color.parseHexString(line) + "\n";
        }
        return message;
    }

    private boolean isList() {
        if (FileManager.getInstance().getLanguage().getFile().contains(path)) {
            return !FileManager.getInstance().getLanguage().getFile().getStringList(path).isEmpty();
        } else {
            return defaultMessage == null;
        }
    }


    private boolean exists() {
        return FileManager.getInstance().getLanguage().getFile().contains(path);
    }

    public static void addMissingMessages() {
        FileConfiguration messages = FileManager.getInstance().getLanguage().getFile();
        boolean saveFile = false;
        for (Language message : values()) {
            if (!messages.contains(message.path)) {
                saveFile = true;
                if (message.defaultMessage != null) {
                    messages.set(message.path, message.defaultMessage);
                } else if (message.defaultListMessage != null) {
                    messages.set(message.path, message.defaultListMessage);
                }
            }
            if (saveFile) {
                FileManager.getInstance().getLanguage().saveFile();
            }
        }
    }


    public String getmessage(Boolean preffix) {

        String message = "";
        boolean isList = isList();
        boolean exists = exists();
        if (isList) {
            if (exists) {
                message = convertList(FileManager.getInstance().getLanguage().getFile().getStringList(path));
            } else {
                message = convertList(getDefaultListMessage());
            }
        } else {
            if (exists) {
                message = Color.parseHexString(FileManager.getInstance().getLanguage().getFile().getString(path, getDefaultMessage()));
            }
        }


        if (preffix) {
            return Color.parseHexString(FileManager.getInstance().getLanguage().getFile().getString("Preffix", "&f[&bSoulParkour&f] ") + message);
        }
        return message;
    }


    public String getmessage(Player player, Boolean preffix, String arg1, String arg2) {

        String message = "";
        boolean isList = isList();
        boolean exists = exists();
        if (isList) {
            if (exists) {
                message = convertList(FileManager.getInstance().getLanguage().getFile().getStringList(path));
            } else {
                message = convertList(getDefaultListMessage());
            }
        } else {
            if (exists) {
                message = Color.parseHexString(FileManager.getInstance().getLanguage().getFile().getString(path, getDefaultMessage()));
            }
        }
        PlaceholderApiCompatibility.setPlaceholders(player, message);
        message = getPlaceholder(message, arg1, arg2);

        if (preffix) {
            return Color.parseHexString(FileManager.getInstance().getLanguage().getFile().getString("Preffix", "&f[&bSoulParkour&f] ") + message);
        }
        return message;
    }

    public String getmessage(Player player, Boolean preffix, String arg1, String arg2, String arg3) {

        String message = "";
        boolean isList = isList();
        boolean exists = exists();
        if (isList) {
            if (exists) {
                message = convertList(FileManager.getInstance().getLanguage().getFile().getStringList(path));
            } else {
                message = convertList(getDefaultListMessage());
            }
        } else {
            if (exists) {
                message = Color.parseHexString(FileManager.getInstance().getLanguage().getFile().getString(path, getDefaultMessage()));
            }
        }
        PlaceholderApiCompatibility.setPlaceholders(player, message);
        message = getPlaceholder(message, arg1, arg2, arg3);

        if (preffix) {
            return Color.parseHexString(FileManager.getInstance().getLanguage().getFile().getString("Preffix", "&f[&bSoulParkour&f] ") + message);
        }
        return message;
    }


    private String getPlaceholder(String text, String arg1, String arg2) {

        if (arg1 != null) {
            text = text.replace("%arg1%", arg1);
        }
        if (arg2 != null) {
            text = text.replace("%arg2%", arg2);
        }
        return text;

    }

    private String getPlaceholder(String text, String arg1, String arg2, String arg3) {

        if (arg1 != null) {
            text = text.replace("%arg1%", arg1);
        }
        if (arg2 != null) {
            text = text.replace("%arg2%", arg2);
        }
        if (arg3 != null) {
            text = text.replace("%arg3%", arg3);
        }
        return text;

    }


    private String getPath() {
        return path;
    }

    private String getDefaultMessage() {
        return defaultMessage;
    }

    private List<String> getDefaultListMessage() {
        return defaultListMessage;
    }

}
