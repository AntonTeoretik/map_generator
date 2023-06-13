package com.teoretik.components.light

import com.badlogic.gdx.math.Vector2
import kotlin.math.max
import kotlin.math.sqrt

abstract class IntensityAlgorithm {
    abstract fun getFadingFactor(vec: Vector2) : Float
    fun getFadingFactor(x: Float, y : Float, dist: Vector2) : Float {
        return getFadingFactor(dist.cpy().sub(x, y))
    }
}


/**
 * implements a function f(x) = b + 1 / (factor * ( x - a )), such that
 * f(0) = 1
 * f(rad) = 0
 */
class InvDistance(val factor : Float = 1f, rad: Float? = null) : IntensityAlgorithm() {
    private val a = rad?.run { rad/2 - sqrt(rad*rad / 4 + rad / factor) } ?: 1f
    private val b = rad?.run {1f / (factor * (a - rad)) } ?: 0f

    override fun getFadingFactor(vec: Vector2): Float {
        return max(0f, b + 1.0f / (factor * (vec.len() - a)))
    }
}

class Linear(val rad : Float = 17f) : IntensityAlgorithm() {
    override fun getFadingFactor(vec: Vector2): Float {
        //return 1.0f / (1.0f + 2 * vec.len())
        return max(0f, 1.0f - 1.0f / rad * vec.len())
    }
}
