package com.teoretik.components

import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.Vector2
import com.teoretik.components.loaders.LevelLoader

class Level(
    map: TiledMap
) {
    val floors: Map<Int, Floor> = LevelLoader.loadLevel(map)

    fun floorHeight(floorNum: Int): Int? {
        return floors[floorNum]?.height
    }

    fun cellToWorldCoordinates(pos: GlobalPosition): Vector2? {
        val height = floorHeight(pos.floor) ?: return null
        return Vector2(pos.x + 0.5f, -pos.y + height - 0.5f)
    }
}


