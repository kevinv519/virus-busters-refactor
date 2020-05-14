package TileMap;

import Main.GamePanel;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author Kevin
 */
public class TileMap {
    private final String DIR_PATH = "Resources/Maps/";
    
    private final int tiles[][];
    private final int tileSize = 64;
    
    private final int numRows;
    private final int numCols;
    private int posInicialX;
    private int posInicialY;
    
    public TileMap(int mapWidth, int mapHeight){
        numRows = mapHeight/tileSize;
        numCols = mapWidth/tileSize;
        tiles = new int[numRows][numCols];
    }
    
    public void loadMap(String filename){
        try {
            File file = new File(DIR_PATH + filename);
            Scanner sc = new Scanner(file);
            String line;
            String tokens[];
            for (int i = 0; i < numRows; i++) {
                line = sc.nextLine();
                tokens = line.split(" ");
                for (int j = 0; j < numCols; j++) {
                    tiles[i][j] = Integer.parseInt(tokens[j]);
                }
            }
            posInicialX = Integer.parseInt(sc.nextLine())*tileSize;
            posInicialY = Integer.parseInt(sc.nextLine())*tileSize;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public int getMapWidth(){
        return tileSize*numCols;
    }
    
    public int getMapHeight(){
        return tileSize*numRows;
    }
    
    public int getPosInicialX(){
        if (posInicialX > GamePanel.PANEL_WIDTH){
            return posInicialX - tileSize*numCols + GamePanel.PANEL_WIDTH;
        }
        return posInicialX;
    }
    
    public int calculatePosInicialX(int posX){
        posX= posX*tileSize;
        if (posX > GamePanel.PANEL_WIDTH){
            return posX - tileSize*numCols + GamePanel.PANEL_WIDTH;
        }
        return posX;
    }
    
    public int getPosInicialY(){
        if (posInicialY > GamePanel.PANEL_HEIGHT){
            return posInicialY - tileSize*numRows + GamePanel.PANEL_HEIGHT;
        }
        return posInicialY;
    }
    
    public int calculatePosInicialY(int posY){
        posY = posY*tileSize;
        if (posY > GamePanel.PANEL_HEIGHT){
            return posY - tileSize*numRows + GamePanel.PANEL_HEIGHT;
        }
        return posY;
    }
    
    public int getPosInicialRX(){
        return posInicialX;
    }
    
    public int getPosInicialRY(){
        return posInicialY;
    }
    
    public int getTileSize(){
        return tileSize;
    }
    
    public boolean isBlocked(int row, int col){
        return tiles[row][col] == 1;
    }
    
    public int getNumRows(){
        return numRows;
    }
    
    public int getNumCols(){
        return numCols;
    }
}
