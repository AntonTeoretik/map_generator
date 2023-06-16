package com.teoretik.graphics.render

import Screen
import com.teoretik.components.GameConfiguration
import com.teoretik.graphics.camera.Camera

class UIRenderer() : Renderer {
    override fun render() {}
    override fun setView(camera: Camera) {}
}

class GameRenderer(configuration: GameConfiguration) : Renderer {
    private val worldRenderer = WorldRenderer(configuration)
    private val uiRenderer = UIRenderer()

    override fun render() {
        worldRenderer.render()
    }
    override fun setView(camera: Camera) {}
}