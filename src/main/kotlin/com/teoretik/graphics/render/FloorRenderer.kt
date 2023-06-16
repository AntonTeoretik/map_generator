package com.teoretik.graphics.render

import com.badlogic.gdx.maps.tiled.TiledMap
import com.teoretik.components.Floor
import com.teoretik.graphics.camera.Camera
import com.teoretik.graphics.render.floor.ObjectRenderer
import com.teoretik.graphics.render.floor.ObstacleRenderer
import com.teoretik.graphics.render.floor.ShadowsRenderer
import com.teoretik.graphics.render.floor.TerrainRenderer

class FloorRenderer(private val floor: Floor) : Renderer {

    private val renderers = listOf(
        TerrainRenderer(TiledMap().apply { layers.add(floor) }),
        ObjectRenderer(floor.objects),
        ShadowsRenderer(floor.lightProcessor.lightColorMap),
        ObstacleRenderer(floor.obstacleProcessor)
    )

    override fun render() {
        renderers.forEach {it.render()}
    }

    override fun setView(camera : Camera) {
        renderers.forEach {it.setView(camera)}
    }

}