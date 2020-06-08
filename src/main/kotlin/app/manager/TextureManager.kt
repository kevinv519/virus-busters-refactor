package app.manager

import javafx.application.Platform
import javafx.scene.image.Image
import org.apache.logging.log4j.LogManager
import kotlin.system.exitProcess

object TextureManager {
    private val logger = LogManager.getLogger(this::class.java)

    private const val PATH = "images/"

    private val imageNotFound = loadImage("not-found.png")

    private val images = mapOf(
            "logo" to loadImage("logo.png"),
            "state-menu" to loadImage("state-menu.png"),
            "state-pause" to loadImage("state-pause.png"),
            "state-win" to loadImage("state-win.png"),
            "state-help" to loadImage("state-help.png"),
            "arrow-right" to loadImage("arrow-right.png"),
            "sprite-bullet" to loadImage("sprite-bullet.png"),
            "sprite-enemy" to loadImage("sprite-enemy.png"),
            "sprite-enemy-bullet" to loadImage("sprite-enemy-bullet.png"),
            "sprite-boss" to loadImage("sprite-boss.png"),
            "sprite-boss-bullet" to loadImage("sprite-boss-bullet.png"),
            "sprite-boss-explosion" to loadImage("sprite-boss-explosion.png"),
            "usb" to loadImage("sprite-usb.png"),
            "sprite-coin" to loadImage("sprite-coin.png"),
            "level-lab" to loadImage("level-lab.png"),
            "level-classroom" to loadImage("level-classroom.png"),
            "level-magna" to loadImage("level-magna.png"),
            "level-final" to loadImage("level-final.png"),
            "hud-usb" to loadImage("hud-usb.png"),
            "hud-coin" to loadImage("hud-coin.png"),
            "win-clock" to loadImage("win-clock.png"),
            "win-coin" to loadImage("win-coin.png"),
            "win-usb" to loadImage("win-usb.png"),
            "win-life" to loadImage("win-life.png"),
            "button" to loadImage("button.png"),
            "button-over" to loadImage("button-over.png"),
            "button-pressed" to loadImage("button-pressed.png")
    )

    private fun loadImage(filename: String): Image {
        try {
            return Image(PATH + filename)
        } catch (e: IllegalArgumentException) {
            logger.error("Error occurred while loading image $filename. More details:", e)
            Platform.exit()
            exitProcess(0)
        }
    }

    fun getImage(key: String): Image {
        return try {
            images.getValue(key)
        } catch (e: NoSuchElementException) {
            logger.error("Could not find image with key '$key'. More details:", e)
            imageNotFound
        }
    }
}