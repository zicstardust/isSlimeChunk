package com.zicstardust.isSlimeChunk;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SlimeChunkCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        final TextComponent textIsSlimeChunk = Component.text(Main.getPluginConfig().getString("Translate.IsSlimeChunk"), NamedTextColor.GREEN);
        final TextComponent textNotSlimeChunk = Component.text(Main.getPluginConfig().getString("Translate.NotSlimeChunk"), NamedTextColor.RED);
        final TextComponent textNotPlayer = Component.text(Main.getPluginConfig().getString("Translate.NotPlayer"), NamedTextColor.RED);
        final TextComponent textNotPermission = Component.text(Main.getPluginConfig().getString("Translate.NotPermission"), NamedTextColor.RED);

        if (sender instanceof Player player){

            if (!player.hasPermission("isSlimeChunk.slimechunk")) {
                player.sendMessage(textNotPermission);
                return true;
            }

            if (player.getChunk().isSlimeChunk()){
                player.sendMessage(textIsSlimeChunk);
            } else {
                player.sendMessage(textNotSlimeChunk);
            }

        } else {
            sender.sendMessage(textNotPlayer);
        }

        return true;
    }
}
