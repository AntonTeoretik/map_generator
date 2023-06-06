package com.teoretik.graphics.render

import Screen
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.utils.viewport.FitViewport

class WorldRenderer(val screen: Screen) {
    // Renderers
    val mapRenderer = MapRenderer(screen)
    val visionRenderer = VisionRenderer()
    val shadowsRenderer = ShadowsRenderer()




    val backgroundColor = Color.BLACK

    private val viewport = FitViewport(
        GraphicsSettings.worldHeight * Gdx.graphics.width / Gdx.graphics.height,
        GraphicsSettings.worldHeight
    )

    fun render() {
        viewport.apply()

        // background
        val col = backgroundColor
        Gdx.gl.glClearColor(col.r, col.g, col.b, col.a)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // cells
        mapRenderer.setView(screen.position)
        screen.gameSetup.renderer.render()
        mapRenderer.renderVisionRadius(screen.position)

    }
}
