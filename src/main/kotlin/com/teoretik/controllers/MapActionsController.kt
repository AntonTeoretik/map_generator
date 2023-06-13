package com.teoretik.controllers

import Screen
import com.badlogic.gdx.InputAdapter

class MapActionsController(
    private val screen: Screen,
    ) : InputAdapter() {
    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        //val point = screen.camera.screenToWorld2D(screenX, screenY)
        //TODO use this point for something
        return true
    }

    override fun keyDown(keycode: Int): Boolean {
        //TODO implement later
        return true
    }

}