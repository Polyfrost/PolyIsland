package cc.polyfrost.islandtils.utils;

import cc.polyfrost.islandtils.mixin.transformers.BossBarHudAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ClientBossBar;

import java.util.Collection;
import java.util.Locale;

public class ServerUtils {
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static boolean isOnMCCIsland() {
        if (mc.getCurrentServerEntry() == null || mc.getCurrentServerEntry().address == null) return false;

        return mc.getCurrentServerEntry().address.toLowerCase(Locale.ENGLISH).endsWith("mccisland.net");
    }

    public static Collection<ClientBossBar> getServerBossBars() {
        return ((BossBarHudAccessor) mc.inGameHud.getBossBarHud()).getBossBars().values();
    }
}
