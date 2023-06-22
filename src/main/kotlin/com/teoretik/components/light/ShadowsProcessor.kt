package com.teoretik.components.light

import IntPair
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.maps.MapProperties
import com.badlogic.gdx.maps.objects.TextureMapObject
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.teoretik.components.Floor
import com.teoretik.geometry.shapes.Ball
import com.teoretik.components.light.source.DynamicLightSource
import com.teoretik.components.light.source.LightSource
import com.teoretik.components.light.source.StaticLightSource
import com.teoretik.components.obstacles.Obstacle
import com.teoretik.graphics.render.GraphicsSettings.lightResolution
import com.teoretik.geometry.integral.Array2D
import com.teoretik.geometry.rays.HitResult
import com.teoretik.geometry.toPolygon

class ShadowsProcessor(
    floor: Floor
) {
    val dynamicLights: MutableList<DynamicLightSource> = mutableListOf()
    val dynamicObstacles: MutableList<Obstacle> by lazy { floor.obstacleProcessor.dynamicObstacles }

    private val staticLights: MutableList<StaticLightSource> by lazy { selectStaticLights(floor) }
    private val staticObstacles: List<Obstacle> by lazy { floor.obstacleProcessor.staticObstacles }

    private val staticLightMaps: MutableMap<StaticLightSource, LightMapRegion> = mutableMapOf()

    private val region = Rectangle(0f, 0f, floor.width.toFloat(), floor.height.toFloat())

    val lightMap = Array2D(
        floor.width * lightResolution + 1,
        floor.height * lightResolution + 1
    ) { _, _ -> LightColor() }

    // Methods

    fun processStaticLights() {
        staticLights.forEach { processStaticLight(it) }
    }

    private fun processStaticLight(lightSource: StaticLightSource) {
        val lightRegion = getLightRectangle(lightSource) ?: return

        val lightMapRegion = LightMapRegion(
            (lightResolution * lightRegion.x).toInt(),
            (lightResolution * lightRegion.y).toInt(),
            lightResolution * lightRegion.width.toInt() + 2,
            lightResolution * lightRegion.height.toInt() + 2
        )

        val relevantStaticObstacles = staticObstacles
            .filter { Intersector.intersectPolygons(it.polygon, lightRegion.toPolygon(), null) }
            .map { it.polygon }

        val coordList = mutableListOf<Pair<Int, Int>>()
        for (i in lightMapRegion.x until lightMapRegion.x + lightMapRegion.width) {
            for (j in lightMapRegion.y until lightMapRegion.y + lightMapRegion.height) {
                val res = lightSource.shape.processor.processRay(
                    Vector2(lightSource.x, lightSource.y),
                    lightMapToWorldCoordinates(i, j),
                    relevantStaticObstacles.asSequence()
                )
                if (res == HitResult.HIT)
                    coordList.add(i to j)

            }
        }
        lightMapRegion.lightMap = coordList
        staticLightMaps[lightSource] = lightMapRegion
    }

    private fun processDynamicLights() {
        dynamicLights.forEach { processDynamicLight(it) }
    }

    private fun processDynamicLight(lightSource: DynamicLightSource) {
        val lightRegion = getLightRectangle(lightSource) ?: return

        val x = (lightResolution * lightRegion.x).toInt()
        val y = (lightResolution * lightRegion.y).toInt()
        val width = lightResolution * lightRegion.width.toInt() + 2
        val height = lightResolution * lightRegion.height.toInt() + 2

        val relevantObstacles = (staticObstacles + dynamicObstacles)
            .filter {  Intersector.intersectPolygons(it.polygon, lightRegion.toPolygon(), null) }
            .map { it.polygon }

        for (i in x until x + width) {
            for (j in y until y + height) {
                val res = lightSource.shape.processor.processRay(
                    Vector2(lightSource.x, lightSource.y),
                    lightMapToWorldCoordinates(i, j),
                    relevantObstacles.asSequence()
                )

                if (res == HitResult.HIT) {
                    addLight(lightSource, i, j)
                    //println(lightSource.computeLightInPoint(lightMapToWorldCoordinates(i, j)))
                    //println(lightSource.algorithm.getFadingFactor(lightSource.x, lightSource.y, lightMapToWorldCoordinates(i, j)))
                }
            }
        }
    }

    private fun addLight(lightSource: LightSource, i: Int, j: Int) {
        lightMap[i, j]?.add(
            lightSource.computeLightInPoint(lightMapToWorldCoordinates(i, j))
        )
    }


    private fun getLightRectangle(lightSource: LightSource): Rectangle? {
        val lightPreRegion = lightSource.shape.hitBox()?.apply {
            x += lightSource.x
            y += lightSource.y
        } ?: region

        val lightRegion = Rectangle()
        if (!Intersector.intersectRectangles(lightPreRegion, region, lightRegion)) return null
        return lightRegion
    }

    private fun selectStaticLights(floor: Floor): MutableList<StaticLightSource> {
        return floor.objects
            .filterIsInstance<TextureMapObject>()
            .filter {
                it.properties["lightProperties"] != null
            }
            .map {
                val color = (it.properties["lightProperties"] as MapProperties)["color"] as? Color ?: Color.WHITE
                StaticLightSource(
                    it.x + 0.5f,
                    it.y + 0.5f,
                    LightColor(color.r, color.g, color.b),
                    Ball(20f) // CHANGE ALL OF THIS!!!
                )
            }.toMutableList()
    }

    private fun processDynamicObstacles(light: StaticLightSource, i: Int, j: Int): Boolean {
        val res = light.shape.processor.processRay(
            Vector2(light.x, light.y),
            lightMapToWorldCoordinates(i, j),
            dynamicObstacles.asSequence().map { it.polygon })
        return res == HitResult.HIT
    }

    private fun clearLightmap() {
        lightMap.validIndices().forEach { (_, _, l) -> l.clear() }
    }

    fun computeFinalLightMap() {
        clearLightmap()

        staticLightMaps.forEach { (light, lm) ->
            lm.lightMap.forEach {
                val (i, j) = it

                if (processDynamicObstacles(light, i, j))
                    addLight(light, i, j)
            }
        }

        processDynamicLights()
    }

    fun updateLight() {
        //val t1 = Clock.systemUTC().millis()
        computeFinalLightMap()
        //println(Clock.systemUTC().millis() - t1)
    }

    companion object {
        class LightMapRegion(
            val x: Int,
            val y: Int,
            val width: Int,
            val height: Int,
        ) {
            var lightMap = listOf<Pair<Int, Int>>()
        }

        fun lightMapToWorldCoordinates(x: Int, y: Int): Vector2 {
            return Vector2(
                x.toFloat() / lightResolution,
                y.toFloat() / lightResolution
            )
        }

        fun worldCoordinatesToIndices(x: Float, y: Float): IntPair {
            return IntPair((x * lightResolution).toInt(), (y * lightResolution).toInt())
        }
    }
}