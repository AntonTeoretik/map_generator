package com.teoretik.components.light.processors

import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.Vector2
import com.teoretik.components.light.HitResult
import com.teoretik.components.obstacles.Obstacle

class PointRayProcessor() : RayProcessor(null) {
    override fun processRay(start: Vector2, end: Vector2, obstacles: List<Obstacle>): HitResult {
        obstacles.forEach {
            val intersects = Intersector.intersectSegmentPolygon(start, end, it.polygon)
            if (intersects && !it.polygon.contains(end)) return HitResult.MISS
            if (intersects) return HitResult.MISS

        }
        return HitResult.HIT
    }
}