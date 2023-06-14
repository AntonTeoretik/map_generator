package com.teoretik.components

import com.badlogic.gdx.maps.objects.TextureMapObject
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
    val levels : MutableList<Level> = mutableListOf()
    var activeLevel : Level? = null

    val camera = Camera()

    val dynamicLight = TextureMapObject()

    fun load() {
        val loader = MapLoader()
        val map = loader.load("src/main/resources/map.tmx")
        val level = Level(map)

        levels += level
        activeLevel = level

        setUpCamera()

        dynamicLight.properties.put("lightProperties", "")

        //  activeLevel!!.floors[1]!!.objects.add(dynamicLight)

        activeLevel!!.floors[1]!!.updateObstacles()
        activeLevel!!.floors[1]!!.fullUpdateLight()
    }

    fun setUpCamera() {
        val vec2 = activeLevel?.cellToWorldCoordinates(GlobalPosition(0, 15 , 1))
        camera.position.set(Vector3(vec2, 0f))
    }
}