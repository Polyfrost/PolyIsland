package cc.polyfrost.islandtils;

import cc.polyfrost.islandtils.config.ConfigManager;
import cc.polyfrost.islandtils.game.GameTracker;
import cc.polyfrost.islandtils.game.HudTracker;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class Islandtils implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ConfigManager.INSTANCE.load();
        ClientTickEvents.START_WORLD_TICK.register(GameTracker.INSTANCE::onWorldTick);
        HudRenderCallback.EVENT.register(HudTracker.INSTANCE::onHudRender);
    }
}
