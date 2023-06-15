package com.teoretik.components

import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.Vector2
import com.teoretik.components.loaders.LevelLoader

class Level(
    val map: TiledMap
) {
    val floors: MutableMap<Int, Floor> = mutableMapOf()

    init {
        LevelLoader.loadLevel(map, this)
    }

    fun floorHeight(floorNum : Int) : Int? {
        return floors[floorNum]?.height
    }

    fun cellToWorldCoordinates(pos : GlobalPosition) : Vector2? {
        val height = floorHeight(pos.floor) ?: return null
        return Vector2(pos.x + 0.5f, -pos.y + height - 0.5f)
    }

    fun worldToCellCoordinates(vec : Vector2, floorNum: Int) : GlobalPosition? {
        val height = floorHeight(floorNum) ?: return null
        return GlobalPosition(vec.x.toInt(), -vec.y.toInt() + height - 1 , floorNum)
    }
}


