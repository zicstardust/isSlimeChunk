package com.zicstardust.isSlimeChunk;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SlimeChunkCommand implements CommandExecutor, TabCompleter {
    private final Main plugin;

    public SlimeChunkCommand(Main plugin) {
        this.plugin = plugin;
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
        final TextComponent textRadarEnabled = Component.text(Objects.requireNonNull(Main.getPluginConfig().getString("Translate.RadarEnabled")), NamedTextColor.GREEN);
        final TextComponent textRadarDisabled = Component.text(Objects.requireNonNull(Main.getPluginConfig().getString("Translate.RadarDisabled")), NamedTextColor.RED);

        if (!(sender instanceof Player player)) {
            sender.sendMessage(textNotPlayer);
            return true;
        }


        if (!player.hasPermission("isSlimeChunk.slimechunk")) {
            player.sendMessage(textNotPermission);
            return true;
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("radar")) {
            if (args[1].equalsIgnoreCase("on")) {
                plugin.setRadarEnable(player.getUniqueId(), true);
                player.sendMessage(textRadarEnabled);
            }
            else if (args[1].equalsIgnoreCase("off")) {
                plugin.setRadarEnable(player.getUniqueId(), false);
                player.sendMessage(textRadarDisabled);
            }
            return true;
        }  else if (args.length == 0) {
            if (!player.getWorld().getEnvironment().toString().equals("NORMAL")) {
                player.sendMessage(textExecuteOnOverworld);
                return true;
            }

            if (player.getChunk().isSlimeChunk()){
                player.sendMessage(textIsSlimeChunk);
            } else {
                player.sendMessage(textNotSlimeChunk);
            }

        } else {
            player.sendMessage(textWrongArguments);
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(@NonNull CommandSender sender, @NonNull Command cmd, @NonNull String alias, String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1) {
            list.add("radar");
        } else if (args.length == 2 && args[0].equalsIgnoreCase("radar")) {
            list.add("on");
            list.add("off");
        }
        return list;
    }
}
