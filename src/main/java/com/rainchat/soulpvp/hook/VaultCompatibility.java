package com.rainchat.soulpvp.hook;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.UUID;

public class VaultCompatibility {

    public static boolean VAULT_ENABLED = true;
    private static Economy econ = null;
    private static Permission perms = null;
    private static Chat chat = null;

    public static void vaultSetup() {
        if (Bukkit.getServer().getPluginManager().isPluginEnabled("Vault")) {
            Bukkit.getLogger().info("Vault detected.");
            VAULT_ENABLED = true;
            VaultCompatibility.setupEconomy();
            VaultCompatibility vaultCompatibility = new VaultCompatibility();
            //vaultCompatibility.setupChat();
            vaultCompatibility.setupPermissions();

        }
    }

    public static void setupEconomy() {
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
            return;
        }
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return;
        }
        econ = rsp.getProvider();
    }

    public static void addVaultCurrency(UUID user, double amount) {
        econ.depositPlayer(Bukkit.getOfflinePlayer(user), amount);
    }

    public static void subtractCurrency(UUID user, double amount) {
        econ.withdrawPlayer(Bukkit.getOfflinePlayer(user), amount);
    }

    public static double checkCurrency(UUID user) {
        double currency = 0;
        try {
            currency = econ.getBalance(Bukkit.getOfflinePlayer(user));
        } catch (NullPointerException e) {
            Bukkit.getLogger().warning("[SoulCore] Player tried to check currency when they had no economy entry" +
                    "associated to them. This is an issue with your Vault/economy implementation.");
        }
        return currency;

    }

    public static Economy getEconomy() {
        return econ;
    }

    public static Permission getPermissions() {
        return perms;
    }

    public static Chat getChat() {
        return chat;
    }

    public boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = Bukkit.getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    public boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = Bukkit.getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }

}
