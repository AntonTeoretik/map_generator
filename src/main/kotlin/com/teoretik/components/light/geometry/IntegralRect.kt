package com.teoretik.components.light.geometry

data class IntegralRect(var x0: Int, var y0: Int, var x1: Int, var y1: Int) {
    fun getWidth() = x1 - x0
    fun getHeight() = y1 - y0
}

