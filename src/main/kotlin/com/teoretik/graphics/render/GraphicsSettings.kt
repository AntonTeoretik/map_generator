package com.teoretik.graphics.render

object GraphicsSettings {
    /** Worlds heights in units **/
    val worldHeight = 10f

    /** Amount of random variation of light **/
    val pixelResolution = 32
    val unitScale = 1f / pixelResolution
    val lightResolution = 4                         // light "pixels" per cell
    val memoryResolution = 2
    val visibilityResolution = 4
}