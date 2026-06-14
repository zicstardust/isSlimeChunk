package com.zicstardust.isSlimeChunk;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class ChunkChangeListener implements Listener {

    final TextComponent textEnteredSlimeChunk = Component.text(Objects.requireNonNull(Main.getPluginConfig().getString("Translate.EnteredSlimeChunk")), NamedTextColor.GREEN);

    private final JavaPlugin plugin;

    public ChunkChangeListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Location from = event.getFrom();
        Location to = event.getTo();

        if (to == null) return;

        int fromChunkX = from.getBlockX() >> 4;
        int fromChunkZ = from.getBlockZ() >> 4;

        int toChunkX = to.getBlockX() >> 4;
        int toChunkZ = to.getBlockZ() >> 4;


        if ((fromChunkX != toChunkX || fromChunkZ != toChunkZ)
                && (to.getWorld().getEnvironment().toString().equals("NORMAL"))
                && (to.getChunk().isSlimeChunk())
        ) {
            Player player = event.getPlayer();

            player.sendMessage(textEnteredSlimeChunk);
        }
    }
}