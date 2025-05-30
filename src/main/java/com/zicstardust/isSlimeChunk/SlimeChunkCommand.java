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
import java.util.regex.Pattern;

public class SlimeChunkCommand implements CommandExecutor {

    public enum Position  {
        X,
        Z
    }

    private double positionValidator(@NotNull Position position,  @NotNull String argument,  @NotNull Player player) {

        if (argument.equals("~")){
            if (position.equals(Position.X)){
                return player.getLocation().getX();
            } else {
                return player.getLocation().getZ();
            }
        }
        return Double.parseDouble(argument);
    }



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

                if (((!Pattern.matches("[0-9]+", args[0])) && (!args[0].equals("~"))) || ((!Pattern.matches("[0-9]+", args[1])) && (!args[1].equals("~")))) {
                    player.sendMessage(textWrongArguments);
                    return true;
                }

                check_chunk = new Location(
                        player.getLocation().getWorld(),
                        positionValidator(Position.X, args[0], player),
                        player.getLocation().getY(),
                        positionValidator(Position.Z, args[1], player)
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
