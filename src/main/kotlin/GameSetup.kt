
import com.teoretik.components.GameConfiguration
import com.teoretik.graphics.render.WorldRenderer


class GameSetup {
    val gameConfiguration = GameConfiguration()
    init {
        gameConfiguration.load()
    }
    val renderer = WorldRenderer(gameConfiguration)
}
