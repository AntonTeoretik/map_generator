package com.teoretik.components.light.processors

import com.badlogic.gdx.math.Vector2
import com.teoretik.components.light.HitResult
import com.teoretik.components.obstacles.Obstacle

class NoProcessor() : RayProcessor(null) {
    override fun processRay(start: Vector2, end: Vector2, obstacles: List<Obstacle>): HitResult {
        return HitResult.MISS
    }
}