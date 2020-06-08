package app.gamestate

import app.enums.GameStates
import app.manager.GameStateManager
import app.manager.KeyManager
import app.manager.TextureManager.getImage
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import org.apache.logging.log4j.LogManager

private val logger = LogManager.getLogger(IntroState::class.java)
class IntroState(gameStateManager: GameStateManager) : GameStateBase(gameStateManager) {
    private val FADE_IN = 80
    private val FADE_OUT = 80
    private val LENGTH = 80

    private var ticks = 0
    private var alpha = 1.0

    init {
        initialize()
    }

    override fun initialize() {
        logger.info("Entering IntroState")
    }

    override fun update() {
        handleInput()
        ticks++
        if (ticks < FADE_IN) {
            alpha = 1.0 - 1.0 * ticks / FADE_IN
            if (alpha < 0) alpha = 0.0
        }
        if (ticks > FADE_IN + LENGTH) {
            alpha = 1 * (1.0 * ticks - FADE_IN - LENGTH) / FADE_OUT
            if (alpha > 1) {
                alpha = 1.0
            }
        }
        if (ticks > FADE_IN + FADE_OUT + LENGTH) {
            gameStateManager.changeState(GameStates.MENU)
        }
    }

    override fun draw(graphics: GraphicsContext) {
        graphics.drawImage(getImage("logo"), 0.0, 0.0)
        graphics.fill = Color.rgb(0, 0, 0, alpha)
        graphics.fillRect(0.0, 0.0, 640.0, 640.0)
    }

    override fun handleInput() {
        if (KeyManager.isPressed(KeyManager.K_ENTER)) {
            logger.info("User pressed enter. Skipping intro screen.")
            gameStateManager.changeState(GameStates.MENU)
        }
    }
}