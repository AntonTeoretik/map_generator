package com.teoretik.graphics.render

import com.teoretik.graphics.camera.Camera

interface Renderer {
    fun render()
    fun setView(camera: Camera)
}
