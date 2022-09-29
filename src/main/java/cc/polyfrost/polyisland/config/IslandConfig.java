package cc.polyfrost.polyisland.config;

import cc.polyfrost.polyisland.misc.ReportButtonSetting;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
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

public class IslandConfig {
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .excludeFieldsWithoutExposeAnnotation()
            .create();
    private static final Path path = FabricLoader.getInstance().getConfigDir().resolve("polyisland.json");

    private static IslandConfig instance;

    public static final IslandConfig DEFAULTS = new IslandConfig();

    @Expose public boolean lightIndicator = true;
    @Expose public boolean greenVignette = true;
    @Expose public ReportButtonSetting playerReportingReplacement = ReportButtonSetting.REPLACE;

    public static IslandConfig getInstance() {
        if (instance == null)
            instance = new IslandConfig();

        return instance;
    }

    public static void load() {
        if (Files.notExists(path)) {
            getInstance().save();
            return;
        }

        try {
            String jsonString = Files.readString(path);
            instance = gson.fromJson(jsonString, IslandConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            Files.deleteIfExists(path);

            String jsonString = gson.toJson(this);
            Files.writeString(path, jsonString);
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
                                        DEFAULTS.lightIndicator,
                                        () -> lightIndicator,
                                        newValue -> lightIndicator = newValue
                                )
                                .controller(BooleanController::new)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.of("Add Green Vignette"))
                                .tooltip(Text.of("Show a green vignette when green light is on in Red Light Green Light"))
                                .binding(
                                        DEFAULTS.greenVignette,
                                        () -> greenVignette,
                                        newValue -> greenVignette = newValue
                                )
                                .controller(BooleanController::new)
                                .build())
                        .build())
                .save(this::save)
                .build()
                .generateScreen(parent);
    }
}
