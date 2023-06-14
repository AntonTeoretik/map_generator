package com.teoretik.components

import com.badlogic.gdx.maps.MapGroupLayer
import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.math.Vector2
import com.teoretik.components.light.processors.FloorLightProcessor
import com.teoretik.components.obstacles.Obstacle
import com.teoretik.components.obstacles.ObstacleProcessor
import com.teoretik.components.loaders.FLOOR_NUMBER

class Floor : MapGroupLayer() {
    val width: Int by lazy { tileLayers().first().width }
    val height: Int by lazy { tileLayers().first().height }

    val floorNumber: Int by lazy { properties[FLOOR_NUMBER] as Int }

    val obstacleProcessor by lazy { ObstacleProcessor(this) }
    val lightProcessor by lazy { FloorLightProcessor(this) }

    val lightMap by lazy { lightProcessor.lightColorMap }

    fun fullUpdateLight() {
        lightProcessor.processStaticLights()
        lightProcessor.computeFinalLightMap()
    }

    fun tileLayers() : Sequence<TiledMapTileLayer> = layers.asSequence().filterIsInstance<TiledMapTileLayer>()
}

fun TiledMapTileLayer.tilesWithIndexes(predicate: (TiledMapTile) -> Boolean = {true}) : Sequence<Triple<Int, Int, TiledMapTile>> = sequence {
    (0 until width).forEach { i ->
        (0 until height).forEach { j ->
            getCell(i, j)?.tile?.run { if (predicate(this)) yield(Triple(i, j, this)) }
        }
    }
}