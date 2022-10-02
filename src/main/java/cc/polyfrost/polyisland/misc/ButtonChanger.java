package cc.polyfrost.polyisland.misc;

import cc.polyfrost.polyisland.config.IslandConfig;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;

public class ButtonChanger {
    public static final ButtonChanger INSTANCE = new ButtonChanger();

    public void afterInitButtons(MinecraftClient mc, Screen screen, int scaledWidth, int scaledHeight) {
        if (screen instanceof GameMenuScreen gms) {
            var buttons = Screens.getButtons(gms);
            var setting = IslandConfig.getInstance().playerReportingReplacement;
            for (ClickableWidget button : buttons) {
                if (button.getMessage().getContent() instanceof TranslatableTextContent ttc) {
                    if (ttc.getKey().equals("menu.playerReporting")) {
                        if (setting == ReportButtonSetting.REPLACE) {
                            buttons.add(makeButton(button, mc));
                            buttons.remove(button);
                        } else if (setting == ReportButtonSetting.REMOVE) {
                            buttons.remove(button);
                        }
                    } else if (ttc.getKey().equals("menu.options") && setting == ReportButtonSetting.REMOVE) {
                        button.setWidth(204);
                    }
                }
            }
        }
    }

    private ButtonWidget makeButton(ClickableWidget oldButton, MinecraftClient mc) {
        return new ButtonWidget(oldButton.x, oldButton.y,
                oldButton.getWidth(), oldButton.getHeight(),
                Text.literal("PolyIsland Options"),
                button1 -> mc.setScreen(IslandConfig.getInstance().createGui(mc.currentScreen))
        );
    }
}
