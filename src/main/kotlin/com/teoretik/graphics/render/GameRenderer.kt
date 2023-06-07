package com.teoretik.graphics.render

import Screen
import com.teoretik.components.GameConfiguration

class UIRenderer(screen: Screen) {

}

class GameRenderer(configuration: GameConfiguration) {
    val worldRenderer = WorldRenderer(configuration)
}