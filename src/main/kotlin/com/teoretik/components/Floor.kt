package com.teoretik.components

import com.badlogic.gdx.maps.MapGroupLayer
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.math.Vector2
import com.teoretik.components.light.processors.FloorLightProcessor
import com.teoretik.components.obstacles.Obstacle
import com.teoretik.components.obstacles.ObstacleProcessor
import com.teoretik.utils.geometry.Array2D
import com.teoretik.components.loaders.FLOOR_NUMBER
import com.teoretik.components.loaders.cellToWorldCoordinates
import com.teoretik.graphics.render.GraphicsSettings

class Floor : MapGroupLayer() {
    var width: Int = 0
        private set
    var height: Int = 0
        private set
    var floorNumber: Int = 0
        private set

    var obstacles: MutableList<Obstacle> = mutableListOf()

    val obstacleProcessor by lazy { ObstacleProcessor(this) }
    val lightProcessor by lazy { FloorLightProcessor(this) }

    val lightMap by lazy { lightProcessor.lightColorMap }

    fun fullUpdateLight() {
        lightProcessor.processStaticLights()
        lightProcessor.computeFinalLightMap()
    }

    fun updateObstacles() {
        obstacleProcessor.updateObstacles()
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