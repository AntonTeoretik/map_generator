package com.teoretik.graphics.render.floor

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.teoretik.components.light.ShadowsProcessor
import com.teoretik.components.light.toColorMask
import com.teoretik.components.viewpoint.ViewPointProcessor
import com.teoretik.components.viewpoint.Visibility
import com.teoretik.graphics.camera.Camera
import com.teoretik.graphics.render.BoundedRenderer
import com.teoretik.graphics.render.GraphicsSettings.visibilityResolution
import com.teoretik.graphics.resources.Shape
import com.teoretik.utils.vectors.component1
import com.teoretik.utils.vectors.component2
import kotlin.math.max
import kotlin.math.min

class VisibilityRenderer(private val viewPointProcessor: ViewPointProcessor) : BoundedRenderer() {
    override fun setView(camera: Camera) {
        Shape.projectionMatrix = camera.projMatrix()
        super.setView(camera)
    }

    override fun render() {
        viewPointProcessor.processVisibility()

        Gdx.gl.glEnable(GL20.GL_BLEND)
        Gdx.gl.glBlendFunc(GL20.GL_DST_COLOR, GL20.GL_ZERO)

        Shape.begin(ShapeRenderer.ShapeType.Filled)

        val x0 = (viewBounds.x * visibilityResolution).toInt()
        val y0 = (viewBounds.y * visibilityResolution).toInt()

        val x1 = (x0 + viewBounds.width * visibilityResolution + 1).toInt()
        val y1 = (y0 + viewBounds.height * visibilityResolution + 1).toInt()


        with(viewPointProcessor.totalVisibility) {
            iterate(
                max(x0 - 1, 0) until min(width - 1, x1 + 1),
                max(y0 - 1, 0) until min(height - 1, y1 + 1)
            ).forEach { (i, j, _) ->
                val alpha = sequenceOf(0 to 0, 0 to 1, 1 to 0, 1 to 1).filter { (ii, jj) ->
                    this@with[i + ii, j + jj] == Visibility.VISIBLE
                }.count() * 0.25f

                Shape.color = Color.WHITE.cpy()
                Shape.color.mul(alpha).add(Color(0.2f, 0.3f, 0.2f, 0f))
                renderSquare(Vector2(i.toFloat() / visibilityResolution, j.toFloat() / visibilityResolution))
            }
        }

        Shape.end()
    }

    private fun renderSquare(vector2: Vector2) {
        val (x, y) = vector2
        val offset = 1f / visibilityResolution
        val vertexes: FloatArray =
            listOf(
                x, y + offset,
                x + offset, y + offset,
                x + offset, y,
                x, y
            ).toFloatArray()

        for (i in 2 until vertexes.size - 2 step 2) {
            Shape.triangle(
                vertexes[0], vertexes[1],
                vertexes[i], vertexes[i + 1],
                vertexes[i + 2], vertexes[i + 3]
            )
        }
    }
}