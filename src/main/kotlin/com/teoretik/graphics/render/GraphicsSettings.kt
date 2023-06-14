package com.teoretik.graphics.render

object GraphicsSettings {
    /** Worlds heights in units **/
    val worldHeight = 10f

    /** Amount of random variation of light **/
    val noiseLevel = 0.02f
    val pixelResolution = 32
    val unitScale = 1f / pixelResolution
    val lightResolution = 4                         // light "pixels" per cell
}