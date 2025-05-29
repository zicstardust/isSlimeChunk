package com.zicstardust.isSlimeChunk;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("slimechunk").setExecutor(new SlimeChunkCommand());
    }
}

