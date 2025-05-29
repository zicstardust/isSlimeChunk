package com.zicstardust.isSlimeChunk;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Main extends JavaPlugin {

    private static FileConfiguration config;


    @Override
    public void onEnable() {

        saveDefaultConfig();
        config = getConfig();

        Objects.requireNonNull(getCommand("slimechunk")).setExecutor(new SlimeChunkCommand());
    }


    public static FileConfiguration getPluginConfig(){
        return config;
    }
}
