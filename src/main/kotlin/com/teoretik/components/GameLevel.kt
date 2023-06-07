package com.teoretik.components

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.maps.MapGroupLayer
import com.badlogic.gdx.maps.tiled.TiledMap
import com.teoretik.components.loaders.GameLevelLoader
import com.teoretik.graphics.utils.toDynamic

enum class TerrainType {
    STONE_FLOOR, WATER
}

class GameCell() {
    var terrain : TerrainType? = null
    var objects : List<GameObject> = listOf()

}


class GameLevel(
    val map: TiledMap
) {
    val objects: MutableList<GameObject> = mutableListOf()
    val floors: MutableMap<Int, MapGroupLayer> = mutableMapOf()

    // Light
    var backgroundLight = Color.WHITE.cpy().toDynamic(0.1f)

    init {
        GameLevelLoader.loadLevel(map, this)
    }


}


