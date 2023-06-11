package com.teoretik.graphics.render

import IntPair
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.teoretik.components.GameConfiguration
import com.teoretik.graphics.render.GraphicsSettings.Companion.unitScale

class WorldRenderer(val gameConfiguration: GameConfiguration) {
    val shapeRenderer = ShapeRenderer()
    val backgroundColor = Color.BLACK.cpy()

    val mapRenderer = MapRenderer(null)

    fun setView(position: IntPair, rad : Int) {
//        shapeRenderer.setView(
//            gameConfiguration.camera.projMatrix(),
//            position.x - rad + .5f,
//            position.y - rad - .5f,
//            2f * rad, 2 * rad - 1f
//        )
    }

    fun render() {
        gameConfiguration.camera.update()

        // background
        val col = backgroundColor
        Gdx.gl.glClearColor(col.r, col.g, col.b, col.a)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // render something here
        // For now just render the current layer
        mapRenderer.map = gameConfiguration.activeLevel?.map
        mapRenderer.setView(gameConfiguration.camera.camera)

        mapRenderer.shapeRenderer.projectionMatrix = gameConfiguration.camera.projMatrix()

        // TODO bad!
        val v = gameConfiguration.camera.position.get().cpy()
        gameConfiguration.dynamicLight.x = v.x / unitScale
        gameConfiguration.dynamicLight.y = v.y / unitScale
        //gameConfiguration.activeLevel!!.floors[1]!!.updateLight()

        mapRenderer.render()

        // grid for technical info
//        drawCartesianGrid(10, 10, 1f, Color.YELLOW)

    }


    private fun drawCartesianGrid(numCellsX: Int, numCellsY: Int, cellSize: Float, color: Color) {
        shapeRenderer.projectionMatrix = gameConfiguration.camera.projMatrix()
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
}
