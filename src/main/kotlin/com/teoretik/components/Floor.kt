package com.teoretik.components

import com.badlogic.gdx.maps.MapGroupLayer
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.math.Vector2
import com.teoretik.components.light.FloorLightProcessor
import com.teoretik.components.light.Obstacle
import com.teoretik.components.loaders.FLOOR_NUMBER

class Floor : MapGroupLayer() {
    var width: Int = 0
        private set
    var height: Int = 0
        private set
    var floorNumber: Int = 0
        private set

    var obstacles: MutableList<Obstacle> = mutableListOf()

    val lightProcessor by lazy {
        FloorLightProcessor(this)
    }

    val lightMap by lazy { lightProcessor.lightMap }

    fun updateLightmap() {
        lightProcessor.computeFinalLightMap()
    }

    fun fullUpdateLight() {
        //TODO -- make more efficient
        // (use only potential visible cells)
        // use static light where is possible
        // block ray if hit ones
        lightProcessor.processStaticLights()
        lightProcessor.computeFinalLightMap()
        //FloorLightProcessor.processLight(this)
    }

    fun updateObstacles() {
        obstacles = mutableListOf()
        for (layer in layers.filterIsInstance<TiledMapTileLayer>()) {
            (0 until layer.width).forEach { i ->
                (0 until layer.height).forEach { j ->
                    val obst = Obstacle.fromCell(i, j, layer)
                    if (obst != null) obstacles.add(obst)
                }
            }
        }
    }

    fun update() {
        for (layer in layers)
            if (layer is TiledMapTileLayer) {
                width = layer.width
                height = layer.height
            }

        floorNumber = run {
            try {
                properties[FLOOR_NUMBER] as Int
            } catch (e: Exception) {
                0
            }
        }
    }

    fun floorToWorldCoordinates(x: Float, y: Float): Vector2 = Vector2(x, height - y)
}