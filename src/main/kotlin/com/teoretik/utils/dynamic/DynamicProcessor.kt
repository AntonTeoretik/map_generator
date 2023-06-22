package com.teoretik.utils.dynamic

internal object DynamicProcessor {
    val dynamicObjects: MutableList<Dynamic> = mutableListOf()

    fun add(obj: DynamicValue<*>) {
        dynamicObjects += obj
    }

    fun update(delta: Float) {
        dynamicObjects.forEach { it.update(delta) }
    }
}