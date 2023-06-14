package com.teoretik.components.light.source

import com.teoretik.components.light.Light
import com.teoretik.components.light.shape.LightSourceShape

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