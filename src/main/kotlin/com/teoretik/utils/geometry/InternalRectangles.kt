package com.teoretik.utils.geometry

class InternalRectangles(val table: Array2D<Boolean>) :
    POS<IntegralRect>() {

    override val minimums = sequence {
        (0 until table.numRows).flatMap { i ->
            (0 until table.numColumns).map { j ->
                if(table[i, j] == true) yield(IntegralRect(i, j, i, j))
            }
        }
    }.toSet()

    override fun less(elem1: IntegralRect, elem2: IntegralRect): Boolean {
        return elem1.x0 >= elem2.x0 &&
                elem1.x1 <= elem2.x1 &&
                elem1.y0 >= elem2.y0 &&
                elem1.y1 <= elem2.y1
    }

    override fun parents(elem: IntegralRect): Sequence<IntegralRect> =
        sequence {
            with(elem) {
                if (isInSet((y0..y1).asSequence().map { x0 - 1 to it }))
                    yield(this.copy(x0 = x0 - 1))
                if (isInSet((y0..y1).asSequence().map { x1 + 1 to it }))
                    yield(this.copy(x1 = x1 + 1))
                if (isInSet((x0..x1).asSequence().map { it to y0 - 1 }))
                    yield(this.copy(y0 = y0 - 1))
                if (isInSet((x0..x1).asSequence().map { it to y1 + 1 }))
                    yield(this.copy(y1 = y1 + 1))
            }
        }

    override fun isInSet(elem: IntegralRect): Boolean {
        val validXRange = 0 until table.numColumns
        val validYRange = 0 until table.numRows

        return with(elem) {
            x0 in validXRange && x1 in validXRange && y0 in validYRange && y1 in validYRange &&
                    (x0..x1).all { i -> (y0..y1).all { j -> table[i, j] == true } }
        }
    }

    private fun isInSet(sequence: Sequence<Pair<Int, Int>>): Boolean =
        sequence.all { table[it.first, it.second] == true }
}