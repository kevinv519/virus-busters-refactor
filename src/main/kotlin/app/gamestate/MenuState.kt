package app.gamestate

import app.enums.GameStates
import app.manager.GameStateManager
import app.manager.KeyManager
import app.manager.LanguageManager
import app.manager.TextureManager.getImage
import app.utils.PUSAB
import app.utils.loadFont
import javafx.application.Platform
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import javafx.scene.text.TextAlignment
import org.apache.logging.log4j.LogManager
import kotlin.system.exitProcess

private val logger = LogManager.getLogger(MenuState::class.java)
class MenuState(gameStateManager: GameStateManager) : GameStateBase(gameStateManager) {

    private val labels = listOf(
            LanguageManager.get("menu_start"),
            LanguageManager.get("menu_help"),
            LanguageManager.get("menu_score"),
            LanguageManager.get("menu_settings"),
            LanguageManager.get("menu_exit")
    )

    private val pusabFont = loadFont(PUSAB, 50.0)

    private var currentOption = 0


    override fun initialize() {
        logger.info("Entering MenuState")
    }

    override fun update() {
        handleInput()
    }

    override fun draw(graphics: GraphicsContext) {
        graphics.drawImage(getImage("state-menu"), 0.0, 0.0)

        graphics.textAlign = TextAlignment.CENTER
        graphics.font = pusabFont
        for (i in labels.indices) {
            if (currentOption == i) {
                graphics.fill = Color.RED
                graphics.drawImage(getImage("arrow-right"), 150.0, 182.0 + i * 70.0)
            } else {
                graphics.fill = Color.BLACK
            }
            graphics.fillText(labels[i], 320.0, 215.0 + 70.0 * i)
        }
    }

    override fun handleInput() {
        if (KeyManager.isPressed(KeyManager.K_DOWN)) {
            currentOption++
        } else if (KeyManager.isPressed(KeyManager.K_UP)) {
            currentOption--
        } else if (KeyManager.isPressed(KeyManager.K_ENTER)) {
            selectOption()
        }

        verifyCurrentOption()
    }

    private fun verifyCurrentOption() {
        if (currentOption == labels.size) {
            currentOption = 0
        } else if (currentOption == -1) {
            currentOption = labels.lastIndex
        }
    }

    private fun selectOption() {
        when (currentOption) {
            1 -> gameStateManager.pushState(GameStates.HELP)
            4 -> {
                Platform.exit()
                exitProcess(0)
            }
        }
    }
}