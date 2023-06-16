package com.teoretik.geometry.integral

class Array2D<T>(val width: Int, val height: Int, init: (Int, Int) -> T) {
    private val data: List<MutableList<T>> = List(width) { i -> MutableList(height) { j -> init(i, j) } }

    operator fun get(row: Int, col: Int): T? = data.getOrNull(row)?.getOrNull(col)
    operator fun set(row: Int, col: Int, value: T) {
        data[row][col] = value
    }

    fun validIndices(predicate: (Int, Int) -> Boolean = { _, _ -> true }) = sequence<Triple<Int, Int, T>> {
        repeat(width) { i ->
            repeat(height) { j ->
                if (predicate(i, j)) yield(Triple(i, j, get(i, j)!!))
            }
        }
    }

    fun iterate(rangeX : IntRange = 0 until width, rangeY: IntRange = 0 until height) = sequence {
        for (i in rangeX) {
            for (j in rangeY) {
                yield(Triple(i, j, get(i, j)))
            }
        }
    }

}
