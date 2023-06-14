package com.teoretik.components.obstacles

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.teoretik.components.Floor
import com.teoretik.components.loaders.cellToWorldCoordinates
import com.teoretik.graphics.render.GraphicsSettings
import com.teoretik.utils.geometry.Array2D
import com.teoretik.utils.geometry.InternalRectangles

class ObstacleProcessor(val floor: Floor) {

    var obstacles: MutableList<Obstacle> = floor.obstacles

    fun updateObstacles() {
        obstacles.clear()
        for (layer in floor.layers.filterIsInstance<TiledMapTileLayer>()) {
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
            processStandardObstacles(obstacleMap, obstacles)
        }
    }



    fun processStandardObstacles(obstacleMap: Array2D<Boolean>, obstacles: MutableList<Obstacle>) {
        InternalRectangles(obstacleMap).maximums.forEach {
            obstacles.add(
                Obstacle.fromPolygon(
                    it.x0.toFloat(),
                    it.y0.toFloat(),
                    it.getWidth().toFloat() + 1f,
                    it.getHeight().toFloat() + 1f
                )
            )
        }
    }
}