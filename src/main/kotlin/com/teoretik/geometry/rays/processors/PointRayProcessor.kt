package com.teoretik.geometry.rays.processors

import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.Polygon
import com.badlogic.gdx.math.Vector2
import com.teoretik.components.obstacles.Obstacle
import com.teoretik.geometry.rays.HitResult

class PointRayProcessor() : RayProcessor(null) {
    override fun processRay(start: Vector2, end: Vector2, obstacles: Sequence<Polygon>): HitResult {
        obstacles.forEach {
            val intersects = Intersector.intersectSegmentPolygon(start, end, it)
//            if (intersects && !it.contains(end)) return HitResult.MISS
            if (intersects) return HitResult.MISS

        }
        return HitResult.HIT
    }
}