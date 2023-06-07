import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.maps.MapGroupLayer
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.teoretik.graphics.camera.Camera
import com.teoretik.graphics.render.LightRenderer
import com.teoretik.graphics.utils.toDynamic
import ktx.app.KtxScreen

data class IntPair(var x: Int, var y: Int)

class Screen(val gameSetup: GameSetup) : KtxScreen {
    val shapeRenderer = ShapeRenderer()

    val groupLayer = gameSetup.map.layers.get("Level 1") as MapGroupLayer
    val layer = groupLayer.layers.get("Floor") as TiledMapTileLayer

    val position = IntPair(0, 0)
    val lightRenderer = LightRenderer(this)

    // Camera info
    val camera = Camera()

    private var cameraPosition = Vector3(0.0f, 0.0f, 0.0f).toDynamic(0.2f)
    val backgroundColor = Color.BLACK

    // viewports
    private val viewport = FitViewport(10f * Gdx.graphics.width / Gdx.graphics.height, 10f)
    private val uiViewport = ScreenViewport()
    private val batch = SpriteBatch()
    var text : String = ""

    init {
        setCameraPosition()
    }

    override fun show() {
        camera.setToOrtho(viewport)
    }

    override fun resize(width: Int, height: Int) {
        uiViewport.update(width, height, true)

        // Update the viewport dimensions
        viewport.setWorldSize(10f * width / height, 10f)
        viewport.update(width, height, true)

        // Update the camera and projection matrix to match the viewport
        camera.setToOrtho(viewport)
        camera.updatePosition(cameraPosition.get())
        // Update the camera
        camera.update()
    }

    fun setCameraPosition() {
        cameraPosition.set(Vector3(position.x + 0.5f, position.y - 0.5f + layer.height, 0f))
    }

    fun updateCamera(delta: Float) {
        cameraPosition.update(delta)
        camera.updatePosition(cameraPosition.get())
        camera.update()
    }

    override fun render(delta: Float) {
        viewport.apply()
        updateCamera(delta)

        // Set the viewport to the correct size and position
        // cameraPosition.set(camera.getPosition())

        // background
        val col = backgroundColor
        Gdx.gl.glClearColor(col.r, col.g, col.b, col.a)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // cells
        val v = cameraPosition.get()

        lightRenderer.setView(position)
        gameSetup.renderer.render()
        lightRenderer.renderVisionRadius(position)

        //drawCartesianGrid(20, 20, 1f, Color.FOREST)

        // Technical info
        text = "${v.x} ${v.y} \n ${position.x} ${position.y}"

        uiViewport.apply()
        drawTextTopLeft(batch, BitmapFont(), text)

    }

    override fun dispose() {
    }

    private fun drawCartesianGrid(numCellsX: Int, numCellsY: Int, cellSize: Float, color: Color) {
        shapeRenderer.projectionMatrix = camera.projMatrix()
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
        shapeRenderer.color = color

        // Draw vertical lines
        for (i in 0..numCellsX) {
            val x = i * cellSize
            shapeRenderer.line(x, 0f, x, numCellsY * cellSize)
        }

        // Draw horizontal lines
        for (i in 0..numCellsY) {
            val y = i * cellSize
            shapeRenderer.line(0f, y, numCellsX * cellSize, y)
        }

        shapeRenderer.end()
    }

    private fun drawTextTopLeft(batch: SpriteBatch, font: BitmapFont, text: String) {
        batch.projectionMatrix = uiViewport.camera.combined
        batch.begin()
        font.color = Color.YELLOW
        font.draw(batch, text, 10f, uiViewport.worldHeight - 30f)
        batch.end()
    }

}