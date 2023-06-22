package com.teoretik.components.map

import com.badlogic.gdx.math.Vector2
import com.teoretik.components.viewpoint.Visibility
import com.teoretik.geometry.integral.Array2D
import com.teoretik.graphics.render.GraphicsSettings.visibilityResolution

class MemoryProcessor(val floor: Floor) {
    val discoverMap by lazy { Array2D(floor.width, floor.height) { _, _ -> false } }

    fun isDiscovered(vector2: Vector2) : Boolean {
        val x = vector2.x.toInt()
        val y = vector2.y.toInt()
        return discoverMap[x, y] ?: false
    }

    fun updateDiscoveredCells() {
        val visibilityMap = floor.viewPointProcessor.totalVisibility

        visibilityMap.iterate().filter { it.third == Visibility.VISIBLE }.forEach {
            val (i, j, _) = it
            discoverMap[i / visibilityResolution, j / visibilityResolution] = true
        }
    }
}
