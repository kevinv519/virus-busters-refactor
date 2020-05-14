package app.level;

import app.entity.USB;
import app.manager.AudioManager;
import app.manager.GameStateManager;
import app.manager.TextureManager;
import app.tilemap.TileMap;


public class Level2 extends Level{
    
    public Level2(GameStateManager gsm){
        super(gsm);
        fondo = TextureManager.level2;
        tm = new TileMap(fondo.getWidth(), fondo.getHeight());
        tm.loadMap("aula.map");
        
        usb = new USB(tm);
        usb.setRealPosition(386, 256);
        AudioManager.getInstance().loop(AudioManager.MUSIC, 0, 0, AudioManager.getInstance().getFrames(AudioManager.MUSIC)-1);
        
        MAX_ENEMIES = 3;
    }
    
    @Override
    public void update(){
        super.update();
    }
}
