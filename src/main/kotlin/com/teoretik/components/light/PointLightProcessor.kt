package com.teoretik.components.light

import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.Vector2

class PointLightProcessor() : LightProcessor(null) {
    override fun processRay(start: Vector2, end: Vector2, obstacles: List<Obstacle>): ShadowState {
        obstacles.forEach {
            val intersects = Intersector.intersectSegmentPolygon(start, end, it.polygon)
            if (intersects && !it.polygon.contains(end)) return ShadowState.SHADOW
        }
        return ShadowState.LIGHT
    }
}