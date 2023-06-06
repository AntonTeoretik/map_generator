package com.teoretik.components

import IntPair
import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.teoretik.graphics.render.GraphicsSettings
import kotlin.math.ceil


open class GameObject(
) {
    val position : GlobalPosition = GlobalPosition()
    val occupiedCellRelativeCoordinates : MutableList<IntPair> = mutableListOf()

    companion object Loader {
        fun loadFromTile(tile: TiledMapTile) : GameObject {
            // I don't like to find size of tile this way, but ok for now
            val width = ceil(tile.textureRegion.regionWidth / GraphicsSettings.pixelResolution.toFloat()).toInt()
            val height = ceil(tile.textureRegion.regionHeight / GraphicsSettings.pixelResolution.toFloat()).toInt()

            return GameObject()
        }
    }
}

