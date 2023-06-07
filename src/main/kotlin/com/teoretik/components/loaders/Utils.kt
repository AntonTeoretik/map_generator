package com.teoretik.components.loaders

import com.badlogic.gdx.maps.MapLayer
import com.badlogic.gdx.maps.tiled.TiledMapTile

// Layer type and properties
const val FLOOR = "Floor"
const val FLOOR_NUMBER = "floor"

// Tile types
const val TYPE = "type"
const val OBJECT = "Object"

fun TiledMapTile.type() : Any? = this.properties[TYPE]
fun TiledMapTile.isObject() : Boolean = this.properties[TYPE] == OBJECT


fun MapLayer.type() : Any? = this.properties[TYPE]
fun MapLayer.isFloor() : Boolean = this.type() == FLOOR
fun MapLayer.floorNumber() : Int? {
    return try {
        properties[FLOOR_NUMBER] as Int
    } catch (e: Exception) {
        null
    }
}