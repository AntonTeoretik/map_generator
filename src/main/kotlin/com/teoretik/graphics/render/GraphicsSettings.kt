package com.teoretik.graphics.render

object GraphicsSettings {
    /** Worlds heights in units **/
    val worldHeight = 10f

    /** Amount of random variation of light **/
    val pixelResolution = 32
    val unitScale = 1f / pixelResolution
    val lightResolution = 5                         // light "pixels" per cell
    val visibilityResolution = 8
}