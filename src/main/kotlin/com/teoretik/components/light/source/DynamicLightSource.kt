package com.teoretik.components.light.source

import com.teoretik.components.light.intensity.IntensityAlgorithm
import com.teoretik.components.light.LightColor
import com.teoretik.components.light.shape.Shape
import com.teoretik.components.light.intensity.Linear

open class DynamicLightSource(
    x : Float,
    y : Float,
    lightColor: LightColor,
    shape: Shape,
    val intensityAlgorithm: IntensityAlgorithm = Linear(),
) : LightSource(x, y, lightColor, shape) {
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