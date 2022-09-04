package cc.polyfrost.islandtils.api;

import cc.polyfrost.islandtils.Islandtils;
import org.jetbrains.annotations.Nullable;

public class ServerAPI {
    public static String @Nullable [] getPlayerNames() {
        if (Islandtils.SERVER_INSTANCE == null) return null;
        return Islandtils.SERVER_INSTANCE.getPlayerNames();
    }

    public static @Nullable net.minecraft.scoreboard.ServerScoreboard getScoreboard() {
        if (Islandtils.SERVER_INSTANCE == null) return null;
        return Islandtils.SERVER_INSTANCE.getScoreboard();
    }

    public static @Nullable String getServerName() {
        if (Islandtils.SERVER_INSTANCE == null) return null;
        return Islandtils.SERVER_INSTANCE.getName();
    }

    public static @Nullable String getServerVersion() {
        if (Islandtils.SERVER_INSTANCE == null) return null;
        return Islandtils.SERVER_INSTANCE.getVersion();
    }

    public static @Nullable String getServerMOTD() {
        if (Islandtils.SERVER_INSTANCE == null) return null;
        return Islandtils.SERVER_INSTANCE.getServerMotd();
    }

    public static @Nullable String getServerIP() {
        if (Islandtils.SERVER_INSTANCE == null) return null;
        return Islandtils.SERVER_INSTANCE.getServerIp();
    }

    public static int getServerPort() {
        if (Islandtils.SERVER_INSTANCE == null) return -1;
        return Islandtils.SERVER_INSTANCE.getServerPort();
    }

    public static int getServerMaxPlayers() {
        if (Islandtils.SERVER_INSTANCE == null) return -1;
        return Islandtils.SERVER_INSTANCE.getMaxPlayerCount();
    }

    public static int getServerCurrentPlayers() {
        if (Islandtils.SERVER_INSTANCE == null) return -1;
        return Islandtils.SERVER_INSTANCE.getCurrentPlayerCount();
    }

    public static @Nullable java.util.Collection<net.minecraft.entity.boss.CommandBossBar> getServerBossBars() {
        if (Islandtils.SERVER_INSTANCE == null) return null;
        return Islandtils.SERVER_INSTANCE.getBossBarManager().getAll();
    }
}
