package com.teoretik.graphics.render

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.maps.MapLayer
import com.badlogic.gdx.maps.MapObject
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Affine2
import com.badlogic.gdx.math.Vector2
import com.teoretik.components.Floor
import com.teoretik.components.light.processors.ShadowsProcessor
import com.teoretik.components.light.LightColor
import com.teoretik.components.light.toColorMask
import com.teoretik.graphics.camera.Camera

import com.teoretik.utils.vectors.*

class TerrainRenderer(
    map: TiledMap?
) : OrthogonalTiledMapRenderer(map, GraphicsSettings.unitScale) {
    val shapeRenderer = ShapeRenderer()

    override fun renderMapLayer(layer: MapLayer) {
        super.renderMapLayer(layer)

        if (!layer.isVisible || layer !is Floor) return

        renderObjects(layer)
       // renderObstacles(layer)
    }

    private fun renderObstacles(floor: Floor) {
        batch.end()
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_ONE_MINUS_SRC_ALPHA, GL20.GL_SRC_ALPHA)

        for (obst in floor.obstacleProcessor.staticObstacles) {
            val p = obst.polygon

            shapeRenderer.color = Color.RED.cpy()
            shapeRenderer.color.a = 0.5f
            shapeRenderer.polygon(p.transformedVertices)
        }
        shapeRenderer.end()
        batch.begin()
    }

    override fun renderObject(obj: MapObject?) {
        if (obj == null) return
        super.renderObject(obj)
        if (obj is TiledMapTileMapObject)
            renderTileObject(obj)
    }

    private fun renderTileObject(obj: TiledMapTileMapObject) {
        val tile = obj.tile

        if (tile != null) {
            val region = tile.textureRegion
            val offsetX = region.regionWidth / 2f * unitScale
            val offsetY = region.regionHeight / 2f * unitScale

            val aff = Affine2()
                .translate(obj.x + offsetX, obj.y + offsetY)
                .rotate(obj.rotation)
                .translate(-offsetX, -offsetY)

            batch.draw(region, 1f, 1f, aff)
        }
    }

    fun setView(camera: Camera) {
        setView(camera.camera)
    }
}