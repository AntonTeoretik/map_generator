package com.teoretik.components.loaders

import com.badlogic.gdx.maps.MapGroupLayer
import com.badlogic.gdx.maps.MapLayer
import com.badlogic.gdx.maps.tiled.TiledMap
import com.teoretik.components.FloorLayer
import com.teoretik.components.GameLevel

class GameLevelLoader {
    companion object {
        fun loadLevel(map: TiledMap, level: GameLevel) {
            loadFloors(map, level)
            moveObjectsToFloorLayers(level)
        }

        private fun loadFloors(map: TiledMap, level: GameLevel) {
            for (layer in map.layers) {
                if (layer is FloorLayer) {
                    try {
                        level.floors[layer.floorNumber] = layer
                    } catch (e: Exception) {
                        println("The configuration is wrong: floor layer must contain property ")
                    }
                }
            }
        }

        private fun moveObjectsToFloorLayers(level: GameLevel) {
            level.floors.forEach { (_, layer) ->
                layer.layers.forEach {
                    moveObjectsToTop(layer, it)
                }
            }
        }

        private fun moveObjectsToTop(topLayer: FloorLayer, layer: MapLayer) {
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
