package app.gamestate

import app.manager.*
import app.utils.PUSAB
import app.utils.loadFont
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import javafx.scene.text.TextAlignment

class ScoreState(gameStateManager: GameStateManager) : GameStateBase(gameStateManager) {
    private val labelName = LanguageManager.get("scores_title_name")
    private val labelScore = LanguageManager.get("scores_title_score")
    private val labelBack = LanguageManager.get("scores_option_back")

    private val pusab25 = loadFont(PUSAB, 25.0)
    private val pusab35 = loadFont(PUSAB, 35.0)

    override fun initialize() {}

    override fun update() {
        handleInput()
    }

    override fun draw(graphics: GraphicsContext) {
        graphics.drawImage(TextureManager.getImage("state-menu"), 0.0, 0.0)

        graphics.textAlign = TextAlignment.LEFT

        graphics.fill = Color.RED
        graphics.font = pusab35
        graphics.fillText(labelName, 175.0, 180.0)
        graphics.fillText(labelScore, 370.0, 180.0)

        graphics.fill = Color.BLACK
        graphics.font = pusab25
        ScoreManager.scores.forEachIndexed { i, score ->
            graphics.textAlign = TextAlignment.RIGHT
            graphics.fillText("${i + 1}.", 165.0, 210.0 + 30 * i)
            graphics.textAlign = TextAlignment.LEFT
            graphics.fillText(ScoreManager.names[i], 175.0, 210.0 + 30 * i)
            graphics.fillText(score.toString(), 370.0, 210.0 + 30 * i)
        }

        graphics.textAlign = TextAlignment.CENTER
        graphics.font = pusab35
        graphics.fill = Color.RED
        graphics.drawImage(TextureManager.getImage("arrow-right"), 180.0, 500.0)
        graphics.fillText(labelBack, 320.0, 530.0)
    }

    override fun handleInput() {
        if (KeyManager.isPressed(KeyManager.K_ESCAPE) || KeyManager.isPressed(KeyManager.K_ENTER))  {
            gameStateManager.popState()
        }
    }
}