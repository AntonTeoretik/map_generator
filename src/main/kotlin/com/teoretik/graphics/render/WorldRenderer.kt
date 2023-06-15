package com.teoretik.graphics.render

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.teoretik.components.GameConfiguration

class WorldRenderer(private val gameConfiguration: GameConfiguration) : Renderer {
    private val backgroundColor = Color.BLACK.cpy()

    override fun render() {
        clearBackground()

        gameConfiguration.camera.update()

        val level = gameConfiguration.activeLevel ?: return
        level.renderer.withCamera(gameConfiguration.camera).render()
    }

    private fun clearBackground() {
        val col = backgroundColor
        Gdx.gl.glClearColor(col.r, col.g, col.b, col.a)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    }
}
