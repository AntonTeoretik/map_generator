package com.teoretik.components.viewpoint

import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.teoretik.components.Floor
import com.teoretik.geometry.integral.Array2D
import com.teoretik.geometry.rays.HitResult
import kotlin.math.max
import kotlin.math.min

class ViewPointProcessor(val floor: Floor) {
    private val viewPoints = floor.viewPoints

    private val region = Rectangle(0f, 0f, floor.width.toFloat(), floor.height.toFloat())

    val totalVisibility = Array2D(floor.width, floor.height) { _, _ -> Visibility.NOT_VISIBLE }

    fun processVisibility() {
        totalVisibility.iterate().forEach { totalVisibility[it.first, it.second] = Visibility.NOT_VISIBLE }
        viewPoints.forEach {point ->
            val preRegion = point.shape.hitBox()?.apply {
                x += point.x
                y += point.y
            } ?: region

            val r = Rectangle()

            if (!Intersector.intersectRectangles(preRegion, region, r)) return

            with(totalVisibility) {
                iterate(
                    max(0, r.x.toInt())..min((r.x + r.width + 1).toInt(), width - 1),
                    max(0, r.y.toInt())..min((r.y + r.height + 1).toInt(), height - 1),
                ).forEach { cell ->
                    val (i, j, _) = cell

                    val res = point.shape.processor.processRay(
                        Vector2(point.x, point.y),
                        Vector2(i.toFloat(), j.toFloat()),
                        floor.obstacleProcessor.staticObstacles.asSequence().map { it.polygon },
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