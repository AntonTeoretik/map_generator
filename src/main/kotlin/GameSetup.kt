
import com.teoretik.components.GameConfiguration
import com.teoretik.graphics.render.GameRenderer


class GameSetup {
    val gameConfiguration = GameConfiguration()
    init {
        gameConfiguration.load()
    }
    val renderer = GameRenderer(gameConfiguration)
}
