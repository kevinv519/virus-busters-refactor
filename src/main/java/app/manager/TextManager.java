package app.manager;

import javafx.scene.text.Font;

public class TextManager {

    public static final String NINTENDER = "nintender.ttf";
    public static final String PUSAB = "pusab.ttf";

    private TextManager() {}

    public static Font loadFont(String fontName, double fontSize) {
        var fontInputStream = TextManager.class.getResourceAsStream("/fonts/" + fontName);
        var font = Font.loadFont(fontInputStream, fontSize);
        return font != null ? font : Font.font(fontSize);
    }
}
