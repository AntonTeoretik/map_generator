package com.teoretik.components.light.shape

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.teoretik.components.light.processors.LightProcessor
import com.teoretik.components.light.processors.NoProcessor

class ConeLightSourceShape(val direction: Vector2, val angle: Float, val rad: Float) : LightSourceShape() {
    override fun provideRegion(): Rectangle? {
        if (rad == Float.POSITIVE_INFINITY)
            return null
        return Rectangle(
            -rad, -rad, 2 * rad, 2 * rad
        )
    }
    override val processor: LightProcessor = NoProcessor()
}