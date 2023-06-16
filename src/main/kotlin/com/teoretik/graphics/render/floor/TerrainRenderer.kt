package com.teoretik.graphics.render.floor

import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.teoretik.graphics.camera.Camera
import com.teoretik.graphics.render.GraphicsSettings.unitScale
import com.teoretik.graphics.render.Renderer
import com.teoretik.graphics.resources.Batch

class TerrainRenderer(map: TiledMap) : OrthogonalTiledMapRenderer(map, unitScale, Batch()), Renderer {
    override fun setView(camera: Camera) = setView(camera.camera)
}