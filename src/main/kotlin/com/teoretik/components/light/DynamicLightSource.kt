package com.teoretik.components.light

open class DynamicLightSource(
    light: Light,
    shape: LightSourceShape
) : LightSource(light, shape) {
    override var x: Float = 0f
    override var y: Float = 0f
}