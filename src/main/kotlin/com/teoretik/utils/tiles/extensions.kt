package com.teoretik.utils.tiles

import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.teoretik.graphics.render.GraphicsSettings

val TiledMapTile.widthInPixels: Int
    get() = this.textureRegion.regionWidth
val TiledMapTile.heightInPixels: Int
    get() = this.textureRegion.regionHeight
val TiledMapTile.widthInCells: Int
    get() = this.widthInPixels / GraphicsSettings.pixelResolution
val TiledMapTile.heightInCells: Int
    get() = this.heightInPixels / GraphicsSettings.pixelResolution
val TiledMapTile.width: Float
    get() = this.widthInPixels * GraphicsSettings.unitScale
val TiledMapTile.height: Float
    get() = this.heightInPixels * GraphicsSettings.unitScale
fun TiledMapTile.isStandard(): Boolean =
    widthInPixels % GraphicsSettings.pixelResolution == 0 && heightInPixels % GraphicsSettings.pixelResolution == 0
