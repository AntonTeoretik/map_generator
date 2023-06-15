package com.teoretik.graphics.render

import com.badlogic.gdx.maps.tiled.TiledMap
import com.teoretik.components.Floor
import com.teoretik.graphics.camera.Camera

class FloorRenderer(private val floor: Floor) : Renderer {
    private val terrainRenderer = TerrainRenderer(TiledMap().apply { layers.add(floor) })
    private val shadowsRenderer = ShadowsRenderer(floor.lightProcessor.lightColorMap)

    override fun render() {
        terrainRenderer.render()
        shadowsRenderer.render()
    }

    fun setView(camera : Camera) {
        terrainRenderer.setView(camera)
        terrainRenderer.shapeRenderer.projectionMatrix = camera.projMatrix()
        shadowsRenderer.setView(camera)
    }

}