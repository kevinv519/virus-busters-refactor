package app.level;

import app.entity.USB;
import app.manager.AudioManager;
import app.manager.GameStateManager;
import app.manager.TextureManager;
import app.tilemap.TileMap;


public class Level3 extends Level{

    public Level3(GameStateManager gsm) {
        super(gsm);
        fondo = TextureManager.level3;
        tm = new TileMap(fondo.getWidth(), fondo.getHeight());
        tm.loadMap("magna.map");
        
        usb = new USB(tm);
        usb.setRealPosition(640, 385);
        AudioManager.getInstance().loop(AudioManager.MUSIC, 0, 0, AudioManager.getInstance().getFrames(AudioManager.MUSIC)-1);
        
        MAX_ENEMIES = 4;
    }

    @Override
    public void update() {
        super.update();
    }

}
