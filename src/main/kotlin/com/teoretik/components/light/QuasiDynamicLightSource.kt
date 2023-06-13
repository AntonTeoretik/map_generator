package com.teoretik.components.light

class QuasiDynamicLightSource(
    x: Float,
    y: Float,
    light: Light,
    shape: LightSourceShape,
    val effectiveRadius: Float
) : DynamicLightSource(x, y, light, shape) {
    val originX = x
    val originY = y
}