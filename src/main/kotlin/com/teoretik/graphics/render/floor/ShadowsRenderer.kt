package com.teoretik.graphics.render.floor

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.teoretik.components.light.LightColor
import com.teoretik.components.light.processors.ShadowsProcessor
import com.teoretik.components.light.toColorMask
import com.teoretik.graphics.camera.Camera
import com.teoretik.graphics.render.GraphicsSettings
import com.teoretik.graphics.render.Renderer
import com.teoretik.graphics.resources.Shape
import com.teoretik.utils.geometry.Array2D
import com.teoretik.utils.vectors.component1
import com.teoretik.utils.vectors.component2

class ShadowsRenderer(private val lightColorMap : Array2D<LightColor>) : Renderer {
    override fun setView(camera : Camera) {
        Shape.projectionMatrix = camera.projMatrix()
    }

    override fun render() {
        Gdx.gl.glEnable(GL20.GL_BLEND)
        Gdx.gl.glBlendFunc(GL20.GL_DST_COLOR, GL20.GL_ZERO)

        Shape.begin(ShapeRenderer.ShapeType.Filled)

        with(lightColorMap) {
            validIndicesSeparateFilter(
                { it != numRows - 1 },
                { it != numColumns - 1 }
            ).forEach { (i, j, _) ->
                Shape.color = LightColor().run {
                    sequenceOf(0 to 0, 0 to 1, 1 to 0, 1 to 1).forEach { (ii, jj) ->
                        add(this@with[i + ii, j + jj])
                    }
                    scl(0.25f)
                    toColorMask()
                }
                renderShadowSquare(ShadowsProcessor.lightMapToWorldCoordinates(i, j))
            }
        }

        Shape.end()
        Gdx.gl.glDisable(GL20.GL_BLEND)

    }

    private fun renderShadowSquare(vector2: Vector2) {
        val (x, y) = vector2
        val offset = 1f / GraphicsSettings.lightResolution
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
