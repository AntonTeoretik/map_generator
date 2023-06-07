package com.teoretik.graphics.render

import IntPair
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.teoretik.components.GameConfiguration
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt
import kotlin.random.Random

data class ViewSquare(
    var rad: Int = 50,
    val position: IntPair = IntPair(0, 0)
)


class ViewRenderer(val gameConfiguration: GameConfiguration) {
    private val shapeRenderer = ShapeRenderer()

    var rad = 50
    val largeRad = 54
    val random = Random.Default


    fun renderVisionRadius(position: IntPair) {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_DST_COLOR, GL20.GL_ZERO);

        shapeRenderer.projectionMatrix = gameConfiguration.camera.projMatrix()
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)

        for (i in position.x - largeRad..position.x + largeRad) {
            for (j in position.y - largeRad..position.y + largeRad) {
                val alpha = distToAlpha(intDist(i, j, position))
                renderShadowSquare(i.toFloat(), j.toFloat(), alpha)
            }
        }

        shapeRenderer.end()

        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    private fun distToAlpha(dist: Float): Float {
        val r = random.nextFloat() - 0.5f
        val relDist = dist / rad
        return max(
            0.0f,
            min(
                1.0f,
                relDist * relDist + r * GraphicsSettings.noiseLevel * relDist
            )
        )
    }

    private fun intDist(i: Int, j: Int, center: IntPair): Float =
        sqrt((center.x - i) * (center.x - i) + (center.y - j) * (center.y - j).toFloat())

    private fun renderShadowSquare(x: Float, y: Float, alpha: Float) {
        val yOffset = gameConfiguration.activeLevel?.activeFloorHeight() ?: return

        val vertexes: FloatArray =
            listOf(
                x, y + yOffset - 1,
                x + 1, y + yOffset - 1,
                x + 1, y + yOffset,
                x, y + yOffset
            ).toFloatArray()

        val color = Color.WHITE.cpy()
        color.mul(1.0f - alpha)
        shapeRenderer.color = color

        for (i in 2 until vertexes.size - 2 step 2) {
            shapeRenderer.triangle(
                vertexes[0], vertexes[1],
                vertexes[i], vertexes[i + 1],
                vertexes[i + 2], vertexes[i + 3]
            )
        }

    }
}