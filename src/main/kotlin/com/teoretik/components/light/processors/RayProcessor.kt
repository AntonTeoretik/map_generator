package com.teoretik.components.light.processors

import com.badlogic.gdx.math.Vector2
import com.teoretik.components.light.HitResult
import com.teoretik.geometry.shapes.Shape
import com.teoretik.components.obstacles.Obstacle

abstract class RayProcessor(
    val shape: Shape?,
) {
    abstract fun processRay(start: Vector2, end: Vector2, obstacles: List<Obstacle>): HitResult
}