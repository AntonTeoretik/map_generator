package com.teoretik.components.viewpoint

import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.teoretik.components.map.Floor
import com.teoretik.geometry.integral.Array2D
import com.teoretik.geometry.rays.HitResult
import com.teoretik.geometry.toPolygon
import com.teoretik.graphics.render.GraphicsSettings.visibilityResolution
import kotlin.math.max
import kotlin.math.min

class ViewPointProcessor(val floor: Floor) {
    private val viewPoints = floor.viewPoints

    private val region = Rectangle(0f, 0f, floor.width.toFloat(), floor.height.toFloat())

    val totalVisibility = Array2D(
        floor.width * visibilityResolution + 1,
        floor.height * visibilityResolution + 1
    ) { _, _ -> Visibility.NOT_VISIBLE }

    fun processVisibility() {
        totalVisibility.iterate().forEach { totalVisibility[it.first, it.second] = Visibility.NOT_VISIBLE }
        viewPoints.forEach { point ->
            val preRegion = point.shape.hitBox()?.apply {
                x += point.x
                y += point.y
            } ?: region

            val r = Rectangle()

            if (!Intersector.intersectRectangles(preRegion, region, r)) return

            val relevantObstacles = floor.obstacleProcessor.staticObstacles
                .filter { Intersector.intersectPolygons(it.polygon, r.toPolygon(), null) }
                .map { it.polygon }

            //println(relevantObstacles.count())

            with(totalVisibility) {
                val xScaled = (r.x * visibilityResolution).toInt()
                val yScaled = (r.y * visibilityResolution).toInt()
                val widthScaled = (r.width * visibilityResolution).toInt()
                val heightScaled = (r.height * visibilityResolution).toInt()

                iterate(
                    max(0, xScaled)..min((xScaled + widthScaled + 1), width - 1),
                    max(0, yScaled)..min(yScaled + heightScaled + 1, height - 1),
                ).forEach { cell ->
                    val (i, j, _) = cell

                    val res = point.shape.processor.processRay(
                        Vector2(point.x, point.y),
                        Vector2(i.toFloat() / visibilityResolution, j.toFloat() / visibilityResolution),
                        relevantObstacles.asSequence(),
                        processEndPoint = true
                    )
                    if (res == HitResult.HIT) {
                        set(i, j, Visibility.VISIBLE)
                    }
                }
            }
        }
    }

}