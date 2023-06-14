package com.teoretik.components.loaders

import com.badlogic.gdx.maps.MapGroupLayer
import com.badlogic.gdx.maps.MapLayer
import com.badlogic.gdx.maps.MapObjects
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject
import com.teoretik.components.Floor
import com.teoretik.components.Level
import com.teoretik.graphics.render.GraphicsSettings.unitScale

class LevelLoader {
    companion object {
        fun loadLevel(map: TiledMap, level: Level) {
            loadFloors(map, level)
            moveObjectsToFloorLayers(level)
            adjustObjectCoordinates(level)
        }

        private fun loadFloors(map: TiledMap, level: Level) {
            for (layer in map.layers) {
                if (layer is Floor) {
                    try {
                        level.floors[layer.floorNumber] = layer
                    } catch (e: Exception) {
                        println("The configuration is wrong: floor layer must contain property ")
                    }
                }
            }
        }

        private fun moveObjectsToFloorLayers(level: Level) {
            level.floors.forEach { (_, layer) ->
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

        private fun adjustObjectCoordinates(level: Level) {
            level.floors.forEach { (_, layer) ->
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
}
