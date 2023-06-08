package com.teoretik.components.loaders

import com.badlogic.gdx.maps.MapGroupLayer
import com.badlogic.gdx.maps.MapLayer
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.teoretik.components.FloorLayer
import com.teoretik.components.GameLevel
import com.teoretik.components.GameObject

class GameLevelLoader() {
    companion object {
        fun loadLevel(map: TiledMap, level: GameLevel) {
            loadFloors(map, level)
            loadObjects(level)
        }

        private fun loadFloors(map: TiledMap, level: GameLevel) {
            for (layer in map.layers) {
                if (layer is FloorLayer) {
                    try {
                        level.floors[layer.floorNumber()!!] = layer
                    } catch (e : Exception) {
                        println("The configuration is wrong: floor layer must contain property ")
                    }
                }
            }
        }

        private fun loadObjects(level: GameLevel) {
            level.floors.forEach { (floorNum, layer) ->
                loadObjectsFromLayer(floorNum, layer, level)
            }
        }

        private fun loadObjectsFromLayer(floorNum: Int, layer: MapLayer, level: GameLevel) {
            when (layer) {
                is MapGroupLayer -> layer.layers.forEach { loadObjectsFromLayer(floorNum, it, level) }
                is TiledMapTileLayer -> {
                    for (i in 0 until layer.width) {
                        for (j in 0 until layer.height) {
                            val tile = layer.getCell(i, j)?.tile ?: continue

                            if (tile.isObject()) {
                                val obj = GameObject.loadFromTile(tile)

                                obj.position.floor = floorNum
                                obj.position.x = i
                                obj.position.y = j

                                level.objects += obj
                            }
                        }
                    }
                }
            }
        }
    }
}
