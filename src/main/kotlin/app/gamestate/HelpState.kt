package app.gamestate

import app.manager.GameStateManager
import app.manager.KeyManager
import app.manager.LanguageManager.get
import app.manager.TextureManager.getImage
import app.utils.PUSAB
import app.utils.loadFont
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import javafx.scene.text.TextAlignment
import org.apache.logging.log4j.LogManager

private val logger = LogManager.getLogger(HelpState::class.java)
class HelpState(gameStateManager: GameStateManager) : GameStateBase(gameStateManager) {
    private val labelsControls = listOf(
            get("help_attack"),
            get("help_run"),
            get("help_pause"),
            get("help_move"))
    private val labelExit = get("help_exit")
    private val labelHelp = get("menu_help")

    private val pusab40 = loadFont(PUSAB, 40.0)
    private val pusab50 = loadFont(PUSAB, 50.0)

    init {
        initialize()
    }

    override fun initialize() {
        logger.info("Pushing help state.")
    }

    override fun update() {
        handleInput()
    }

    override fun draw(graphics: GraphicsContext) {
        graphics.drawImage(getImage("state-help"), 0.0, 0.0)
        graphics.fill = Color.BLACK

        graphics.textAlign = TextAlignment.CENTER
        graphics.font = pusab50
        graphics.fillText(labelHelp, 320.0, 120.0)

        graphics.font = pusab40
        graphics.textAlign = TextAlignment.LEFT
        graphics.fillText(labelsControls.get(0), 330.0, 210.0)
        graphics.fillText(labelsControls.get(1), 330.0, 280.0)
        graphics.fillText(labelsControls.get(2), 330.0, 355.0)
        graphics.fillText(labelsControls.get(3), 330.0, 460.0)

        graphics.font = pusab50
        graphics.fill = Color.RED
        graphics.textAlign = TextAlignment.CENTER
        graphics.fillText(labelExit, 320.0, 545.0)
        graphics.drawImage(getImage("arrow-right"), 180.0, 512.0)
    }

    override fun handleInput() {
        if (KeyManager.isPressed(KeyManager.K_ENTER)) {
            logger.info("Popping help state. Going back to previous state.")
            gameStateManager.popState()
        }
    }
}