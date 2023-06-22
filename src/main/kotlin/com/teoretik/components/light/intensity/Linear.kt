package com.teoretik.components.light.intensity

import com.badlogic.gdx.math.Vector2
import kotlin.math.max

class Linear(val rad : Float = 17f) : IntensityAlgorithm() {
    override fun getFadingFactor(vec: Vector2): Float {
        return max(0f, 1.0f - 1.0f / rad * vec.len())
    }
}