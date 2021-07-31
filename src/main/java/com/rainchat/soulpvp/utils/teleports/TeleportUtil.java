package com.rainchat.soulpvp.utils.teleports;


import org.bukkit.Location;

public class TeleportUtil {

    static public String loctoString(Location loc) {
        SerializableLocation serloc = new SerializableLocation(loc);
        return serloc.toString();
    }

    static public Location getloc(String locationString) {
        String[] worldSplit = locationString.split(":"), coordSplit = worldSplit[1].split(",");
        return new SerializableLocation(worldSplit[0], Double.parseDouble(coordSplit[0]), Double.parseDouble(coordSplit[1]),
                Double.parseDouble(coordSplit[2]), (float) Double.parseDouble(coordSplit[3]), (float) Double.parseDouble(coordSplit[4])).asBukkitLocation();
    }

}
