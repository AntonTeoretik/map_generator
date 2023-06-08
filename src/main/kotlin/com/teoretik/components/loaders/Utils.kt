package com.teoretik.components.loaders

import com.badlogic.gdx.maps.tiled.TiledMapTile

// Layer type and properties
const val FLOOR_NUMBER = "floorNumber"

// Tile types
const val TYPE = "type"
const val OBJECT = "Object"

fun TiledMapTile.type() : Any? = this.properties[TYPE]
fun TiledMapTile.isObject() : Boolean = this.properties[TYPE] == OBJECT
