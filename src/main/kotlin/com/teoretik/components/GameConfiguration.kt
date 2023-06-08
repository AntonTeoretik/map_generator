package com.teoretik.components

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.teoretik.components.loaders.MapLoader
import com.teoretik.graphics.camera.Camera

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

        setUpCamera()
    }

    fun setUpCamera() {
        val vec2 =  activeLevel?.cellToWorldCoordinates(GlobalPosition(0, 0 , 1))
        println(vec2)
        println(activeLevel?.worldToCellCoordinates(Vector2(0.5f, 0.5f), 1))
        camera.position.set(Vector3(vec2, 0f))
    }
}