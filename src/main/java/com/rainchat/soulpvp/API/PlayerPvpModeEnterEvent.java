//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.rainchat.soulpvp.API;

import com.rainchat.soulpvp.SoulCarma;
import com.rainchat.soulpvp.data.configs.ConfigSettings;
import com.rainchat.soulpvp.data.configs.Language;
import com.rainchat.soulpvp.utils.Color;
import com.rainchat.soulpvp.utils.EventCaller;
import com.rainchat.soulpvp.managers.NametagManager;
import java.util.HashMap;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerPvpModeEnterEvent extends Event implements Cancellable {
    private static final HashMap<Player, PlayerPvpModeEnterEvent> playerPlayerPreTeleportEventHashMap = new HashMap();
    private static final HandlerList handlers = new HandlerList();
    private boolean isCancelled = false;
    private final Player player;

    public static void JoinInPvp(Player player, Boolean update) {
        PlayerPvpModeEnterEvent pvpmode = playerPlayerPreTeleportEventHashMap.get(player);
        if (pvpmode != null) {
            pvpmode.setCancelled(true);
        }

        new EventCaller(new PlayerPvpModeEnterEvent(player, ConfigSettings.CARMA_PVP_MODE, update));
    }

    public static boolean isBad(Player player) {
        PlayerPvpModeEnterEvent pvpmode = playerPlayerPreTeleportEventHashMap.get(player);
        return pvpmode != null;
    }

    public static String getPvpTag(Player player) {
        return isBad(player) ? Color.parseHexString(ConfigSettings.CARMA_IS_PVP) : Color.parseHexString(ConfigSettings.CARMA_IS_NONPVP);
    }

    public PlayerPvpModeEnterEvent(final Player player, final int timer, Boolean update) {
        this.player = player;
        if (!update) {
            player.sendMessage(Language.CARMA_JOIN_PVP.getmessage(player, false, player.getName(), String.valueOf(ConfigSettings.CARMA_WHEN_HIT)));
            (new BukkitRunnable() {
                public void run() {
                    NametagManager.getInstance().update(player);
                }
            }).runTaskLater(SoulCarma.getPluginInstance(), 10L);
        }

        playerPlayerPreTeleportEventHashMap.put(player, this);
        (new BukkitRunnable() {
            public void run() {
                if (!PlayerPvpModeEnterEvent.this.isCancelled) {
                    PlayerPvpModeEnterEvent.playerPlayerPreTeleportEventHashMap.remove(player);
                    if (player.isOnline()) {
                        ;
                    }
                }
            }
        }).runTaskLater(SoulCarma.getPluginInstance(), (20 * timer));
        (new BukkitRunnable() {
            int timerLeft = timer;

            public void run() {
                if (PlayerPvpModeEnterEvent.this.isCancelled) {
                    this.cancel();
                } else if (this.timerLeft == 0) {
                    player.sendMessage(Language.CARMA_LEAVE_PVP.getmessage(player, false, player.getName(), String.valueOf(ConfigSettings.CARMA_WHEN_HIT)));
                    (new BukkitRunnable() {
                        public void run() {
                            NametagManager.getInstance().update(player);
                        }
                    }).runTaskLater(SoulCarma.getPluginInstance(), 10L);
                    this.cancel();
                } else {
                    --this.timerLeft;
                }
            }
        }).runTaskTimer(SoulCarma.getPluginInstance(), 0L, 20L);
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
        playerPlayerPreTeleportEventHashMap.remove(this.player);
    }

    public Player getPlayer() {
        return this.player;
    }

    public static class PlayerPreTeleportEventEvents implements Listener {
        public PlayerPreTeleportEventEvents() {
        }

        @EventHandler(
                ignoreCancelled = true
        )
        public void onPlayerMove(PlayerDeathEvent event) {
            PlayerPvpModeEnterEvent pvpmode = (PlayerPvpModeEnterEvent)PlayerPvpModeEnterEvent.playerPlayerPreTeleportEventHashMap.get(event.getEntity());
            if (pvpmode != null) {
                pvpmode.setCancelled(true);
            }
        }
    }
}
