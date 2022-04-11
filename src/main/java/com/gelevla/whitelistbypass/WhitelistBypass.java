package com.gelevla.whitelistbypass;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import net.milkbowl.vault.permission.Permission;

public final class WhitelistBypass extends JavaPlugin implements Listener{

    public static Permission permission = null;

    public WhitelistBypass() {
    }

    public void onEnable() {
        this.setupPermissions();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> permissionProvider = this.getServer().getServicesManager().getRegistration(Permission.class);
        if (permissionProvider != null) {
            permission = (Permission)permissionProvider.getProvider();
        }

        return permission != null;
    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void onKick(PlayerLoginEvent e) {
        if (e.getResult() == PlayerLoginEvent.Result.KICK_WHITELIST && permission.has(e.getPlayer(), "whitelist.bypass")) {
            e.setResult(PlayerLoginEvent.Result.ALLOWED);
        }

    }
}
