
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.teoretik.components.GameLevel
import com.teoretik.components.loaders.MapLoader
import com.teoretik.graphics.render.MapRenderer


class GameSetup {

    val parameters = TmxMapLoader.Parameters()

    init {
        parameters.convertObjectToTileSpace
    }

    val loader = MapLoader()
    val map = loader.load("src/main/resources/map.tmx", parameters)
    val level = GameLevel(map)

    val renderer = MapRenderer(map, 1/32f)
}
