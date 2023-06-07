package com.teoretik.controllers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.math.Vector3
import Screen
import com.badlogic.gdx.Input
import com.teoretik.graphics.camera.Camera

class CameraController(private val camera: Camera) : InputAdapter() {
    private var positionOnTouch = Pair(0, 0)
    private val lastPosition = Vector3()

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        positionOnTouch = screenX to screenY
        lastPosition.set(camera.unprojectScreenCoords(screenX, screenY))
        return true
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        val now = camera.screenToWorld(screenX, screenY)
        lastPosition.sub(now)
        camera.translate(lastPosition)
        lastPosition.set(camera.screenToWorld(screenX, screenY))
        return true
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return positionOnTouch != screenX to screenY
    }

    override fun scrolled(amountX: Float, amountY: Float): Boolean {
        val cursor = camera.screenToWorld(Gdx.input.x, Gdx.input.y)
        camera.updateZoom(cursor, amountY * 0.05f)
        return true
    }

    override fun keyDown(keycode: Int): Boolean {
        val v = camera.position.get()
        when (keycode) {
            Input.Keys.W -> v.y += 1
            Input.Keys.S -> v.y -= 1
            Input.Keys.A -> v.x -= 1
            Input.Keys.D -> v.x += 1
            else -> return true
        }
        camera.position.set(v)
        return true
    }
}