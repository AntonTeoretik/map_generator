package com.teoretik.geometry.shapes

import com.badlogic.gdx.math.Polygon
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.teoretik.geometry.rays.HitResult
import com.teoretik.geometry.rays.processors.PointRayProcessor

class Ball(val rad: Float) : Shape() {
    override fun hitBox(): Rectangle? {
        if (rad == Float.POSITIVE_INFINITY)
            return null
        return Rectangle(
            -rad, -rad, 2 * rad, 2 * rad
        )
    }
    override val processor = PointRayProcessor

    override fun processRay(
        v1: Vector2,
        v2: Vector2,
        obstacles: Sequence<Polygon>,
        processEndPoint: Boolean
    ): HitResult {
        if (v1.dst(v2) > rad) return HitResult.MISS
        return processor.processRay(v1, v2, obstacles, processEndPoint)
    }
}