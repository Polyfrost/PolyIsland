package cc.polyfrost.polyisland.mixin.transformers;

import cc.polyfrost.polyisland.config.ConfigManager;
import cc.polyfrost.polyisland.game.Game;
import cc.polyfrost.polyisland.game.GameState;
import cc.polyfrost.polyisland.game.GameTracker;
import net.minecraft.client.render.WorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @Inject(method = "renderEntity", at = @At("HEAD"), cancellable = true)
    private void onRenderEntity(CallbackInfo ci) {
        if (ConfigManager.INSTANCE.getConfig().hideHITWPlayers && GameTracker.INSTANCE.getGame() == Game.HOLE_IN_THE_WALL && GameTracker.INSTANCE.getState() == GameState.ACTIVE) {
            ci.cancel();
        }
    }
}
