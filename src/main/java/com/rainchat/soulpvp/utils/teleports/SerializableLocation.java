package com.rainchat.soulpvp.utils.teleports;

import com.rainchat.soulpvp.SoulCarma;
import org.bukkit.Location;

import java.util.Objects;

public class SerializableLocation {
    private double x, y, z;
    private float yaw, pitch;
    private String worldName;

    public SerializableLocation(Location location) {
        setWorldName(Objects.requireNonNull(location.getWorld()).getName());
        setX(location.getX());
        setY(location.getY());
        setZ(location.getZ());
        setYaw(location.getYaw());
        setPitch(location.getPitch());
    }

    public SerializableLocation(String worldName, double x, double y, double z, float yaw, float pitch) {
        setWorldName(worldName);
        setX(x);
        setY(y);
        setZ(z);
        setYaw(yaw);
        setPitch(pitch);
    }

    public Location asBukkitLocation() {
        return new Location(SoulCarma.getPluginInstance().getServer().getWorld(getWorldName()), getX(), getY(), getZ(), getYaw(), getPitch());
    }

    public double distance(double x, double y, double z) {
        final double differenceInX = (Math.max(x, getX()) - Math.min(x, getX())), differenceInY = (Math.max(y, getY()) - Math.min(y, getY())),
                differenceInZ = (Math.max(z, getZ()) - Math.min(z, getZ()));
        return Math.sqrt(Math.pow(differenceInX, 2) + Math.pow(differenceInY, 2) + Math.pow(differenceInZ, 2));
    }

    public double distance(Location location) {
        final double differenceInX = (Math.max(location.getX(), getX()) - Math.min(location.getX(), getX())), differenceInY = (Math.max(location.getY(), getY()) - Math.min(location.getY(), getY())),
                differenceInZ = (Math.max(location.getZ(), getZ()) - Math.min(location.getZ(), getZ()));
        return Math.sqrt(Math.pow(differenceInX, 2) + Math.pow(differenceInY, 2) + Math.pow(differenceInZ, 2));
    }

    public double distance(SerializableLocation location) {
        final double differenceInX = (Math.max(location.getX(), getX()) - Math.min(location.getX(), getX())), differenceInY = (Math.max(location.getY(), getY()) - Math.min(location.getY(), getY())),
                differenceInZ = (Math.max(location.getZ(), getZ()) - Math.min(location.getZ(), getZ()));
        return Math.sqrt(Math.pow(differenceInX, 2) + Math.pow(differenceInY, 2) + Math.pow(differenceInZ, 2));
    }


    public String getWorldName() {
        return worldName;
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    public double getX() {
        return x;
    }

    private void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    private void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    private void setZ(double z) {
        this.z = z;
    }

    public float getYaw() {
        return yaw;
    }

    private void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    private void setPitch(float pitch) {
        this.pitch = pitch;
    }

    /**
     * Gets the location in a string format.
     *
     * @return The converted string format
     */
    @Override
    public String toString() {
        return getWorldName() + ":" + getX() + "," + getY() + "," + getZ() + "," + getYaw() + "," + getPitch();
    }

}

