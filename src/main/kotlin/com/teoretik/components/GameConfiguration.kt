package com.teoretik.components

import GameSetup
import com.badlogic.gdx.maps.tiled.TiledMap
import com.teoretik.components.loaders.MapLoader
import com.teoretik.graphics.camera.Camera
import com.teoretik.graphics.render.GameRenderer
import com.teoretik.graphics.render.MapRenderer
import com.teoretik.graphics.render.ViewSquare
import com.teoretik.graphics.render.WorldRenderer

/** Full game configuration, including
 *  - maps, levels and so on
 *  - camera properties
 *  - main character and npc properties
 */
class GameConfiguration {
    val levels : MutableList<GameLevel> = mutableListOf()
    var activeLevel : GameLevel? = null

    val camera = Camera()


    fun load() {
        val loader = MapLoader()
        val map = loader.load("src/main/resources/map.tmx")
        val level = GameLevel(map)

        levels += level
        activeLevel = level
    }
}