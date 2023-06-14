package com.teoretik.components

import com.badlogic.gdx.maps.MapGroupLayer
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.math.Vector2
import com.teoretik.components.light.processors.FloorLightProcessor
import com.teoretik.components.obstacles.Obstacle
import com.teoretik.components.obstacles.ObstacleProcessor
import com.teoretik.geometry.Array2D
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

    val lightProcessor by lazy {
        FloorLightProcessor(this)
    }

    val lightMap by lazy { lightProcessor.lightColorMap }

    fun fullUpdateLight() {
        lightProcessor.processStaticLights()
        lightProcessor.computeFinalLightMap()
    }

    fun updateObstacles() {
        obstacles = mutableListOf()
        for (layer in layers.filterIsInstance<TiledMapTileLayer>()) {
            val obstacleMap = Array2D(layer.width, layer.height) { _, _ -> false }
            (0 until layer.width).forEach { i ->
                (0 until layer.height).forEach { j ->
                    val tile = layer.getCell(i, j)?.tile
                    if (tile?.properties?.get(Obstacle.SOLID) == true) {
                        if (Obstacle.isStandard(tile.textureRegion.regionWidth, tile.textureRegion.regionHeight)) {
                            val width = tile.textureRegion.regionWidth / GraphicsSettings.pixelResolution
                            val height = tile.textureRegion.regionHeight / GraphicsSettings.pixelResolution

                            repeat(width) { ii ->
                                repeat(height) { jj ->
                                    if (i + ii < layer.width && j + jj < layer.height) {
                                        obstacleMap[i + ii, j + jj] = true
                                    }
                                }
                            }
                        } else {
                            val vec2 = layer.cellToWorldCoordinates(i, j)

                            val width = tile.textureRegion.regionWidth * GraphicsSettings.unitScale
                            val height = tile.textureRegion.regionHeight * GraphicsSettings.unitScale

                            obstacles.add(Obstacle.fromPolygon(vec2.x, vec2.y, width, height))
                        }
                    }
                }
            }
            ObstacleProcessor.processStandardObstacles(obstacleMap, obstacles)
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