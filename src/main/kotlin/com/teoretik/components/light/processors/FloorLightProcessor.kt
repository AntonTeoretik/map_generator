package com.teoretik.components.light.processors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.maps.MapProperties
import com.badlogic.gdx.maps.objects.TextureMapObject
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.teoretik.components.Floor
import com.teoretik.components.light.LightColor
import com.teoretik.components.light.ShadowState
import com.teoretik.components.light.clear
import com.teoretik.components.light.shape.PointLightSourceShape
import com.teoretik.components.light.source.DynamicLightSource
import com.teoretik.components.light.source.StaticLightSource
import com.teoretik.components.obstacles.Obstacle
import com.teoretik.graphics.render.GraphicsSettings.lightResolution
import com.teoretik.utils.geometry.Array2D

class FloorLightProcessor(
    floor: Floor
) {
    val dynamicLights: MutableList<DynamicLightSource> = mutableListOf()

    private val staticLights: MutableList<StaticLightSource> by lazy { selectStaticLights(floor) }
    private val staticObstacles: List<Obstacle> by lazy { floor.obstacleProcessor.staticObstacles }

    private val staticLightMaps: MutableMap<StaticLightSource, LightMapRegion> = mutableMapOf()

    private val region = Rectangle(0f, 0f, floor.width.toFloat(), floor.height.toFloat())

    val lightColorMap = Array2D(
        floor.width * lightResolution + 1,
        floor.height * lightResolution + 1
    ) { _, _ ->
        LightColor() }

    // Methods

    fun processStaticLights() {
        staticLights.forEach { processStaticLight(it) }
    }

    private fun processStaticLight(lightSource: StaticLightSource) {

        val lightPreRegion = lightSource.shape.provideRegion()?.apply {
            x += lightSource.x
            y += lightSource.y
        } ?: region

        val lightRegion = Rectangle()
        if (!Intersector.intersectRectangles(lightPreRegion, region, lightRegion)) return

        val lightMapRegion = LightMapRegion(
            (lightResolution * lightRegion.x).toInt(),
            (lightResolution * lightRegion.y).toInt(),
            lightResolution * lightRegion.width.toInt() + 2,
            lightResolution * lightRegion.height.toInt() + 2
        )

        for (i in 0 until lightMapRegion.width) {
            for (j in 0 until lightMapRegion.height) {
                lightMapRegion.lightMap[i, j] = lightSource.shape.processor.processRay(
                    Vector2(lightSource.x, lightSource.y),
                    lightMapToWorldCoordinates(i + lightMapRegion.x, j + lightMapRegion.y),
                    staticObstacles
                )
            }
        }

        staticLightMaps[lightSource] = lightMapRegion
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
                    PointLightSourceShape(20f) // CHANGE ALL OF THIS!!!
                )
            }.toMutableList()
    }

    fun computeFinalLightMap() {
        clearLightmap()

        staticLightMaps.forEach { (light, lm) ->
            lm.lightMap
                .validIndicesSeparateFilter({ i -> i + lm.x < lightColorMap.numRows }, { j -> j + lm.y < lightColorMap.numColumns })
                .forEach {
                    val (i, j, state) = it

                    if (state == ShadowState.LIGHT) {
                        lightColorMap[i + lm.x, j + lm.y]?.add(
                            light.computeLightInPoint(lightMapToWorldCoordinates(i + lm.x, j + lm.y))
                        )
                    }
                }
        }
    }

    private fun clearLightmap() {
        lightColorMap.validIndices().forEach { (_, _, l) -> l.clear() }
    }

    companion object {
        class LightMapRegion(
            val x: Int,
            val y: Int,
            val width: Int,
            val height: Int,
        ) {
            val lightMap = Array2D(width, height) { _, _ -> ShadowState.UNCERTAIN }
        }

        fun lightMapToWorldCoordinates(x: Int, y: Int): Vector2 {
            return Vector2(
                x.toFloat() / lightResolution,
                y.toFloat() / lightResolution
            )
        }
    }
}