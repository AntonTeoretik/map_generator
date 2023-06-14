package com.teoretik.components.light.source

import com.teoretik.components.light.intensity.IntensityAlgorithm
import com.teoretik.components.light.intensity.InvDistance
import com.teoretik.components.light.Light
import com.teoretik.components.light.shape.LightSourceShape

class StaticLightSource(
    x : Float,
    y : Float,
    light: Light,
    shape: LightSourceShape,
    algorithm: IntensityAlgorithm = InvDistance(0.1f, 15f)
) : LightSource(x, y, light, shape, algorithm) {

}