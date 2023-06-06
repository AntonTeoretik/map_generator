
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.teoretik.components.GameLevel
import com.teoretik.components.loaders.MapLoader


class GameSetup {

    val parameters = TmxMapLoader.Parameters()

    init {
        parameters.convertObjectToTileSpace
    }

    val loader = MapLoader()
    val map = loader.load("src/main/resources/map.tmx", parameters)
    val level = GameLevel(map)

    val renderer = OrthogonalTiledMapRenderer(map, 1/32f)
}
