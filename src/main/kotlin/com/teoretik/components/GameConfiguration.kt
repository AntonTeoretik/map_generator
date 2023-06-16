package com.teoretik.components

import com.badlogic.gdx.maps.objects.TextureMapObject
import com.badlogic.gdx.math.Vector3
import com.teoretik.components.loaders.MapLoader
import com.teoretik.components.viewpoint.ViewPoint
import com.teoretik.geometry.shapes.Ball
import com.teoretik.graphics.camera.Camera
import com.teoretik.utils.vectors.*

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

    val viewPoint = ViewPoint(0f, 0f, Ball(10f))

    fun load() {
        val loader = MapLoader()
        val map = loader.load("src/main/resources/map.tmx")
        val level = Level(map)

        levels += level
        activeLevel = level

        setUpCamera()

        dynamicLight.properties.put("lightProperties", "")

        activeLevel!!.floors[1]!!.fullUpdateLight()
        activeLevel!!.floors[1]!!.viewPoints.add(viewPoint)
        activeLevel!!.floors[2]!!.fullUpdateLight()

        activeLevel!!.activeFloor = 1
    }

    private fun setUpCamera() {
        val vec2 = activeLevel?.cellToWorldCoordinates(GlobalPosition(0, 15 , 1))
        camera.position.set(Vector3(vec2, 0f))
    }

    fun updateViewPoint() {
        val (x, y) = camera.getPosition2D()
        viewPoint.x = x
        viewPoint.y = y
    }
}