package cc.polyfrost.islandtils.mixin.transformers;

import cc.polyfrost.islandtils.config.ConfigManager;
import cc.polyfrost.islandtils.game.GameTracker;
import cc.polyfrost.islandtils.game.LightState;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Unique private boolean aboutToDoTrolling = false;

    @ModifyExpressionValue(method = "renderVignetteOverlay", at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(DD)D"))
    private double modifyVignette(double value) {
        if (ConfigManager.INSTANCE.getConfig().greenVignette && GameTracker.INSTANCE.getLightState() == LightState.GREEN) {
            aboutToDoTrolling = true;
            return Integer.MAX_VALUE;
        } else {
            return value;
        }
    }

    @ModifyArgs(method = "renderVignetteOverlay", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderColor(FFFF)V", ordinal = 0))
    private void modifyVignetteColor(Args args) {
        if (aboutToDoTrolling) {
            args.set(0, 1.0f);
            args.set(1, 0.0f);
            args.set(2, 1.0f);
            aboutToDoTrolling = false;
        }
    }
}
