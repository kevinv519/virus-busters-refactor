package Manager;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;

/**
 * @author Kevin
 */
public class TextManager {
    private static final String PATH = "Fonts/";
    public static final String PUSAB = "pusab.ttf";
    public static final String VCR = "vcr.ttf";
    public static final String NINTENDER = "nintender.ttf";
    public static final String BLOCKTOPIA = "blocktopia.ttf";
    
    public static Font getFont(String fontName, float fontSize){
        Font temp = null;
        try {
            temp = Font.createFont(Font.TRUETYPE_FONT, TextManager.class.getClassLoader().getResourceAsStream(PATH + fontName)).deriveFont(fontSize);
        } catch (FontFormatException | IOException e) {
            System.out.println(e.getMessage());
        }
        return temp;
    }
    
    public static int centerPosX(String text, java.awt.Graphics2D g, int width){
        int posX =(width - g.getFontMetrics().stringWidth(text))/2;
        return posX < 0 ? 0 : posX;
    }

}
