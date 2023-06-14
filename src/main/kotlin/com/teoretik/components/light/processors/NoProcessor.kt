package com.teoretik.components.light.processors

import com.badlogic.gdx.math.Vector2
import com.teoretik.components.light.ShadowState
import com.teoretik.components.obstacles.Obstacle

class NoProcessor() : LightProcessor(null) {
    override fun processRay(start: Vector2, end: Vector2, obstacles: List<Obstacle>): ShadowState {
        return ShadowState.SHADOW
    }
}