package com.teoretik.graphics.render

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.teoretik.graphics.camera.Camera

class ShadowsRenderer : Renderer {
    private val shapeRenderer = ShapeRenderer()
    fun setView(camera : Camera) {
        shapeRenderer.projectionMatrix = camera.projMatrix()
    }

    override fun render() {
    }

}
