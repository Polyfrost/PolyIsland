package cc.polyfrost.polyisland;

import cc.polyfrost.polyisland.config.ConfigManager;
import cc.polyfrost.polyisland.game.GameTracker;
import cc.polyfrost.polyisland.game.HudTracker;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class PolyIsland implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ConfigManager.INSTANCE.load();
        ClientTickEvents.START_WORLD_TICK.register(GameTracker.INSTANCE::onWorldTick);
        HudRenderCallback.EVENT.register(HudTracker.INSTANCE::onHudRender);
    }
}
