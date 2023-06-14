package com.teoretik.utils.dynamic

internal object DynamicProcessor {
    val dynamicObjects: MutableList<Dynamic<*>> = mutableListOf()

    fun add(obj: Dynamic<*>) {
        dynamicObjects += obj
    }

    fun update(delta: Float) {
        dynamicObjects.forEach { it.update(delta) }
    }
}