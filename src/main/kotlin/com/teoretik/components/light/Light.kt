package com.teoretik.components.light

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector3

class Light(x: Float = 0f, y: Float = 0f, z: Float = 0f) : Vector3(x, y, z) {
    fun toColorMask(): Color = Color(x, y, z, 0.0f)
    fun clear() {
        x = 0f
        y = 0f
        z = 0f
    }
}

