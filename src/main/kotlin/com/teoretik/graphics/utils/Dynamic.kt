package com.teoretik.graphics.utils

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3

class Dynamic<T>(
    private var current: T,
    private val duration: Float,
    private val interpolate: T.(T, Float) -> T,
    private val cpy: T.() -> T = { this }
) {
    private var timer: Float = 0.0f
    private var next: T? = null

    private var animationInProgress: Boolean = false

    private fun getInterpolated(time: Float): T {
        return if (next != null)
            current.cpy().interpolate(next!!, time)
        else
            current.cpy()
    }

    fun update(delta: Float) {
        if (animationInProgress) {
            timer += delta
            if (timer > duration) {
                animationInProgress = false
                timer = 0.0f
                next?.run {
                    current = this.cpy()
                    next = null
                }
            }
        }
    }

    fun get(): T = when {
        animationInProgress -> getInterpolated(timer / duration)
        else -> current.cpy()
    }

    fun set(next: T) {
        this.current = this.get()
        this.next = next.cpy()

        timer = 0.0f
        animationInProgress = true
    }
}

fun Vector3.toDynamic(duration: Float): Dynamic<Vector3> = Dynamic(this, duration, Vector3::lerp, Vector3::cpy)
fun Vector2.toDynamic(duration: Float): Dynamic<Vector2> = Dynamic(this, duration, Vector2::lerp, Vector2::cpy)
fun Float.toDynamic(duration: Float): Dynamic<Float> =
    Dynamic(this, duration, { next, t -> (1 - t) * this + t * next })