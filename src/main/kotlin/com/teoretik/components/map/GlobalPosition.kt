package com.teoretik.components.map

data class GlobalPosition(
    var x : Int,
    var y : Int,
    var floor: Int
) {
    override fun toString(): String {
        return "($x, $y, $floor)"
    }
}