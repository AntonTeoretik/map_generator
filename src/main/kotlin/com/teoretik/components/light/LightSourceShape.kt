package com.teoretik.components.light

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2

abstract class LightSourceShape {
    abstract fun provideRegion(): Rectangle?
}

class PointLightSourceShape(val rad: Float) : LightSourceShape() {
    override fun provideRegion(): Rectangle? {
        if (rad == Float.POSITIVE_INFINITY)
            return null
        return Rectangle(
            -rad, -rad, 2 * rad, 2 * rad
        )
    }
}

class ConeLightSourceShape(val direction: Vector2, val angle: Float, val rad: Float) : LightSourceShape() {
    override fun provideRegion(): Rectangle? {
        if (rad == Float.POSITIVE_INFINITY)
            return null
        return Rectangle(
            -rad, -rad, 2 * rad, 2 * rad
        )
    }
}