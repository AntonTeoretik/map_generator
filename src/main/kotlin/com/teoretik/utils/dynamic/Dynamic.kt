package com.teoretik.utils.dynamic


abstract class Dynamic {
    abstract fun update(delta: Float)
    init {
        DynamicProcessor.dynamicObjects.add(this)
    }
}

