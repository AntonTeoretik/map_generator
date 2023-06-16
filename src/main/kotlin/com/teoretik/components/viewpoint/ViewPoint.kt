package com.teoretik.components.viewpoint

import com.teoretik.geometry.shapes.Shape

enum class Visibility {
    VISIBLE, NOT_VISIBLE
}

data class ViewPoint(var x: Float, var y: Float, val shape: Shape)

