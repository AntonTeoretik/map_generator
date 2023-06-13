package com.teoretik.components.loaders

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.math.Vector2

// Layer type and properties
const val FLOOR_NUMBER = "floorNumber"

// Tile types
const val TYPE = "type"
const val OBJECT = "Object"

fun TiledMapTile.isObject() : Boolean = this.properties[TYPE] == OBJECT


fun TiledMapTileLayer.cellToWorldCoordinates(x : Int, y : Int) : Vector2 {
    return Vector2(x + offsetX,  y + offsetY)
}