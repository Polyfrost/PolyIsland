package cc.polyfrost.polyisland.game;

import cc.polyfrost.polyisland.config.ConfigManager;
import cc.polyfrost.polyisland.mixin.transformers.InGameHudAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;

public class HudTracker {
    public static final HudTracker INSTANCE = new HudTracker();
    private static final MinecraftClient mc = MinecraftClient.getInstance();
    private static final String CIRCLE = "⏺";

    private HudTracker() {}

    public void onHudRender(MatrixStack matrices, float tickDelta) {
        renderLightIndicator(matrices);
    }

    private void renderLightIndicator(MatrixStack matrices) {
        if (!ConfigManager.INSTANCE.getConfig().lightIndicator) return;
        final LightState lightState = GameTracker.INSTANCE.getLightState();
        if (lightState == LightState.NONE) return;
        matrices.push();
        matrices.translate((double) mc.getWindow().getScaledWidth() / 2, (double) mc.getWindow().getScaledHeight() / 2, 0.0);
        matrices.scale(4.0F, 4.0F, 0F);
        final int m = mc.textRenderer.getWidth(CIRCLE);
        ((InGameHudAccessor) mc.inGameHud).invokeDrawTextBackground(matrices, mc.textRenderer, -10, m, Color.BLACK.getRGB());
        mc.textRenderer.drawWithShadow(matrices, CIRCLE, (float)(-m / 2), -10.0F, lightState == LightState.GREEN ? Color.GREEN.getRGB() : Color.RED.getRGB());
        matrices.pop();
    }
}
