package com.teoretik.components

import IntPair

data class GlobalPosition(
    var x : Int,
    var y : Int,
    var floor: Int
) {
    constructor(coord: IntPair, floor: Int) : this(coord.x, coord.y, floor)
    constructor() : this(IntPair(0, 0), 0)

    override fun toString(): String {
        return "($x, $y, $floor)"
    }
}