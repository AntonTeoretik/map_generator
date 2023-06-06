package com.teoretik.controllers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.math.Vector3
import Screen
import com.badlogic.gdx.Input

class CameraController(private val screen: Screen) : InputAdapter() {
    private var positionOnTouch = Pair(0, 0)
    private val lastPosition = Vector3()

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        positionOnTouch = screenX to screenY
        lastPosition.set(screen.camera.unprojectScreenCoords(screenX, screenY))
        return true
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        val now = screen.camera.screenToWorld(screenX, screenY)
        lastPosition.sub(now)
        screen.camera.translate(lastPosition)
        lastPosition.set(screen.camera.screenToWorld(screenX, screenY))
        return true
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return positionOnTouch != screenX to screenY
    }

    override fun scrolled(amountX: Float, amountY: Float): Boolean {
        val cursor = screen.camera.screenToWorld(Gdx.input.x, Gdx.input.y)
        screen.camera.updateZoom(cursor, amountY * 0.05f)
        return true
    }

    override fun keyDown(keycode: Int): Boolean {
        when (keycode) {
            Input.Keys.W -> screen.position.y += 1
            Input.Keys.S -> screen.position.y -= 1
            Input.Keys.A -> screen.position.x -= 1
            Input.Keys.D -> screen.position.x += 1
        }
        screen.setCameraPosition()
        return true
    }
}