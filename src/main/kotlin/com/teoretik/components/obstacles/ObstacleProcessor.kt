package com.teoretik.components.obstacles

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.Polygon
import com.badlogic.gdx.math.Vector2
import com.teoretik.components.map.Floor
import com.teoretik.components.loaders.cellToWorldCoordinates
import com.teoretik.geometry.integral.Array2D
import com.teoretik.geometry.integral.InternalRectangles
import com.teoretik.utils.tiles.*
import com.teoretik.utils.vectors.*

class ObstacleProcessor(val floor: Floor) {
    val staticObstacles: List<Obstacle> = updateStaticObstacles()
    val dynamicObstacles : MutableList<Obstacle> = mutableListOf()

    fun isObstacleInCell(x: Int, y: Int): Boolean {
        val (x0, y0) = floor.cellToWorldCoordinates(x, y)
        val shift = 0.5f
        val p = Polygon(
            floatArrayOf(
                x0 - shift, y0 - shift,
                x0 + shift, y0 + shift,
                x0 + shift, y0 - shift,
                x0 - shift, y0 + shift,
            )
        )
        return staticObstacles.any { Intersector.intersectPolygons(p, it.polygon, null) }
    }

    fun isPointInObstacle(point: Vector2) : Boolean {
        return staticObstacles.any { it.polygon.contains(point) }
    }

    private fun updateStaticObstacles(): List<Obstacle> {
        val obstacles = mutableListOf<Obstacle>()
        floor.tileLayers().forEach { layer ->
            val obstacleMap = Array2D(layer.width, layer.height) { _, _ -> false }
            layer.tilesWithIndexes(TiledMapTile::isSolid).forEach {
                val (i, j, tile) = it
                if (tile.isStandard())
                    fillObstacleMapForStandardTile(tile, i, j, layer, obstacleMap)
                else {
                    val (x, y) = layer.cellToWorldCoordinates(i, j)
                    obstacles.add(Obstacle.fromPolygon(x, y, tile.width, tile.height))
                }
            }
            generateMaximalObstacleCoverage(obstacleMap, obstacles)
        }
        return obstacles.toList()
    }

    private fun generateMaximalObstacleCoverage(obstacleMap: Array2D<Boolean>, obstacles: MutableList<Obstacle>) {
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

    companion object {
        private fun fillObstacleMapForStandardTile(
            tile: TiledMapTile,
            i: Int,
            j: Int,
            layer: TiledMapTileLayer,
            obstacleMap: Array2D<Boolean>
        ) {
            repeat(tile.widthInCells) { ii ->
                repeat(tile.heightInCells) { jj ->
                    if (i + ii < layer.width && j + jj < layer.height) {
                        obstacleMap[i + ii, j + jj] = true
                    }
                }
            }
        }
    }
}

private fun TiledMapTile.isSolid(): Boolean = this.properties?.get(Obstacle.SOLID) == true
