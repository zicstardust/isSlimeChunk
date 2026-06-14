package com.zicstardust.isSlimeChunk;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public final class Main extends JavaPlugin {

    private static FileConfiguration config;
    
    private final Set<UUID> radarEnabled = new HashSet<>();


    @Override
    public void onEnable() {

        saveDefaultConfig();
        config = getConfig();
        
        loadConfigs();

        Objects.requireNonNull(getCommand("slimechunk")).setExecutor(new SlimeChunkCommand(this));

        getServer().getPluginManager().registerEvents(new ChunkChangeListener(this), this);
    }

    @Override
    public void onDisable() {
        saveConfigs();
    }

    public static FileConfiguration getPluginConfig(){
        return config;
    }
    
    public boolean isRadarEnable(UUID uuid) {
        return radarEnabled.contains(uuid);
    }

    public void setRadarEnable(UUID uuid, boolean enable) {
        if (enable) {
            radarEnabled.add(uuid);
        } else {
            radarEnabled.remove(uuid);
        }
        FileConfiguration config = getConfig();
        config.set("RadarPlayers." + uuid.toString(), enable);
        saveConfig();
    }

    private void loadConfigs() {
        FileConfiguration config = getConfig();
        if (config.contains("RadarPlayers")) {
            for (String uuidStr : Objects.requireNonNull(config.getConfigurationSection("RadarPlayers")).getKeys(false)) {
                if (config.getBoolean("RadarPlayers." + uuidStr)) {
                    radarEnabled.add(UUID.fromString(uuidStr));
                }
            }
        }
    }

    private void saveConfigs() {
        FileConfiguration config = getConfig();
        config.set("RadarPlayers", null);

        for (UUID uuid : radarEnabled) {
            config.set("RadarPlayers." + uuid.toString(), true);
        }
        saveConfig();
    }
}

