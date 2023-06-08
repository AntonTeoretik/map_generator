package com.teoretik.components

import com.badlogic.gdx.maps.MapGroupLayer
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.math.Vector2
import com.teoretik.components.loaders.GameLevelLoader

class GameLevel(
    val map: TiledMap
) {
    val objects: MutableList<GameObject> = mutableListOf()
    val floors: MutableMap<Int, MapGroupLayer> = mutableMapOf()

    val activeFloor : Int? = null

    init {
        GameLevelLoader.loadLevel(map, this)
    }

    fun activeFloorHeight() : Int? {
        return activeFloor?.run { floorHeight(this) }
    }

    fun floorHeight(floorNum : Int) : Int? {
        val floor = floors[floorNum] ?: return null
        for (layer in floor.layers)
            if (layer is TiledMapTileLayer)
                return layer.height
        return null
    }

    fun cellToWorldCoordinates(pos : GlobalPosition) : Vector2? {
        val height = floorHeight(pos.floor) ?: return null
        return Vector2(pos.x + 0.5f, -pos.y + height - 0.5f)
    }

    fun worldToCellCoordinates(vec : Vector2) {

    }

}


