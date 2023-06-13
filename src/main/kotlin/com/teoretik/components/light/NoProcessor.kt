package com.teoretik.components.light

import com.badlogic.gdx.math.Vector2

class NoProcessor() : LightProcessor(null) {
    override fun processRay(start: Vector2, end: Vector2, obstacles: List<Obstacle>): ShadowState {
        return ShadowState.SHADOW
    }
}