package com.teoretik.components.loaders

import com.badlogic.gdx.maps.MapGroupLayer
import com.badlogic.gdx.maps.MapLayer
import com.badlogic.gdx.maps.tiled.TiledMap
import com.teoretik.components.Floor
import com.teoretik.components.Level

class LevelLoader {
    companion object {
        fun loadLevel(map: TiledMap, level: Level) {
            loadFloors(map, level)
            moveObjectsToFloorLayers(level)
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
    }
}
