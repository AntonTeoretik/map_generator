package com.teoretik.components.light.source

import com.teoretik.components.light.LightColor
import com.teoretik.components.light.shape.LightSourceShape

class QuasiDynamicLightSource(
    x: Float,
    y: Float,
    lightColor: LightColor,
    shape: LightSourceShape,
    val effectiveRadius: Float
) : DynamicLightSource(x, y, lightColor, shape) {
    val originX = x
    val originY = y
}