package com.teoretik.geometry.shapes

import com.badlogic.gdx.math.Rectangle
import com.teoretik.geometry.rays.processors.RayProcessor

abstract class Shape {
    abstract fun provideRegion(): Rectangle?
    abstract val processor : RayProcessor
}

