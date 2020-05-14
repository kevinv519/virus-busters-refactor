package app.main;

import app.manager.LanguageManager;
import javax.swing.JFrame;

/**
 * @author Kevin
 */
public class Game {
    public static void main (String[] args){
        JFrame window = new JFrame(LanguageManager.getInstance().get("nombre_juego"));
        window.setContentPane(new GamePanel());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        window.setVisible(true);
        window.setLocationRelativeTo(null);
    }
}
