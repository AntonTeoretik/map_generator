package com.teoretik.components.light.shape

import com.badlogic.gdx.math.Rectangle
import com.teoretik.components.light.processors.PointLightProcessor

class PointLightSourceShape(val rad: Float) : LightSourceShape() {
    override fun provideRegion(): Rectangle? {
        if (rad == Float.POSITIVE_INFINITY)
            return null
        return Rectangle(
            -rad, -rad, 2 * rad, 2 * rad
        )
    }
    override val processor = PointLightProcessor()
}