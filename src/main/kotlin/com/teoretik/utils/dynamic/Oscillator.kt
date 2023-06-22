package com.teoretik.utils.dynamic

import kotlin.math.PI

class Oscillator(val frequency: Float, start : Float = 0f) : Dynamic() {
    var phase = start
        private set

    private var twopi = PI.toFloat() * 2

    override fun update(delta: Float) {
        phase += delta * frequency * twopi
        if (phase > twopi) {
            phase -= twopi
        }
    }
}