package com.teoretik.components.light

import com.badlogic.gdx.math.Vector2

abstract class LightSourceShape()

class PointLightSourceShape {}

class ConeLightSourceShape(val direction : Vector2, val angle : Float) {}