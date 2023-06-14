package com.teoretik.components.obstacles

import com.teoretik.utils.geometry.Array2D
import com.teoretik.utils.geometry.InternalRectangles

object ObstacleProcessor {
    fun processStandardObstacles(obstacleMap: Array2D<Boolean>, obstacles: MutableList<Obstacle>) {
        InternalRectangles(obstacleMap).maximums.forEach {
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