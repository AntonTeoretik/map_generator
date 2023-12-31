package com.teoretik.graphics.render

import com.badlogic.gdx.maps.tiled.TiledMap
import com.teoretik.components.map.Floor
import com.teoretik.graphics.camera.Camera
import com.teoretik.graphics.render.floor.*

class FloorRenderer(private val floor: Floor) : Renderer {

    private val renderers = listOf(
        TerrainRenderer(TiledMap().apply { layers.add(floor) }),
        ObjectRenderer(floor.objects),
        ShadowsRenderer(floor.lightProcessor.lightMap),
        VisibilityRenderer(floor.viewPointProcessor, floor.memoryProcessor),
//        ObstacleRenderer(floor.obstacleProcessor)
    )

    override fun render() {
        floor.lightProcessor.updateLight()
        floor.memoryProcessor.updateDiscoveredCells()
        renderers.forEach {it.render()}
    }

    override fun setView(camera : Camera) {
        renderers.forEach {it.setView(camera)}
    }

}