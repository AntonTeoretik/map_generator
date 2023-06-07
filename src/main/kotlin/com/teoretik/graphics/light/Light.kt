package com.teoretik.graphics.light

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector3
import kotlin.math.max

class Light : Vector3() {
    fun toColorMask() : Color = Color(x, y, z, 0.0f)
}