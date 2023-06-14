package com.teoretik.geometry


/**
 * Represents a partially ordered set
 */
abstract class POS<T> {
    abstract fun parents(elem: T): Sequence<T>
    abstract fun isInSet(elem: T): Boolean
    abstract fun less(elem1: T, elem2 : T) : Boolean
    abstract val minimals: Set<T>

    val maximals: Set<T> by lazy {
        run {
            val maxis = mutableSetOf<T>()
            minimals.forEach {
                if (maxis.none {maxT -> less(it, maxT)}) {
                    produceMaximalsForElem(it, maxis)
                }
            }
            maxis
        }
    }

    private fun produceMaximalsForElem(elem: T, maxis : MutableSet<T>) {
        var isMax = true

        parents(elem).forEach {
            isMax = false
            produceMaximalsForElem(it, maxis)
        }
        if (isMax) maxis.add(elem)
    }
}