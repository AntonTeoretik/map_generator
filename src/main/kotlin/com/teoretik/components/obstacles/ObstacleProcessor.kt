package com.teoretik.components.obstacles

import com.teoretik.geometry.Array2D
import com.teoretik.geometry.InternalRectangles

object ObstacleProcessor {
    fun processStandardObstacles(obstacleMap: Array2D<Boolean>, obstacles: MutableList<Obstacle>) {
        InternalRectangles(obstacleMap).maximals.forEach {
            obstacles.add(
                Obstacle.fromPolygon(
                    it.x0.toFloat(),
                    it.y0.toFloat(),
                    it.getWidth().toFloat() + 1f,
                    it.getHeight().toFloat() + 1f
                )
            )
        }
    }
}