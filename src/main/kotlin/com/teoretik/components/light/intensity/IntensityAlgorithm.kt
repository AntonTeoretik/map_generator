package com.teoretik.components.light.intensity

import com.badlogic.gdx.math.Vector2

abstract class IntensityAlgorithm {
    abstract fun getFadingFactor(vec: Vector2) : Float
    fun getFadingFactor(x: Float, y : Float, dist: Vector2) : Float {
        return getFadingFactor(dist.cpy().sub(x, y))
    }
}


