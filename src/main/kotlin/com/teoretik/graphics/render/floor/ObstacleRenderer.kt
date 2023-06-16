package com.teoretik.graphics.render.floor

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.teoretik.components.obstacles.ObstacleProcessor
import com.teoretik.graphics.camera.Camera
import com.teoretik.graphics.render.Renderer
import com.teoretik.graphics.resources.Shape

class ObstacleRenderer(private val obstacleProcessor : ObstacleProcessor) : Renderer {

    override fun setView(camera : Camera) {
        Shape.projectionMatrix = camera.projMatrix()
    }

    override fun render() {
        Shape.begin(ShapeRenderer.ShapeType.Line)

        Gdx.gl.glEnable(GL20.GL_BLEND)
        Gdx.gl.glBlendFunc(GL20.GL_ONE_MINUS_SRC_ALPHA, GL20.GL_SRC_ALPHA)

        for (obst in obstacleProcessor.staticObstacles) {
            val p = obst.polygon

            Shape.color = Color.RED.cpy()
            Shape.color.a = 0.5f
            Shape.polygon(p.transformedVertices)
        }
        Shape.end()
    }

}