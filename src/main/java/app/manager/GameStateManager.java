package app.manager;

import app.gamestate.ContinueState;
import java.util.ArrayList;
import app.gamestate.GameState;
import app.gamestate.IntroState;
import app.gamestate.PauseState;
import java.awt.Graphics2D;
import java.util.List;
/**
 * @author Kevin
 */
public class GameStateManager {
    
    private boolean paused;
    private final PauseState pauseState;
    
    private boolean inContinue;
    private final ContinueState continueState;
    
    private final List<GameState> gameStates;
    private int currentState = -1;
    private int previousState = -1;
    
    /**
     * Constructor de la clase GameStateManager
     * Inicializa el ArrayList de estados
     */
    public GameStateManager() {
        gameStates = new ArrayList<>();
        pauseState = new PauseState(this);
        continueState = new ContinueState(this);
        pushState(new IntroState(this));
    }
    
    /**
     * Metodo que agrega un estado encima del anterior
     * @param state 
     * Un nuevo app.GameState
     */
    public final void pushState(GameState state){
        if (currentState >= 0)
            previousState = currentState;
        currentState = state.getStateID();
        state.init();
        gameStates.add(state);
    }
    
    /**
     * Quita el ultimo app.GameState que esta en la lista
     */
    public void popState(){
        if (!gameStates.isEmpty()){
            getLast(gameStates).exit();
            gameStates.remove(getLast(gameStates));
        }
    }
    
    /**
     * Quita el ultimo app.GameState de la lista y pone uno nuevo al final;
     * @param state 
     */
    ///Elimina el ultimo app.GameState de la lista y agrega uno nuevo
    public void changeState(GameState state){
        if(!gameStates.isEmpty()){
            if (currentState == state.getStateID()) return;
            getLast(gameStates).exit();
            gameStates.remove(getLast(gameStates));
        }
        pushState(state);
    }
    
    /**
     * Actualiza la logica del app.GameState en ejecucion
     */
    public void update(){
        if (paused)
            pauseState.update();
        else if(inContinue)
            continueState.update();
        else if (!gameStates.isEmpty())
            getLast(gameStates).update();
    }
    
    /**
     * Dibuja los elementos del app.GameState en ejecucion
     * @param g 
     * Graphics2D
     */
    public void draw(Graphics2D g){
        if (paused)
            pauseState.draw(g);
        else if(inContinue)
            continueState.draw(g);
        else if (!gameStates.isEmpty())
            getLast(gameStates).draw(g);
    }
    
    /**
     * Metodo que devuelve el ultimo app.GameState de la lista
     */
    private GameState getLast(List<GameState> states){
        return states.isEmpty()? null : states.get(states.size()-1) ;
    }
    
    public void setPaused(boolean b){
        paused = b;
    }
    
    public boolean isPaused(){
        return paused;
    }
    
    public void setInContinue(boolean b){
        inContinue = b;
    }
    
    public boolean isInContinue(){
        return inContinue;
    }
}
