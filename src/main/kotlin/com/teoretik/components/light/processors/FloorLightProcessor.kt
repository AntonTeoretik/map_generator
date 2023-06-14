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
import com.teoretik.graphics.render.GraphicsSettings

class FloorLightProcessor(
    floor: Floor
) {
    val staticLights: MutableList<StaticLightSource> = selectStaticLights(floor)

    val dynamicLights: MutableList<DynamicLightSource> = mutableListOf()

    val staticObstacles: MutableList<Obstacle> = floor.obstacles

    val staticLightMaps: MutableMap<StaticLightSource, LightMapRegion> = mutableMapOf()

    val region = Rectangle(0f, 0f, floor.width.toFloat(), floor.height.toFloat())

    val lightColorMap =
        Array(floor.width * GraphicsSettings.lightResolution + 1) {
            Array(floor.height * GraphicsSettings.lightResolution + 1) {
                LightColor()
            }
        }

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
            (GraphicsSettings.lightResolution * lightRegion.x).toInt(),
            (GraphicsSettings.lightResolution * lightRegion.y).toInt(),
            GraphicsSettings.lightResolution * lightRegion.width.toInt() + 2,
            GraphicsSettings.lightResolution * lightRegion.height.toInt() + 2
        )

        for (i in 0 until lightMapRegion.width) {
            for (j in 0 until lightMapRegion.height) {
                lightMapRegion.lightMap[i][j] = lightSource.shape.processor.processRay(
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
            lm.lightMap.filterIndexed { i, _ ->
                i + lm.x < lightColorMap.size
            }.forEachIndexed { i, arr ->
                arr.filterIndexed { j, _ ->
                    j + lm.y < lightColorMap[i].size
                }.forEachIndexed { j, shadowState ->
                    if (shadowState == ShadowState.LIGHT) {
                        lightColorMap[i + lm.x][j + lm.y].add(
                            light.computeLightInPoint(lightMapToWorldCoordinates(i + lm.x, j + lm.y))
                        )

                    }
                }
            }
        }
    }

    private fun clearLightmap() {
        lightColorMap.forEach { it.forEach { l -> l.clear() } }
    }

    companion object {
        class LightMapRegion(
            val x: Int,
            val y: Int,
            val width: Int,
            val height: Int,
        ) {
            val lightMap = Array(width) { Array(height) { ShadowState.UNCERTAIN } }
        }

        fun lightMapToWorldCoordinates(x: Int, y: Int): Vector2 {
            return Vector2(
                x.toFloat() / GraphicsSettings.lightResolution,
                y.toFloat() / GraphicsSettings.lightResolution
            )
        }
    }
}