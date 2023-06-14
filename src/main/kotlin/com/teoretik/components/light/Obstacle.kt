package com.teoretik.components.light

import com.badlogic.gdx.maps.MapObject
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject
import com.badlogic.gdx.math.Polygon
import com.teoretik.components.light.geometry.Array2D
import com.teoretik.components.light.geometry.IntegralRect
import com.teoretik.components.light.geometry.InternalRectangles
import com.teoretik.components.loaders.cellToWorldCoordinates
import com.teoretik.graphics.render.GraphicsSettings

object ObstacleProcessor {
    private fun collectEdgeCells(obstacleMap: Array<Array<Boolean>>): Array<Array<Boolean>> {
        val newObstacleMap = Array(obstacleMap.size) { i ->
            Array(obstacleMap[i].size) { j ->
                var isInternal = obstacleMap[i][j]
                (-1..1).forEach { ii ->
                    (-1..1).forEach { jj ->
                        val nbr = obstacleMap.getOrNull(i + ii)?.getOrNull(j + jj) ?: false
                        isInternal = isInternal && nbr
                    }
                }
                !isInternal && obstacleMap[i][j]
            }
        }
        return newObstacleMap
    }

    private fun collectRectangles() {

    }

    fun processStandardObstacles(obstacleMap: Array2D<Boolean>, obstacles: MutableList<Obstacle>) {

        InternalRectangles(obstacleMap).maximals.forEach {
            println("Max obstacle $it")
            obstacles.add(Obstacle.fromPolygon(
                it.x0.toFloat(),
                it.y0.toFloat(),
                it.getWidth().toFloat() + 1f,
                it.getHeight().toFloat() + 1f
            ))
        }
    }
}


class Obstacle(
    val polygon: Polygon,
    val opacity: Float,
) {
    companion object {
        val DYNAMIC = "dynamic"
        val SOLID = "solid"
        val eps = 0.001f

        fun fromObject(obj: MapObject): Obstacle? {
            if (obj.properties?.get(SOLID) != true) return null

            if (obj is TiledMapTileMapObject) {
                val width = (obj.properties["width"] as Float) * GraphicsSettings.unitScale
                val height = (obj.properties["height"] as Float) * GraphicsSettings.unitScale

                val p = createPolygon(obj.x, obj.y, width, height)
                p.rotate(obj.rotation)
                return Obstacle(p, obj.opacity)
            }
            // TODO handle other cases?
            return null
        }

        fun fromCell(x: Int, y: Int, layer: TiledMapTileLayer): Obstacle? {
            val cell = layer.getCell(x, y)
            val tile = cell?.tile ?: return null

            if (tile.properties?.get(SOLID) != true) return null

            val vec2 = layer.cellToWorldCoordinates(x, y)
            val width = tile.textureRegion.regionWidth * GraphicsSettings.unitScale
            val height = tile.textureRegion.regionHeight * GraphicsSettings.unitScale

            val p = createPolygon(vec2.x, vec2.y, width, height)

            return Obstacle(p, 1f)

            // TODO -- check opacity
            // TODO -- need to take in to account rotation

        }

        fun fromPolygon(x: Float, y: Float, width: Float, height: Float): Obstacle {
            val p = createPolygon(x, y, width, height)
            return Obstacle(p, 1f)
        }


        private fun createPolygon(x: Float, y: Float, width: Float, height: Float): Polygon {
            val p = Polygon(
                floatArrayOf(
                    x + eps, y + eps,
                    x + width - eps, y + eps,
                    x + width - eps, y + height - eps,
                    x + eps, y + height - eps
                )
            )
            p.setOrigin(x + width / 2.0f, y + height / 2.0f)
            return p
        }

        fun isStandard(width: Int, height: Int): Boolean {
            return width % GraphicsSettings.pixelResolution == 0 && height % GraphicsSettings.pixelResolution == 0
        }
    }

}