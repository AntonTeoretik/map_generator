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

import com.teoretik.utils.vectors.*

class MapRenderer(
    map: TiledMap?,
    unitScale: Float = GraphicsSettings.unitScale
) : OrthogonalTiledMapRenderer(map, unitScale) {

    val shapeRenderer = ShapeRenderer()

    override fun renderMapLayer(layer: MapLayer) {
        super.renderMapLayer(layer)
        if (!layer.isVisible || layer !is Floor) return

        renderObjects(layer)
        renderLight(layer)
        //renderObstacles(layer)
    }

    private fun renderLight(layer: Floor) {
        batch.end()
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_DST_COLOR, GL20.GL_ZERO);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)

        with(layer.lightProcessor.lightColorMap) {
            validIndicesSeparateFilter(
                { it != numRows - 1 },
                { it != numColumns - 1 }
            ).forEach { (i, j, _) ->
                shapeRenderer.color = LightColor().run {
                    sequenceOf(0 to 0, 0 to 1, 1 to 0, 1 to 1).forEach { (ii, jj) ->
                        add(this@with[i + ii, j + jj])
                    }
                    scl(0.25f)
                    toColorMask()
                }
                renderShadowSquare(ShadowsProcessor.lightMapToWorldCoordinates(i, j))
            }
        }

        shapeRenderer.end()
        Gdx.gl.glDisable(GL20.GL_BLEND);
        batch.begin()

    }

    private fun renderShadowSquare(vector2: Vector2) {
        val (x, y) = vector2
        val offset = 1f / GraphicsSettings.lightResolution
        val vertexes: FloatArray =
            listOf(
                x, y + offset,
                x + offset, y + offset,
                x + offset, y,
                x, y
            ).toFloatArray()

        for (i in 2 until vertexes.size - 2 step 2) {
            shapeRenderer.triangle(
                vertexes[0], vertexes[1],
                vertexes[i], vertexes[i + 1],
                vertexes[i + 2], vertexes[i + 3]
            )
        }
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
                .translate(obj.x + offsetX, obj.y + offsetY)
                .rotate(obj.rotation)
                .translate(-offsetX, -offsetY)

            batch.draw(region, 1f, 1f, aff)
        }
    }
}