package com.teoretik.utils.geometry

class Array2D<T>(val numRows: Int, val numColumns: Int, init: (Int, Int) -> T) {
    private val data: List<MutableList<T>> = List(numRows) { i -> MutableList(numColumns) { j -> init(i, j) } }

    operator fun get(row: Int, col: Int): T? = data.getOrNull(row)?.getOrNull(col)
    operator fun set(row: Int, col: Int, value: T) {
        data[row][col] = value
    }
}