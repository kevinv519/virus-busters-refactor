package app.level;

import app.entity.USB;
import app.manager.AudioManager;
import app.manager.GameStateManager;
import app.manager.TextureManager;
import app.tilemap.TileMap;


public class LevelFinal extends Level {
    
    public LevelFinal(GameStateManager gsm){
        super(gsm);
        fondo = TextureManager.levelFinal;
        tm = new TileMap(fondo.getWidth(), fondo.getHeight());
        tm.loadMap("final.map");
        AudioManager.getInstance().loop(AudioManager.MUSIC_FINAL, 0, 0, AudioManager.getInstance().getFrames(AudioManager.MUSIC_FINAL)-1);
        
        usb = new USB(tm);
        usb.setRealPosition(768, 768);
        
        MAX_ENEMIES = 6;
    }
    
    @Override
    public void update() {
        super.update();
        isBossDefeated();
    }
    
    public void isBossDefeated(){
        if (!enemies.getEnemies().isEmpty() && enemies.getEnemies().get(0).getLife() <= 0){
            enemies.removeAll();
        }
    }
}
