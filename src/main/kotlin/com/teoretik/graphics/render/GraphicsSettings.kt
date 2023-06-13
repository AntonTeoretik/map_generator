package com.teoretik.graphics.render

class GraphicsSettings (

) {
    companion object {
        /** Worlds heights in units **/
        val worldHeight = 10f
        val noiseLevel = 0.02f
        val pixelResolution = 32
        val unitScale = 1f / pixelResolution
        val lightResolution = 8                         // light "pixels" per cell
    }
}