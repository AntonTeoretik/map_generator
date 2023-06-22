package com.teoretik.game_objects

interface Context
interface Reaction

interface Movable {
    fun setNewCoordinates(newCoordinates : Pair<Int, Int>)
}

interface Big {

}

sealed class Action {
    object NotAnAction
    //...
}

interface Intractable {
    fun processAction(action : Action, context : Context) : Reaction
}


abstract class GameObject {
    val mapCoordinates : Pair<Int, Int> = 0 to 0
}