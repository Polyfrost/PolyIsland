package cc.polyfrost.polyisland.misc;

import dev.isxander.yacl.api.NameableEnum;
import net.minecraft.text.Text;

public enum ReportButtonSetting implements NameableEnum {
    REMOVE(Text.literal("Remove")),
    REPLACE(Text.literal("Replace with PolyIsland")),
    NOTHING(Text.literal("Don't change"));

    public final Text text;

    ReportButtonSetting(Text text) {
        this.text = text;
    }

    @Override
    public Text getDisplayName() {
        return text;
    }
}
