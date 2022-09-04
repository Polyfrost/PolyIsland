package cc.polyfrost.islandtils.utils;

import net.minecraft.client.MinecraftClient;

import java.util.Locale;

public class ServerUtils {
    public static MinecraftClient mcInstance = MinecraftClient.getInstance();

    public static boolean isOnMCCIsland() {
        if (mcInstance.world == null || mcInstance.player == null || mcInstance.isInSingleplayer()) return false;

        return mcInstance.player.getServerBrand().toLowerCase(Locale.ENGLISH).contains("mcc");
    }
}
