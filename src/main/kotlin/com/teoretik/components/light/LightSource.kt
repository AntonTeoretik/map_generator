package com.teoretik.components.light

import com.badlogic.gdx.maps.MapObject
import com.badlogic.gdx.math.Vector2

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

