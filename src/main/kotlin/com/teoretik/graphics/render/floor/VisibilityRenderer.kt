package com.teoretik.graphics.render.floor

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.teoretik.components.light.ShadowsProcessor
import com.teoretik.components.light.toColorMask
import com.teoretik.components.viewpoint.ViewPointProcessor
import com.teoretik.components.viewpoint.Visibility
import com.teoretik.graphics.camera.Camera
import com.teoretik.graphics.render.BoundedRenderer
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

        Shape.begin(ShapeRenderer.ShapeType.Filled)

        val x0 = viewBounds.x.toInt()
        val y0 = viewBounds.y.toInt()

        val x1 = (x0 + viewBounds.width + 1).toInt()
        val y1 = (y0 + viewBounds.height + 1).toInt()


        with(viewPointProcessor.totalVisibility) {
            iterate(
                max(x0 - 1, 0) until min(width - 1, x1 + 1),
                max(y0 - 1, 0) until min(height - 1, y1 + 1)
            ).filter { it.third == Visibility.NOT_VISIBLE }.forEach { (i, j, _) ->
                Shape.color = Color.BLACK.cpy()
                renderSquare(Vector2(i.toFloat(), j.toFloat()))
            }
        }

        Shape.end()
    }

    private fun renderSquare(vector2: Vector2) {
        val (x, y) = vector2
        val offset = 1f
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