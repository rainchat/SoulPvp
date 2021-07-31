package com.rainchat.soulpvp.resourse.listeners;

import com.rainchat.soulpvp.API.PlayerPvpModeEnterEvent;
import com.rainchat.soulpvp.SoulCarma;
import com.rainchat.soulpvp.data.carma.PlayerCarma;
import com.rainchat.soulpvp.data.configs.ConfigSettings;
import com.rainchat.soulpvp.managers.CarmaManager;
import com.rainchat.soulpvp.managers.FileManager;
import com.rainchat.soulpvp.managers.NametagManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;


public class PvpListener implements Listener {


    public final CarmaManager carmaManager;

    public PvpListener(CarmaManager carmaManager) {
        this.carmaManager = carmaManager;
    }

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent e)  {
        if (!(e.getDamager() instanceof Player) && !(e.getEntity() instanceof Player)) return;

        Player damager = (Player)e.getDamager();
        Player player = (Player)e.getEntity();

        PlayerCarma playerCarma = carmaManager.getPlayerCarma(player);
        PlayerCarma damagerCarma = carmaManager.getPlayerCarma(damager);

        if (!PlayerPvpModeEnterEvent.isBad(player)) {
            PlayerPvpModeEnterEvent.JoinInPvp(damager, PlayerPvpModeEnterEvent.isBad(damager));
        } else if (PlayerPvpModeEnterEvent.isBad(player) && playerCarma.getCarma() >= ConfigSettings.CARMA_MAX_CARMA){

        } else if (damagerCarma.getCarma() <= ConfigSettings.CARMA_MAX_CARMA) {
            PlayerPvpModeEnterEvent.JoinInPvp(damager, PlayerPvpModeEnterEvent.isBad(damager));
        }



    }

    @EventHandler
    public void onEntityKill(EntityDeathEvent e) {
        LivingEntity mob = e.getEntity();
        if (mob.getKiller() != null) {
            Player killer = mob.getKiller();
            PlayerCarma killerCarma = carmaManager.getPlayerCarma(killer);

            if (mob instanceof Player) {
                Player player = (Player)mob;

                PlayerCarma playerCarma = carmaManager.getPlayerCarma(player);


                if (killerCarma.getCarma() == ConfigSettings.CARMA_MAX_CARMA && playerCarma.getCarma() < ConfigSettings.CARMA_MAX_CARMA) {
                    return;
                }

                if (PlayerPvpModeEnterEvent.isBad(player) && playerCarma.getCarma() != ConfigSettings.CARMA_MAX_CARMA) {
                    if (killerCarma.getCarma() <= ConfigSettings.CARMA_MAX_CARMA) {
                        if (PlayerPvpModeEnterEvent.isBad(killer)) {
                            PlayerPvpModeEnterEvent.JoinInPvp(killer, true);
                        } else {
                            PlayerPvpModeEnterEvent.JoinInPvp(killer, false);
                        }

                        (new BukkitRunnable() {
                            public void run() {
                                NametagManager.getInstance().update(killer);
                            }
                        }).runTaskLater(SoulCarma.getPluginInstance(), 10L);
                        killerCarma.addCarma(ConfigSettings.CARMA_WHEN_KILL);
                    }
                } else {
                    if (PlayerPvpModeEnterEvent.isBad(killer)) {
                        PlayerPvpModeEnterEvent.JoinInPvp(killer, true);
                    } else {
                        PlayerPvpModeEnterEvent.JoinInPvp(killer, false);
                    }

                    (new BukkitRunnable() {
                        public void run() {
                            NametagManager.getInstance().update(killer);
                        }
                    }).runTaskLater(SoulCarma.getPluginInstance(), 10L);
                    killerCarma.addCarma(ConfigSettings.CARMA_WHEN_KILL);
                }
            } else {
                FileConfiguration file = FileManager.Files.CARMA_MOB.getFile();
                if (file.getConfigurationSection("mobs").getKeys(true).contains(mob.getType().getName().toUpperCase())) {
                    killerCarma.addCarma(file.getInt("mobs." + mob.getType().getName().toUpperCase()));
                }
            }

        }
    }

}
