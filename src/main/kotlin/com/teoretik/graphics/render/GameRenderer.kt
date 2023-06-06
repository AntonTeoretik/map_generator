package com.teoretik.graphics.render

import Screen

class UIRenderer(screen: Screen) {

}

class GameRenderer(screen: Screen) {
    val worldRenderer = WorldRenderer(screen)
    val uiRenderer = UIRenderer(screen)
}