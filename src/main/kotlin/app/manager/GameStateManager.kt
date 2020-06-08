package app.manager

import app.enums.GameStates
import app.gamestate.*
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
            GameStates.SCORE -> gameStates.add(ScoreState(this))
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