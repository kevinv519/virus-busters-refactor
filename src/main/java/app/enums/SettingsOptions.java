package app.enums;

import java.util.Arrays;

public enum SettingsOptions {
    LANGUAGE("language"),
    PLAYER("player"),
    SFX("sfx_enabled"),
    MUSIC("music_enabled"),
    ILLEGAL("illegal");

    public final String label;

    SettingsOptions(String label) {
        this.label = label;
    }
}
