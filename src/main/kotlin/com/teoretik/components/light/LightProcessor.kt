package com.teoretik.components.light

import com.badlogic.gdx.math.Vector2

abstract class LightProcessor(
    val lightSourceShape: LightSourceShape?,
) {
    abstract fun processRay(start: Vector2, end: Vector2, obstacles: List<Obstacle>): ShadowState
}