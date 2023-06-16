package com.teoretik.graphics.render

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.teoretik.components.GameConfiguration
import com.teoretik.graphics.camera.Camera

class WorldRenderer(private val gameConfiguration: GameConfiguration) : Renderer {
    private val backgroundColor = Color.BLACK.cpy()

    override fun render() {
        clearBackground()

        gameConfiguration.camera.update()

        setView(gameConfiguration.camera)
        gameConfiguration.activeLevel?.renderer?.render()
    }

    override fun setView(camera: Camera) {
        gameConfiguration.activeLevel?.renderer?.setView(camera)
    }

    private fun clearBackground() {
        val col = backgroundColor
        Gdx.gl.glClearColor(col.r, col.g, col.b, col.a)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    }
}
