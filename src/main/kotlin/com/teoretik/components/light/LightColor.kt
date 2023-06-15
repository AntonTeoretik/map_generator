package com.teoretik.components.light

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector3

typealias LightColor = Vector3

fun LightColor.clear() {
    x = 0f
    y = 0f
    z = 0f
}

fun LightColor.toColorMask(): Color = Color(x, y, z, 0.0f)


