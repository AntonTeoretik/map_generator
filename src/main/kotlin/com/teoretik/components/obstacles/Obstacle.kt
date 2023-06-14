package com.teoretik.components.obstacles

import com.badlogic.gdx.maps.MapObject
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject
import com.badlogic.gdx.math.Polygon
import com.teoretik.components.loaders.cellToWorldCoordinates
import com.teoretik.graphics.render.GraphicsSettings
import com.teoretik.utils.vectors.*
import com.teoretik.utils.tiles.*



class Obstacle(
    val polygon: Polygon,
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
                return Obstacle(p)
            }
            // TODO handle other cases?
            return null
        }

        fun fromCell(x0: Int, y0: Int, layer: TiledMapTileLayer): Obstacle? {
            val cell = layer.getCell(x0, y0)
            val tile = cell?.tile ?: return null

            if (tile.properties?.get(SOLID) != true) return null

            val (x, y) = layer.cellToWorldCoordinates(x0, y0)
            val p = createPolygon(x, y, tile.width, tile.height)

            return Obstacle(p)

            // TODO -- check opacity
            // TODO -- need to take in to account rotation

        }

        fun fromPolygon(x: Float, y: Float, width: Float, height: Float): Obstacle {
            val p = createPolygon(x, y, width, height)
            return Obstacle(p)
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
    }

}