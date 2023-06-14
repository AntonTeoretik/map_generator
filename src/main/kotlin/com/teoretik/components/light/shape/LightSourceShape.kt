package com.teoretik.components.light.shape

import com.badlogic.gdx.math.Rectangle
import com.teoretik.components.light.processors.LightProcessor

abstract class LightSourceShape {
    abstract fun provideRegion(): Rectangle?
    abstract val processor : LightProcessor
}

