package com.teoretik.graphics.render

import IntPair
import Screen
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt
import kotlin.random.Random

class MapRenderer(val screen: Screen) {
    var rad = 5
    val largeRad = 8
    val random = Random.Default

    fun setView(position: IntPair) {
        screen.gameSetup.renderer.setView(
            screen.camera.projMatrix(),
            position.x - rad + .5f,
            position.y - rad - .5f + screen.layer.height,
            2f * rad, 2 * rad - 1f
        )

    }

    fun renderShadows(position: IntPair) {

    }

    fun renderVisionRadius(position: IntPair) {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        screen.shapeRenderer.projectionMatrix = screen.camera.projMatrix()
        screen.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)

        for (i in position.x - largeRad..position.x + largeRad) {
            for (j in position.y - largeRad..position.y + largeRad) {
                val alpha = distToAlpha(intDist(i, j, position))
                renderShadowSquare(i.toFloat(), j.toFloat(), alpha)
            }
        }

        screen.shapeRenderer.end()
    }

    private fun intDist(i: Int, j: Int, center: IntPair): Float =
        sqrt((center.x - i) * (center.x - i) + (center.y - j) * (center.y - j).toFloat())


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

    private fun renderShadowSquare(x: Float, y: Float, alpha: Float) {
        val yOffset = screen.layer.height

        val vertexes: FloatArray =
            listOf(
                x, y + yOffset - 1,
                x + 1, y + yOffset - 1,
                x + 1, y + yOffset,
                x, y + yOffset
            ).toFloatArray()

        val color = Color.BLACK.cpy()
        color.a = alpha
        screen.shapeRenderer.color = color

        for (i in 2 until vertexes.size - 2 step 2) {
            screen.shapeRenderer.triangle(
                vertexes[0], vertexes[1],
                vertexes[i], vertexes[i + 1],
                vertexes[i + 2], vertexes[i + 3]
            )
        }

    }
}