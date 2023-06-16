package com.teoretik.geometry.rays.processors

import com.badlogic.gdx.math.Polygon
import com.badlogic.gdx.math.Vector2
import com.teoretik.components.obstacles.Obstacle
import com.teoretik.geometry.rays.HitResult

class NoProcessor() : RayProcessor(null) {
    override fun processRay(start: Vector2, end: Vector2, obstacles: Sequence<Polygon>): HitResult {
        return HitResult.MISS
    }
}