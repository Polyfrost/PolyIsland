package cc.polyfrost.islandtils;

import cc.polyfrost.islandtils.utils.ServerUtils;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.SharedConstants;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Islandtils implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("islandtils");
    public static String PACK_URL = null;

    public static ServerPlayNetworkHandler SERVER_HANDLER = null;
    public static PacketSender SERVER_SENDER = null;
    public static MinecraftServer SERVER_INSTANCE = null;

    public static boolean onMCCIslandState = false;

    @Override
    public void onInitializeClient() {
        LOGGER.info("Starting Islandtils 1.0.0 on " + SharedConstants.getGameVersion().getName());

        LOGGER.info("Registering server connection listener");
        ServerPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            if (ServerUtils.isOnMCCIsland()) {
                LOGGER.info("Connected to MCC Island server, sending connect packet.");
                onMCCIslandState = true;
                SERVER_HANDLER = handler;
                SERVER_SENDER = sender;
                SERVER_INSTANCE = client;
            } else {
                LOGGER.info("Connected to non-MCC Island server, aborting.");
                SERVER_HANDLER = null;
                SERVER_SENDER = null;
                SERVER_INSTANCE = null;
            }
        });

        ServerPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            LOGGER.info("Disconnected from server, resetting state.");
            SERVER_HANDLER = null;
            SERVER_SENDER = null;
            SERVER_INSTANCE = null;
        });
    }
}
