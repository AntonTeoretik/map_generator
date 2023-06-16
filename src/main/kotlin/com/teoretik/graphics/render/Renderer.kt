package com.teoretik.graphics.render

import com.badlogic.gdx.math.Rectangle
import com.teoretik.graphics.camera.Camera
import kotlin.math.abs

interface Renderer {

    fun render()
    fun setView(camera: Camera)
}

interface BoundedRenderer : Renderer {
    val viewBounds : Rectangle
    override fun setView(camera: Camera) {
        val pos = camera.getPosition()
        val zoom = camera.getZoom()
        with(camera.camera) {
            val width: Float = viewportWidth * zoom
            val height: Float = viewportHeight * zoom
            val w: Float = width * abs(up.y) + height * abs(up.x)
            val h: Float = height * abs(up.y) + width * abs(up.x)

            viewBounds.set(pos.x - w / 2, pos.y - h / 2, w, h)
        }
    }
}

