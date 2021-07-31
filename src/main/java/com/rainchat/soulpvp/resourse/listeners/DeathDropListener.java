package com.rainchat.soulpvp.resourse.listeners;

import com.rainchat.soulpvp.API.PlayerDeathDropEvent;
import com.rainchat.soulpvp.data.carma.PlayerCarma;
import com.rainchat.soulpvp.data.configs.ConfigSettings;
import com.rainchat.soulpvp.managers.CarmaManager;
import com.rainchat.soulpvp.managers.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class DeathDropListener implements Listener {

    public final CarmaManager carmaManager;

    public DeathDropListener(CarmaManager carmaManager) {
        this.carmaManager = carmaManager;
    }

    @EventHandler
    public void onDrop(PlayerDeathDropEvent e) {
        double chance = ConfigSettings.CARMA_DROP_CHANCE;
        e.setCancelled(!this.drop(chance));
    }

    @EventHandler
    public void onDie(PlayerDeathEvent e) {
        FileConfiguration file = FileManager.Files.CONFIG.getFile();
        List<Integer> slots = file.getIntegerList("itemsNoDrop");

        if (this.SaveDropItem(e.getEntity())) {
            e.getDrops().clear();
            e.setKeepInventory(true);
        } else {
            e.setKeepInventory(false);
            Player p = e.getEntity();
            ItemStack[] inventory = p.getInventory().getArmorContents();

            int a;
            ItemStack i;
            PlayerDeathDropEvent ev;
            ItemStack i2;


            p.getInventory().setArmorContents(inventory);
            inventory = p.getInventory().getContents();

            for (a = 0; a < inventory.length; ++a) {

                i = inventory[a];
                if (i != null && !i.getType().equals(Material.AIR)) {
                    ev = new PlayerDeathDropEvent(p, i, true);
                    Bukkit.getPluginManager().callEvent(ev);
                    i2 = ev.getItem();
                    if (ev.isCancelled()) {
                        inventory[a] = i2;
                    } else if (slots.contains(a)) {
                        continue;
                    } else {
                        inventory[a] = null;
                        p.getWorld().dropItemNaturally(p.getLocation(), i2);
                    }
                }
            }

            p.getInventory().setContents(inventory);
            e.getDrops().clear();
            e.setKeepInventory(true);
        }
    }

    public boolean SaveDropItem(Player player) {
        PlayerCarma playerCarma = carmaManager.getPlayerCarma(player);
        double x = 0;
        if (playerCarma != null) {
            x = playerCarma.getCarma();
        }

        FileConfiguration file = FileManager.Files.CONFIG.getFile();
        Iterator var4 = file.getConfigurationSection("Carma.point-tag").getKeys(false).iterator();

        String s;
        while (var4.hasNext()) {
            s = (String) var4.next();
            if (x >= file.getInt("Carma.point-tag." + s + ".points", 0)) {
                x = file.getInt("Carma.point-tag." + s + ".save-chance", 0);
                break;
            }
        }

        return drop(x);
    }


    public boolean drop(double x) {
        Random rnd = new Random();
        int i = rnd.nextInt(100);

        return i >= x;
    }
}
