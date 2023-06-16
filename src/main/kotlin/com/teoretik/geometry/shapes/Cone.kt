package com.teoretik.geometry.shapes

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.teoretik.geometry.rays.processors.RayProcessor
import com.teoretik.geometry.rays.processors.NoProcessor

class Cone(val direction: Vector2, val angle: Float, val rad: Float) : Shape() {
    override fun hitBox(): Rectangle? {
        if (rad == Float.POSITIVE_INFINITY)
            return null
        return Rectangle(
            -rad, -rad, 2 * rad, 2 * rad
        )
    }
    override val processor: RayProcessor = NoProcessor()
}