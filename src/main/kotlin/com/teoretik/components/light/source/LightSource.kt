package com.teoretik.components.light.source

import com.badlogic.gdx.maps.MapObject
import com.badlogic.gdx.math.Vector2
import com.teoretik.components.light.intensity.IntensityAlgorithm
import com.teoretik.components.light.Light
import com.teoretik.components.light.shape.LightSourceShape
import com.teoretik.components.light.intensity.Linear

abstract class LightSource(
    open val x: Float,
    open val y: Float,
    val light: Light,
    val shape: LightSourceShape,
    val algorithm: IntensityAlgorithm = Linear()
) : MapObject() {
    fun computeLightInPoint(vec: Vector2): Light {
        return light.cpy().scl(algorithm.getFadingFactor(x, y, vec))
    }

    var switchedOn = true
}

