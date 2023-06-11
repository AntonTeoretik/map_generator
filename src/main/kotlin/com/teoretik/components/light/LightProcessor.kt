package com.teoretik.components.light

import com.badlogic.gdx.maps.objects.TextureMapObject
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.teoretik.components.Floor
import com.teoretik.graphics.render.GraphicsSettings
import com.teoretik.graphics.render.GraphicsSettings.Companion.unitScale
import kotlin.math.floor

class LightProcessor(
    floor: Floor
) {
    val staticLights: MutableList<StaticLightSource> = selectStaticLights(floor)
    val dynamicLights: MutableList<DynamicLightSource> = mutableListOf()

    val staticObstacles: MutableList<Obstacle> = floor.obstacles

    val cellToWorldTransformer: (Int, Int) -> Vector2 = { i: Int, j: Int ->
        floor.cellToWorldCoordinates(
            i.toFloat() / GraphicsSettings.resolution,
            j.toFloat() / GraphicsSettings.resolution
        )
    }
    val worldToCellTransformer: (Vector2) -> Pair<Int, Int> = {
        v2 : Vector2 ->
        floor.worldToCellCoordinates(v2.cpy().scl(GraphicsSettings.resolution.toFloat()))
    }

    val staticLightMaps : MutableMap<StaticLightSource, LightMapRegion> = mutableMapOf()

    val lightMap = List(GraphicsSettings.resolution * floor.width + 1) {
        List(GraphicsSettings.resolution * floor.height + 1) {
            Light()
        }
    }

    val region = Rectangle(0f, 0f, floor.width / unitScale, floor.height / unitScale)

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
            region.x.toInt(),
            region.y.toInt(),
            region.width.toInt() + 2,
            region.height.toInt() + 2
            )

        // TODO continue that
    }

    private fun selectStaticLights(floor: Floor): MutableList<StaticLightSource> {
        return floor.objects
            .filterIsInstance<TextureMapObject>()
            .filter { it.properties["lightProperties"] != null }
            .map {
                StaticLightSource(
                    it.x + 0.5f,
                    it.y + 0.5f,
                    Light(1.0f, 1.0f, 1.0f),
                    PointLightSourceShape(10f) // CHANGE ALL OF THIS!!!
                )
            }.toMutableList()
    }


    companion object {
        fun processLight(floor: Floor) {
            floor.lightMap.forEach { it.forEach(Light::clear) }

            floor.objects
                .filterIsInstance<TextureMapObject>()
                .filter { it.properties["lightProperties"] != null }
                .forEach {
                    val x = it.x * unitScale + 0.5f
                    val y = it.y * unitScale + 0.5f
                    processOneLightSource(Vector2(x, y), floor)
                }
        }

        private fun processOneLightSource(
            v1: Vector2,
            floor: Floor,
        ) {

            for (i in 0 until floor.lightMap.count()) {
                for (j in 0 until floor.lightMap[i].count()) {
                    val v2 = floor.cellToWorldCoordinates(
                        i.toFloat() / GraphicsSettings.resolution,
                        j.toFloat() / GraphicsSettings.resolution
                    )

                    floor.lightMap[i][j].add(processLightRay(v1, v2, floor.obstacles))
                }
            }
        }

        private fun processLightRay(v1: Vector2, v2: Vector2, obstacles: MutableList<Obstacle>): Light {
            obstacles.forEach {
                val intersects = Intersector.intersectSegmentPolygon(v1, v2, it.polygon)
                if (intersects && !it.polygon.contains(v2)) return Light()
            }

            val factor = (1f / (1f + v2.dst2(v1) * 0.2f))
            //val factor = 1.0f
            return Light(1f, 0.8f, 0.4f).scl(factor) as Light
        }

        class LightMapRegion(
            val x : Int,
            val y : Int,
            width : Int,
            height : Int,
        ) {
            enum class State { SHADOW, LIGHT, UNCERTAIN }
            val lightMap = List(width) { List(height) {State.UNCERTAIN} }
        }
    }
}