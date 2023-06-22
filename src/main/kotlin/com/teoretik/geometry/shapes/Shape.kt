package com.teoretik.geometry.shapes

import com.badlogic.gdx.math.Polygon
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.teoretik.geometry.rays.HitResult
import com.teoretik.geometry.rays.processors.RayProcessor

abstract class Shape {
    abstract fun hitBox(): Rectangle?
    protected abstract val processor: RayProcessor
    abstract fun processRay(
        v1: Vector2,
        v2: Vector2,
        obstacles: Sequence<Polygon>,
        processEndPoint: Boolean = true
    ): HitResult
}

