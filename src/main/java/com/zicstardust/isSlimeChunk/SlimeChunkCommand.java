package com.zicstardust.isSlimeChunk;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SlimeChunkCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        final TextComponent textIsSlimeChunk = Component.text(Objects.requireNonNull(Main.getPluginConfig().getString("Translate.IsSlimeChunk")), NamedTextColor.GREEN);
        final TextComponent textNotSlimeChunk = Component.text(Objects.requireNonNull(Main.getPluginConfig().getString("Translate.NotSlimeChunk")), NamedTextColor.RED);
        final TextComponent textNotPlayer = Component.text(Objects.requireNonNull(Main.getPluginConfig().getString("Translate.NotPlayer")), NamedTextColor.RED);
        final TextComponent textNotPermission = Component.text(Objects.requireNonNull(Main.getPluginConfig().getString("Translate.NotPermission")), NamedTextColor.RED);
        final TextComponent textWrongArguments = Component.text(Objects.requireNonNull(Main.getPluginConfig().getString("Translate.WrongArguments")), NamedTextColor.RED)
                .appendNewline().append(Component.text(Objects.requireNonNull(Main.getPluginConfig().getString("Translate.CommandExemple")), NamedTextColor.YELLOW));
        final TextComponent textExecuteOnOverworld = Component.text(Objects.requireNonNull(Main.getPluginConfig().getString("Translate.ExecuteOnOverworld")), NamedTextColor.RED);

        if (sender instanceof Player player){

            if (!player.hasPermission("isSlimeChunk.slimechunk")) {
                player.sendMessage(textNotPermission);
                return true;
            }

            boolean check_chunk;
            if (args.length == 0) {
                check_chunk = player.getChunk().isSlimeChunk();
            } else if ( args.length == 2) {
                check_chunk = new Location(
                            player.getLocation().getWorld(),
                            Double.parseDouble(args[0]),
                            player.getLocation().getY(),
                            Double.parseDouble(args[1])
                        ).getChunk().isSlimeChunk();
            } else {
                player.sendMessage(textWrongArguments);
                return true;
            }

            if (!player.getWorld().getEnvironment().toString().equals("NORMAL")) {
                player.sendMessage(textExecuteOnOverworld);
                return true;
            }

            if (check_chunk){
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
