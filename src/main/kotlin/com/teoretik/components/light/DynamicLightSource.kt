package com.teoretik.components.light

open class DynamicLightSource(
    light: Light,
    shape: LightSourceShape
) : LightSource(light, shape) {
    override var x: Float = 0f
        set(value) {
            field = value
            changed = true
        }

    override var y: Float = 0f
        set(value) {
            field = value
            changed = true
        }

    var changed = false
}