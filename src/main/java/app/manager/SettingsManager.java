package app.manager;

import app.enums.SettingsOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

/**
 * @author Kevin
 */
public class SettingsManager {
    private static final Logger logger = LogManager.getLogger(SettingsManager.class);
    private static final String FILENAME = ".settings";

    private final String[] languages = {"en", "es"};
    private final String[] playerNames = {
            "sprite-player-carlos.png",
            "sprite-player-gama.png",
            "sprite-player-karla.png",
            "sprite-player-kevinl.png",
            "sprite-player-kevinv.png",
    };

    private Map<String, String> settings;

    public SettingsManager() {
        settings = Collections.synchronizedMap(new LinkedHashMap<>());
        loadSettings();
    }

    public void loadSettings() {
        File file = new File(FILENAME);
        if (file.exists()) {
            try {
                var scanner = new Scanner(file);
                String line;
                while (scanner.hasNext()) {
                    line = scanner.nextLine();
                    var entry = line.split("=");
                    if (isValidOption(entry[0], entry[1])) {
                        settings.put(entry[0], entry[1]);
                    } else break;
                }
                scanner.close();
                verifyAndFixSettings();
            } catch (FileNotFoundException e) {
                logger.warn("Attempt to read non existing file " + FILENAME, e);
            }
        } else {
            setUpDefaultSettings();
        }
    }

    private void setUpDefaultSettings() {
        settings.put(SettingsOptions.LANGUAGE.label, languages[0]);
        settings.put(SettingsOptions.PLAYER.label, playerNames[0]);
        settings.put(SettingsOptions.SFX.label, "1");
        settings.put(SettingsOptions.MUSIC.label, "1");
        updateSettingsFile();
    }

    public void updateSettingsFile() {
        try {
            var printWriter = new PrintWriter(FILENAME);
            for (var entry : settings.entrySet()) {
                printWriter.printf("%s=%s\n", entry.getKey(), entry.getValue());
            }
            printWriter.close();
        } catch (FileNotFoundException e) {
            logger.error("File with name '" + FILENAME + "' was not found", e);
        }
    }

    private boolean isValidOption(String key, String value) {
        SettingsOptions option = Arrays.stream(SettingsOptions.values()).filter(opt -> opt.label.equals(key)).findFirst().orElse(SettingsOptions.ILLEGAL);

        switch (option) {
            case LANGUAGE:
                return Arrays.asList(languages).contains(value);
            case PLAYER:
                return Arrays.asList(playerNames).contains(value);
            case SFX:
            case MUSIC:
                return value.equals("1") || value.equals("0");
            default:
                return false;
        }
    }

    private void verifyAndFixSettings() {
        if (settings.size() != 4) {
            logger.warn("We detected that the settings file had some problems and it will be replaced with the default settings");
            settings.clear();
            setUpDefaultSettings();
        }
    }

    public String getLanguage() {
        return settings.getOrDefault(SettingsOptions.LANGUAGE.label, languages[0]);
    }

    public String getPlayer() {
        return settings.getOrDefault(SettingsOptions.PLAYER.label, playerNames[0]);
    }

    public boolean isMusicEnabled() {
        return settings.getOrDefault(SettingsOptions.MUSIC.label, "0").equals("1");
    }

    public boolean isSfxEnabled() {
        return settings.getOrDefault(SettingsOptions.SFX.label, "0").equals("1");
    }

    public int countAvailableSettings() {
        return settings.size();
    }
}
