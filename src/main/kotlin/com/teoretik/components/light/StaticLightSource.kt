package com.teoretik.components.light

class StaticLightSource(
    x : Float,
    y : Float,
    light: Light,
    shape: LightSourceShape,
    algorithm: IntensityAlgorithm = InvDistance(0.1f, 15f)
) : LightSource(x, y, light, shape, algorithm) {

}