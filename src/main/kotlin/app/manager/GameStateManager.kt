package app.manager

import app.enums.GameStates
import app.gamestate.GameStateBase
import app.gamestate.HelpState
import app.gamestate.IntroState
import app.gamestate.MenuState
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color

class GameStateManager {
    private val gameStates = mutableListOf<GameStateBase>()

    init {
        pushState(GameStates.INTRO)
    }

    fun pushState(state: GameStates) {
        when (state) {
            GameStates.INTRO ->  gameStates.add(IntroState(this))
            GameStates.MENU -> gameStates.add(MenuState(this))
            GameStates.PLAY -> TODO()
            GameStates.HELP -> gameStates.add(HelpState(this))
        }
    }

    fun popState() {
        if (gameStates.isNotEmpty()) {
            gameStates.removeAt(gameStates.lastIndex)
        }
    }

     fun changeState(state: GameStates) {
         popState()
         pushState(state)
     }

    fun update() {
        if (gameStates.isNotEmpty()) {
            gameStates.last().update()
        }
    }

    fun draw(graphicsContext: GraphicsContext) {
        if (gameStates.isNotEmpty()) {
            gameStates.last().draw(graphicsContext)
        } else {
            graphicsContext.fill = Color.BLACK
            graphicsContext.fillRect(0.0, 0.0, 640.0, 640.0)
        }
    }
}