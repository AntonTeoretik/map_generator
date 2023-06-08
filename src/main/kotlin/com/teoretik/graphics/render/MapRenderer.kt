package com.teoretik.graphics.render

import com.badlogic.gdx.maps.MapObject
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Affine2

class MapRenderer(
    map: TiledMap?,
    unitScale: Float = 1f / GraphicsSettings.pixelResolution
) : OrthogonalTiledMapRenderer(map, unitScale) {

    override fun renderObject(obj: MapObject?) {
        if (obj == null) return
        super.renderObject(obj)
        if (obj is TiledMapTileMapObject) {
            renderTileObject(obj)
        }
    }

    private fun renderTileObject(obj: TiledMapTileMapObject) {
        val tile = obj.tile

        if (tile != null) {
            val region = tile.textureRegion

            val offsetX = region.regionWidth / 2f * unitScale
            val offsetY = region.regionHeight / 2f * unitScale

            val aff = Affine2()
                .translate(obj.x * unitScale + offsetX, obj.y * unitScale + offsetY)
                .rotate(obj.rotation)
                .translate(-offsetX, -offsetY)

            batch.draw(region, 1f, 1f, aff)
        }
    }
}