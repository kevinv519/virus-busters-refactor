package app.gamestate

import app.manager.GameStateManager
import javafx.scene.canvas.GraphicsContext

abstract class GameStateBase(protected val gameStateManager: GameStateManager) {
    abstract fun initialize()
    abstract fun update()
    abstract fun draw(graphics: GraphicsContext)
    abstract fun handleInput()
}