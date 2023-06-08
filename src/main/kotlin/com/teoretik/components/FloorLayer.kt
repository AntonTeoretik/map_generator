package com.teoretik.components

import com.badlogic.gdx.maps.MapGroupLayer
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.teoretik.components.loaders.FLOOR_NUMBER
import com.teoretik.graphics.light.Light

class FloorLayer() : MapGroupLayer() {
    var width: Int = 0
    var height: Int = 0
    var floorNumber: Int = 0

    var lightMap: List<List<Light>> = listOf()

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

        lightMap = List(width) {
            List(height) {
                Light()
            }
        }
    }

}