package com.teoretik.components.obstacles

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.teoretik.components.Floor
import com.teoretik.components.loaders.cellToWorldCoordinates
import com.teoretik.components.tilesWithIndexes
import com.teoretik.utils.geometry.Array2D
import com.teoretik.utils.geometry.InternalRectangles
import com.teoretik.utils.tiles.*
import com.teoretik.utils.vectors.*

private fun TiledMapTile.isSolid(): Boolean = this.properties?.get(Obstacle.SOLID) == true

class ObstacleProcessor(val floor: Floor) {

    var obstacles: MutableList<Obstacle> = floor.obstacles

    fun updateObstacles() {
        obstacles.clear()

        floor.tileLayers().forEach { layer ->
            val obstacleMap = Array2D(layer.width, layer.height) { _, _ -> false }
            layer.tilesWithIndexes(TiledMapTile::isSolid)
                .forEach {
                    val (i, j, tile) = it
                    if (tile.isStandard()) {
                        repeat(tile.widthInCells) { ii ->
                            repeat(tile.heightInCells) { jj ->
                                if (i + ii < layer.width && j + jj < layer.height) {
                                    obstacleMap[i + ii, j + jj] = true
                                }
                            }
                        }
                    } else {
                        val (x, y) = layer.cellToWorldCoordinates(i, j)
                        obstacles.add(Obstacle.fromPolygon(x, y, tile.width, tile.height))
                    }
                }
            processStandardObstacles(obstacleMap)
        }
    }


    private fun processStandardObstacles(obstacleMap: Array2D<Boolean>) {
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
