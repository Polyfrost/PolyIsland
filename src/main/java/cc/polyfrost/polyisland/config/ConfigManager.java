package cc.polyfrost.polyisland.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.isxander.yacl.api.ConfigCategory;
import dev.isxander.yacl.api.Option;
import dev.isxander.yacl.api.YetAnotherConfigLib;
import dev.isxander.yacl.gui.controllers.BooleanController;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigManager {
    public static final ConfigManager INSTANCE = new ConfigManager();
    public final Path configFile = FabricLoader.getInstance().getConfigDir().resolve("polyisland.json");
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static IslandConfig config;

    public void save() {
        try {
            if (config == null) {
                config = new IslandConfig();
            }
            Files.writeString(configFile, gson.toJson(config));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        try {
            if (Files.notExists(configFile)) {
                save();
                return;
            }
            config = gson.fromJson(Files.readString(configFile), IslandConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Screen createGui(Screen parent) {
        return YetAnotherConfigLib.createBuilder()
                .title(Text.of("PolyIsland"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.of("General"))
                        .tooltip(Text.of("General options for PolyIsland"))
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.of("Show Light Indicator"))
                                .tooltip(Text.of("Shows a indicator for Red Light Green Light"))
                                .binding(
                                        true,
                                        () -> getConfig().lightIndicator,
                                        newValue -> getConfig().lightIndicator = newValue
                                )
                                .controller(BooleanController::new)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.of("Add Green Vignette"))
                                .tooltip(Text.of("Show a green vignette when green light is on in Red Light Green Light"))
                                .binding(
                                        true,
                                        () -> getConfig().greenVignette,
                                        newValue -> getConfig().greenVignette = newValue
                                )
                                .controller(BooleanController::new)
                                .build())
                        .build())
                .save(this::save)
                .build()
                .generateScreen(parent);
    }

    public IslandConfig getConfig() {
        if (config == null) {
            load();
        }
        return config;
    }
}
