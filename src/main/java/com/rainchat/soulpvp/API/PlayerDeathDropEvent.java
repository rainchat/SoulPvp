package com.rainchat.soulpvp.API;


import com.rainchat.soulpvp.data.database.CarmaDate;
import com.rainchat.soulpvp.managers.FileManager;
import com.rainchat.soulpvp.utils.WeightedRandomBag;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerDeathDropEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean isCancelled = false;
    private Player p;
    private ItemStack item;
    private boolean cancelled;
    private boolean armor;

    public PlayerDeathDropEvent(Player p, ItemStack item, boolean isArmor) {
        this.p = p;
        this.item = item;
        this.cancelled = false;
        this.armor = isArmor;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public boolean isCancelled() {
        return this.isCancelled;
    }

    public void setCancelled(boolean b) {
        this.isCancelled = b;
    }

    public boolean isArmor() {
        return this.armor;
    }

    public ItemStack getItem() {
        return this.item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public Player getPlayer() {
        return this.p;
    }

}