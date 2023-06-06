package com.teoretik.components

import com.badlogic.gdx.maps.MapGroupLayer
import com.badlogic.gdx.maps.tiled.TiledMap
import com.teoretik.components.loaders.GameLevelLoader

enum class TerrainType {
    STONE_FLOOR, WATER, WALL
}

class GameCellInfo() {
    var terrain : TerrainType? = null
    var objects : List<GameObject> = listOf()
}

class GameLevel(
    val map: TiledMap
) {
    val objects: MutableList<GameObject> = mutableListOf()
    val floors: MutableMap<Int, MapGroupLayer> = mutableMapOf()

    init {
        GameLevelLoader.loadLevel(map, this)
    }


}


