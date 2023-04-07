package com.github.bitbyte.locationlog;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class LocationLog extends JavaPlugin {

    private int checkTimeTicks;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        checkTimeTicks = 20 * 60 * getConfig().getInt("settings.checktime");
        if (checkTimeTicks <= 0) {
            getLogger().severe("Invalid setting in config file");
        }
        logger();
    }

    @Override
    public void onDisable() {
    }

    private void logPlayerLocations() {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            Location location = player.getLocation();
            double x = location.getX();
            double y = location.getY();
            double z = location.getZ();
            getLogger().info(player.getName() + " is at location: X: " + String.format("%.2f", x) + ", Y: " + String.format("%.2f", y) + ", Z: " + String.format("%.2f", z));
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("loglocations")) {
            logPlayerLocations();
            sender.sendPlainMessage("Logged players locations");
        }
        return false;
    }

    void logger() {
        new BukkitRunnable() {
            @Override
            public void run() {
                logPlayerLocations();
            }
        }.runTaskTimerAsynchronously(this, 0, checkTimeTicks);
    }
}