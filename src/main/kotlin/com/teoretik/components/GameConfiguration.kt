package com.teoretik.components

import com.badlogic.gdx.math.Polygon
import com.badlogic.gdx.math.Vector3
import com.teoretik.components.light.LightColor
import com.teoretik.components.light.intensity.InvDistance
import com.teoretik.components.light.source.DynamicLightSource
import com.teoretik.components.loaders.MapLoader
import com.teoretik.components.map.GlobalPosition
import com.teoretik.components.map.Level
import com.teoretik.components.obstacles.Obstacle
import com.teoretik.components.viewpoint.ViewPoint
import com.teoretik.geometry.shapes.Ball
import com.teoretik.graphics.camera.Camera
import com.teoretik.utils.dynamic.Oscillator
import com.teoretik.utils.vectors.*
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/** Full game configuration, including
 *  - maps, levels and so on
 *  - camera properties
 *  - main character and npc properties
 */
class GameConfiguration {
    val levels : MutableList<Level> = mutableListOf()
    var activeLevel : Level? = null

    val camera = Camera()

    val oscillator = Oscillator(1/16f)

    val dynamicLight1 = DynamicLightSource(0f, 0f,
        LightColor(1f, 1f, .7f),
        Ball(5f),
        InvDistance(1f, 5f)
    )
    val dynamicLight2 = DynamicLightSource(0f, 0f,
        LightColor(1f, 1f, .7f),
        Ball(5f),
        InvDistance(1f, 5f)
    )
    val dynamicLight3 = DynamicLightSource(0f, 0f,
        LightColor(1f, 1f, .7f),
        Ball(5f),
        InvDistance(1f, 5f)
    )

    private val dynamicObstacle = Obstacle(Polygon(floatArrayOf(
        -0.2f, -0.2f,
        -0.2f,  0.2f,
         0.2f,  0.2f,
         0.2f, -0.2f,
        )).also {it.setOrigin(0f, 0f)})

    val viewPoint = ViewPoint(0f, 0f, Ball(10f))

    fun load() {
        val loader = MapLoader()
        val map = loader.load("src/main/resources/map.tmx")
        val level = Level(map)

        levels += level
        activeLevel = level

        setUpCamera()


        activeLevel!!.floors[1]!!.lightProcessor.computeFinalLightMap()

        activeLevel!!.floors[1]!!.lightProcessor.dynamicLights.add(dynamicLight1)
        activeLevel!!.floors[1]!!.lightProcessor.dynamicLights.add(dynamicLight2)
        activeLevel!!.floors[1]!!.lightProcessor.dynamicLights.add(dynamicLight3)


        activeLevel!!.floors[1]!!.viewPoints.add(viewPoint)
        activeLevel!!.floors[1]!!.obstacleProcessor.dynamicObstacles.add(dynamicObstacle)


        //activeLevel!!.floors[2]!!.fullUpdateLight()

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

        val phi = oscillator.phase
        val r = 0.5f

        dynamicLight1.x = x + sin(phi) * r
        dynamicLight1.y = y + cos(phi) * r

        dynamicLight2.x = x + sin(phi + PI.toFloat() * 0.66f) * r
        dynamicLight2.y = y + cos(phi + PI.toFloat() * 0.66f) * r

        dynamicLight3.x = x + sin(phi - PI.toFloat() * 0.66f) * r
        dynamicLight3.y = y + cos(phi - PI.toFloat() * 0.66f) * r

        dynamicObstacle.polygon.setPosition(x, y)
    }
}