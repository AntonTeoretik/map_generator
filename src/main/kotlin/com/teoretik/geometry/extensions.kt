package com.teoretik.geometry

import com.badlogic.gdx.math.Polygon
import com.badlogic.gdx.math.Rectangle

fun Rectangle.toPolygon(): Polygon {
    return Polygon(floatArrayOf(x, y, x, y + height, x + width, y + height, x + width, y))
}
