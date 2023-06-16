package com.teoretik.geometry.shapes

import com.badlogic.gdx.math.Rectangle
import com.teoretik.components.light.processors.LightProcessor

abstract class Shape {
    abstract fun provideRegion(): Rectangle?
    abstract val processor : LightProcessor
}

