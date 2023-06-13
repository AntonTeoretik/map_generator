package com.teoretik.components.light

import com.badlogic.gdx.math.Vector2

open class DynamicLightSource(
    x : Float,
    y : Float,
    light: Light,
    shape: LightSourceShape,
    val intensityAlgorithm: IntensityAlgorithm = Linear(),
) : LightSource(x, y, light, shape) {
    override var x: Float = x
        set(value) {
            field = value
            changed = true
        }

    override var y: Float = y
        set(value) {
            field = value
            changed = true
        }

    var changed = false
}