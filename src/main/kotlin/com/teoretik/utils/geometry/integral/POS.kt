package com.teoretik.utils.geometry.integral


/**
 * Represents a partially ordered set
 */
abstract class POS<T> {
    abstract fun parents(elem: T): Sequence<T>
    abstract fun isInSet(elem: T): Boolean
    abstract fun less(elem1: T, elem2 : T) : Boolean
    abstract val minimums: Set<T>

    val maximums: Set<T> by lazy {
        run {
            val maxis = mutableSetOf<T>()
            minimums.forEach {
                if (maxis.none {maxT -> less(it, maxT)}) {
                    produceMaximalsForElem(it, maxis)
                }
            }
            maxis
        }
    }

    private fun produceMaximalsForElem(elem: T, maxis : MutableSet<T>) {
        try {
            produceMaximalsForElem(parents(elem).first(), maxis)
        } catch (e : Exception) {
            maxis.add(elem)
        }
    }
}