import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.teoretik.graphics.render.GraphicsSettings
import com.teoretik.graphics.utils.DynamicProcessor
import ktx.app.KtxScreen

data class IntPair(var x: Int, var y: Int)

class Screen(val gameSetup: GameSetup) : KtxScreen {
    // viewports
    private val viewport = FitViewport(
        GraphicsSettings.worldHeight * Gdx.graphics.width / Gdx.graphics.height,
        GraphicsSettings.worldHeight
    )
    private val uiViewport = ScreenViewport()
    private val batch = SpriteBatch()
    var text: String = ""


    override fun show() {
        gameSetup.gameConfiguration.camera.setToOrtho(viewport)
    }

    override fun resize(width: Int, height: Int) {
        uiViewport.update(width, height, true)

        // Update the viewport dimensions
        viewport.setWorldSize(
            GraphicsSettings.worldHeight * width / height,
            GraphicsSettings.worldHeight
        )
        viewport.update(width, height, true)

        // Update the camera and projection matrix to match the viewport
        gameSetup.gameConfiguration.camera.setToOrtho(viewport)
        // Update the camera
        gameSetup.gameConfiguration.camera.update()
    }

    override fun render(delta: Float) {
        DynamicProcessor.update(delta)

        viewport.apply()
        gameSetup.renderer.render()

        uiViewport.apply()
        drawTextTopLeft(batch, BitmapFont(), text)
    }

    override fun dispose() {
    }


    private fun drawTextTopLeft(batch: SpriteBatch, font: BitmapFont, text: String) {
        batch.projectionMatrix = uiViewport.camera.combined
        batch.begin()
        font.color = Color.YELLOW
        font.draw(batch, text, 10f, uiViewport.worldHeight - 30f)
        batch.end()
    }

}