package com.teoretik.components.light.processors

import com.badlogic.gdx.math.Vector2
import com.teoretik.components.light.ShadowState
import com.teoretik.components.light.shape.LightSourceShape
import com.teoretik.components.obstacles.Obstacle

abstract class LightProcessor(
    val lightSourceShape: LightSourceShape?,
) {
    abstract fun processRay(start: Vector2, end: Vector2, obstacles: List<Obstacle>): ShadowState
}