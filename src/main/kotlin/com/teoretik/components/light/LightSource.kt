package com.teoretik.components.light

import com.badlogic.gdx.maps.MapObject

abstract class LightSource(
    open val x: Float,
    open val y: Float,
    val light: Light,
    val shape: LightSourceShape,
) : MapObject() {
    var switchedOn = true
}

