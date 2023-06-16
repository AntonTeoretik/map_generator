package com.teoretik.components.light.source

import com.teoretik.components.light.intensity.IntensityAlgorithm
import com.teoretik.components.light.intensity.InvDistance
import com.teoretik.components.light.LightColor
import com.teoretik.geometry.shapes.Shape

class StaticLightSource(
    x : Float,
    y : Float,
    lightColor: LightColor,
    shape: Shape,
    algorithm: IntensityAlgorithm = InvDistance(0.1f, 15f)
) : LightSource(x, y, lightColor, shape, algorithm) {

}