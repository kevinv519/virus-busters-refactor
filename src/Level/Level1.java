package Level;

//import Entity.Enemy;
import Entity.USB;
import Manager.AudioManager;
import Manager.GameStateManager;
import Manager.TextureManager;
import TileMap.TileMap;

public class Level1 extends Level {
    
    public Level1(GameStateManager gsm){
        super(gsm);
        fondo = TextureManager.level1;
        tm = new TileMap(fondo.getWidth(), fondo.getHeight());
        tm.loadMap("labo.map");
        
        usb = new USB(tm);
        usb.setRealPosition(288, 192);
        AudioManager.getInstance().loop(AudioManager.MUSIC, 0, 0, AudioManager.getInstance().getFrames(AudioManager.MUSIC)-1);
        
        MAX_ENEMIES = 1;
    }

    @Override
    public void update(){
        super.update();
    }
}