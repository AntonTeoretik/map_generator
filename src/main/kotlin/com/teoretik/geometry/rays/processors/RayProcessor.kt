package com.teoretik.geometry.rays.processors

import com.badlogic.gdx.math.Vector2
import com.teoretik.geometry.shapes.Shape
import com.teoretik.components.obstacles.Obstacle
import com.teoretik.geometry.rays.HitResult

abstract class RayProcessor(
    val shape: Shape?,
) {
    abstract fun processRay(start: Vector2, end: Vector2, obstacles: List<Obstacle>): HitResult
}