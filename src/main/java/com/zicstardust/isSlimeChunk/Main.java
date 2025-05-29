package com.zicstardust.isSlimeChunk;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    final TextComponent textIsSlimeChunk = Component.text("Is Slime Chunk", NamedTextColor.GREEN);
    final TextComponent textNotSlimeChunk = Component.text("Not Slime Chunk", NamedTextColor.RED);
    final TextComponent textNotPlayer = Component.text("You must be a player to use this command", NamedTextColor.RED);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("IsSlimeChunk")) {

            if (sender instanceof Player player){

                if (player.getChunk().isSlimeChunk()){
                    player.sendMessage(textIsSlimeChunk);
                } else {
                    player.sendMessage(textNotSlimeChunk);
                }
            }

        } else {
                sender.sendMessage(textNotPlayer);
        }
        return true;
    }
}

