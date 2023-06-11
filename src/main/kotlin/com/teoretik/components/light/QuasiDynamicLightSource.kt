package com.teoretik.components.light

class QuasiDynamicLightSource(
    light: Light,
    shape: LightSourceShape,
    val effectiveRadius: Float
) : DynamicLightSource(light, shape) {
    val originX = x
    val originY = y
}