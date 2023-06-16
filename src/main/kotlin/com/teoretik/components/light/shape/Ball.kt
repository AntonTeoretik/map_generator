package com.teoretik.components.light.shape

import com.badlogic.gdx.math.Rectangle
import com.teoretik.components.light.processors.PointLightProcessor

class Ball(val rad: Float) : Shape() {
    override fun provideRegion(): Rectangle? {
        if (rad == Float.POSITIVE_INFINITY)
            return null
        return Rectangle(
            -rad, -rad, 2 * rad, 2 * rad
        )
    }
    override val processor = PointLightProcessor()
}