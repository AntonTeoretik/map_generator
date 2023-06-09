package com.teoretik.components

import com.badlogic.gdx.maps.MapGroupLayer
import com.badlogic.gdx.maps.objects.TextureMapObject
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.math.Vector2
import com.teoretik.components.light.Light
import com.teoretik.components.light.LightProcessor
import com.teoretik.components.light.Obstacle
import com.teoretik.components.loaders.FLOOR_NUMBER
import com.teoretik.graphics.render.GraphicsSettings

typealias Array2D<T> = List<List<T>>

class Floor : MapGroupLayer() {
    var width: Int = 0
        private set
    var height: Int = 0
        private set
    var floorNumber: Int = 0
        private set

    var obstacles: MutableList<Obstacle> = mutableListOf()
    var lightMap: Array2D<Light> = listOf()
        private set

    fun updateLight() {
//        println((objects[objects.count() - 1] as TextureMapObject).y)
        //println(lightMap[0][0].x)
        LightProcessor.processLight(this)
    }

    fun updateObstacles() {

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

        lightMap = List(GraphicsSettings.resolution * width + 1) {
            List(GraphicsSettings.resolution * height + 1) {
                Light()
            }
        }
    }

    fun cellToWorldCoordinates(x: Float, y: Float): Vector2 {
        return Vector2(x + offsetX, -y + height + offsetY)
    }
}