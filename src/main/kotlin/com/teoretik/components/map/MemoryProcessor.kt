package com.teoretik.components.map

import com.badlogic.gdx.math.Vector2
import com.teoretik.components.viewpoint.Visibility
import com.teoretik.geometry.integral.Array2D
import com.teoretik.graphics.render.GraphicsSettings.visibilityResolution
import com.teoretik.graphics.render.GraphicsSettings.memoryResolution


class MemoryProcessor(val floor: Floor) {
    private val discoverMap by lazy {
        Array2D(floor.width * memoryResolution, floor.height * memoryResolution) { _, _ -> false }
    }

    fun isDiscovered(vector2: Vector2): Boolean {
        val i = (vector2.x * memoryResolution).toInt()
        val j = (vector2.y * memoryResolution).toInt()
        return discoverMap[i, j] ?: false
    }

    fun updateDiscoveredCells() {
        val visibilityMap = floor.viewPointProcessor.totalVisibility

        visibilityMap.iterate().filter { it.third == Visibility.VISIBLE }.forEach {
            val (i, j, _) = it
            discoverMap[i * memoryResolution / visibilityResolution, j * memoryResolution / visibilityResolution] = true
        }
    }
}
