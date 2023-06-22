package com.teoretik.graphics.render

import com.teoretik.components.map.Level
import com.teoretik.graphics.camera.Camera


class LevelRenderer(private val level: Level) : Renderer {
    override fun render() {
        level.floors[level.activeFloor]?.renderer?.render()
    }

    override fun setView(camera: Camera) {
        level.floors[level.activeFloor]?.renderer?.setView(camera)
    }
}