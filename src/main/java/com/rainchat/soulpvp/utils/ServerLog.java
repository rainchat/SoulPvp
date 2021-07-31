package com.rainchat.soulpvp.utils;


import com.rainchat.soulpvp.SoulCarma;

import java.util.logging.Level;

public class ServerLog {


    public static void log(Level level, String message) {
        SoulCarma.getPluginInstance().getServer().getLogger().log(level,
                "[" + SoulCarma.getPluginInstance().getDescription().getName() + "] " + message);
    }

}
