package com.teoretik.components.loaders

import com.badlogic.gdx.maps.MapGroupLayer
import com.badlogic.gdx.maps.MapLayer
import com.badlogic.gdx.maps.MapObjects
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject
import com.teoretik.components.map.Floor
import com.teoretik.graphics.render.GraphicsSettings.unitScale

object LevelLoader {
    fun loadLevel(map: TiledMap): Map<Int, Floor> {
        val floors : MutableMap<Int, Floor> = mutableMapOf()

        loadFloors(map, floors)
        moveObjectsToFloorLayers(floors)
        adjustObjectCoordinates(floors)

        return floors.toMap()
    }

    private fun loadFloors(map: TiledMap, floors: MutableMap<Int, Floor>) {
        for (layer in map.layers) {
            if (layer is Floor) {
                try {
                    floors[layer.floorNumber] = layer
                } catch (e: Exception) {
                    println("The configuration is wrong: floor layer must contain property ")
                }
            }
        }
    }

    private fun moveObjectsToFloorLayers(floors: MutableMap<Int, Floor>) {
        floors.forEach { (_, layer) ->
            layer.layers.forEach {
                moveObjectsToTop(layer, it)
            }
        }
    }

    private fun moveObjectsToTop(topLayer: Floor, layer: MapLayer) {
        when (layer) {
            is MapGroupLayer -> {
                layer.layers.forEach {
                    moveObjectsToTop(topLayer, it)
                }
            }
        }

        layer.objects.forEach {
            topLayer.objects.add(it)
        }

        (0 until layer.objects.count).reversed().forEach {
            layer.objects.remove(it)
        }
    }

    private fun adjustObjectCoordinates(floors: MutableMap<Int, Floor>) {
        floors.forEach { (_, layer) ->
            adjustObjectCoordinates(layer.objects)
        }
    }

    private fun adjustObjectCoordinates(objects: MapObjects) {
        objects.forEach {
            if (it is TiledMapTileMapObject) {
                it.x = it.x * unitScale
                it.y = it.y * unitScale
            }
        }
    }
}

