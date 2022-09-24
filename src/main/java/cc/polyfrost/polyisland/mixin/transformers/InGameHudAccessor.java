package cc.polyfrost.polyisland.mixin.transformers;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(InGameHud.class)
public interface InGameHudAccessor {
    @Invoker("drawTextBackground")
    void invokeDrawTextBackground(MatrixStack matrices, TextRenderer textRenderer, int yOffset, int width, int color);
}
