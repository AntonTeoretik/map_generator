package com.teoretik.graphics.render.floor

import com.badlogic.gdx.maps.MapObject
import com.badlogic.gdx.maps.MapObjects
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject
import com.badlogic.gdx.math.Affine2
import com.badlogic.gdx.math.Rectangle
import com.teoretik.graphics.camera.Camera
import com.teoretik.graphics.render.BoundedRenderer
import com.teoretik.graphics.render.GraphicsSettings
import com.teoretik.graphics.render.Renderer
import com.teoretik.graphics.resources.Batch
import kotlin.math.abs



class ObjectRenderer(
    private val objects : MapObjects
) : BoundedRenderer() {
    private val batch = Batch()

    override fun setView(camera: Camera) {
        batch.projectionMatrix = camera.projMatrix()
        super.setView(camera)
    }

    override fun render() {
        batch.begin()
        objects.forEach { renderObject(it) }
        batch.end()
    }

    private fun renderObject(obj: MapObject?) {
        if (obj is TiledMapTileMapObject)
            renderTileObject(obj)
    }

    private fun renderTileObject(obj: TiledMapTileMapObject) {
        val tile = obj.tile

        if (tile != null) {
            val region = tile.textureRegion
            val offsetX = region.regionWidth / 2f * GraphicsSettings.unitScale
            val offsetY = region.regionHeight / 2f * GraphicsSettings.unitScale

            val aff = Affine2()
                .translate(obj.x + offsetX, obj.y + offsetY)
                .rotate(obj.rotation)
                .translate(-offsetX, -offsetY)

            batch.draw(region, 1f, 1f, aff)
        }
    }

}