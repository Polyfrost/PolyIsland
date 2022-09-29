package cc.polyfrost.polyisland;

import cc.polyfrost.polyisland.config.IslandConfig;
import cc.polyfrost.polyisland.game.GameTracker;
import cc.polyfrost.polyisland.game.HudTracker;
import cc.polyfrost.polyisland.misc.ButtonChanger;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;

public class PolyIsland implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        IslandConfig.load();
        ClientTickEvents.START_WORLD_TICK.register(GameTracker.INSTANCE::onWorldTick);
        HudRenderCallback.EVENT.register(HudTracker.INSTANCE::onHudRender);
        ScreenEvents.AFTER_INIT.register(ButtonChanger.INSTANCE::afterInitButtons);
    }
}
