package com.teoretik.geometry.rays.processors

import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.Polygon
import com.badlogic.gdx.math.Vector2
import com.teoretik.geometry.rays.HitResult

object PointRayProcessor : RayProcessor {
    override fun processRay(start: Vector2, end: Vector2, obstacles: Sequence<Polygon>, processEndPoint: Boolean): HitResult {
        obstacles.forEach {
            val intersects = Intersector.intersectSegmentPolygon(start, end, it)
            if (intersects && !(processEndPoint && end in it)) return HitResult.MISS
        }
        return HitResult.HIT
    }
}