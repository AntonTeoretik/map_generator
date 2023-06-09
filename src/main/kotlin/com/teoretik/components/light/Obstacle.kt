package com.teoretik.components.light

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.maps.MapObject
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject
import com.badlogic.gdx.math.Polygon
import com.badlogic.gdx.math.Vector3
import com.teoretik.components.loaders.cellToWorldCoordinates
import com.teoretik.graphics.render.GraphicsSettings

class Light(x: Float = 0f, y: Float = 0f, z: Float = 0f) : Vector3(x, y, z) {
    fun toColorMask(): Color = Color(x, y, z, 0.0f)
    fun clear() {
        x = 0f
        y = 0f
        z = 0f
    }
}

class Obstacle(
    val polygon: Polygon,
    val opacity: Float,
) {
    companion object {
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