package com.teoretik.components

import com.badlogic.gdx.maps.MapGroupLayer
import com.teoretik.components.loaders.FLOOR_NUMBER

class FloorLayer : MapGroupLayer() {
    fun floorNumber() : Int? {
        return try {
            properties[FLOOR_NUMBER] as Int
        } catch (e: Exception) {
            null
        }
    }
}