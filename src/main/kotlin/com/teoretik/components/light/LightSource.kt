package com.teoretik.components.light

import com.badlogic.gdx.maps.MapObject

abstract class LightSource(val light: Light, val shape: LightSourceShape) : MapObject() {
    open val x: Float = 0f
    open val y: Float = 0f
    var switchedOn = true
}

