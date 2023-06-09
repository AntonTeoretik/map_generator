package com.teoretik.components.light

import com.badlogic.gdx.maps.objects.TextureMapObject
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.Polygon
import com.badlogic.gdx.math.Vector2
import com.teoretik.components.Floor
import com.teoretik.graphics.render.GraphicsSettings
import com.teoretik.graphics.render.GraphicsSettings.Companion.unitScale

class LightProcessor() {
    companion object {
        fun processLight(floor: Floor) {
            floor.lightMap.forEach{ it.forEach(Light::clear) }

            floor.objects
                .filterIsInstance<TextureMapObject>()
                .filter { it.properties["lightProperties"] != null }
                .forEach {
                    val x = it.x * unitScale + 0.5f
                    val y = it.y * unitScale + 0.5f
                    processOneLightSource(Vector2(x, y), floor)
            }
        }

        private fun processOneLightSource(
            v1: Vector2,
            floor: Floor,
        ) {

            for ( i in 0 until floor.lightMap.count() ) {
                for ( j in 0 until  floor.lightMap[i].count()) {
                    val v2 = floor.cellToWorldCoordinates(
                        i.toFloat() / GraphicsSettings.resolution,
                        j.toFloat() / GraphicsSettings.resolution
                    )

                    floor.lightMap[i][j].add(processLightRay(v1, v2, floor.obstacles) )
                }
            }
        }

        private fun processLightRay(v1: Vector2, v2: Vector2, obstacles: MutableList<Obstacle>): Light {
            obstacles.forEach {
                val intersects = Intersector.intersectSegmentPolygon(v1, v2, it.polygon)
                if (intersects && !it.polygon.contains(v2)) return Light()
            }

            val factor = (1f / (1f + v2.dst2(v1) * 0.2f) )
            //val factor = 1.0f
            return Light(1f, 1f, 0.9f).scl(factor) as Light
        }
    }
}