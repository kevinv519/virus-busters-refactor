package Entity;

import TileMap.TileMap;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

/**
 *
 * @author Gama
 */
public class CoinController {
    List<Coin> coins;
    Coin temCoin;
    TileMap tm;
    int number;
    
    private final Random r = new Random();
    int posRX, posRY;
    
    public CoinController(int number, int posRX, int posRY, int camX, int camY, TileMap tm){
        this.tm = tm;
        coins = new ArrayList<>();
        this.number = number;
        this.posRX = posRX;
        this.posRY = posRY;
        this.loadCoins(camX, camY, posRX, posRY, tm);
    }
    
    public List<Coin> getCoins(){
        return coins;
    }
    
    public void loadCoins(int camX, int camY,int posRX,int posRY, TileMap tm){
        //this.tm = tm;
        for (int i = 0; i < number; i++) {
            coins.add(new Coin(this.tm));
        }
        coins.forEach((coin) -> {
           // System.out.println("Cargando moneda...");
            coin.setRealPosition(posRX+r.nextInt(30), posRY+r.nextInt(30));
            coin.setPosition(camX, camY);
        });
    }
    
    public void update(int dircam, int speed){
        for (ListIterator<Coin> i = coins.listIterator(); i.hasNext();) {
            Coin c = i.next();
            if(!c.isTaken() && c.getCount() < 399){
                c.redraw(dircam, speed);
                c.update();
            }else{
                i.remove();
            }
            //agregar aqui si la moneda es tomada
        }
    }
    
    public void draw(Graphics2D g){
        coins.forEach((coin)->{
            coin.draw(g);
        });
    }
}

