package com.teoretik.graphics.render

import com.teoretik.components.Level
import com.teoretik.graphics.camera.Camera


class LevelRenderer(private val level: Level) : Renderer {
    override fun render() {
        level.floors[level.activeFloor]?.renderer?.render()
    }

    fun withCamera(camera: Camera) : LevelRenderer {
        level.floors[level.activeFloor]?.renderer?.setView(camera)
        return this
    }
}