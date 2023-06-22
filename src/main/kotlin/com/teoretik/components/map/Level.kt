package com.teoretik.components.map

import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.Vector2
import com.teoretik.components.loaders.LevelLoader
import com.teoretik.graphics.render.LevelRenderer


class Level(
    map: TiledMap
) {
    val floors: Map<Int, Floor> = LevelLoader.loadLevel(map)
    val renderer = LevelRenderer(this)

    var activeFloor : Int? = null

    fun cellToWorldCoordinates(pos: GlobalPosition): Vector2? {
        val height = floors[pos.floor]?.height ?: return null
        return Vector2(pos.x + 0.5f, -pos.y + height - 0.5f)
    }
}


