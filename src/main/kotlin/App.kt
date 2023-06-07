import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.teoretik.controllers.CameraController
import com.teoretik.controllers.MapActionsController
import ktx.app.KtxGame
import ktx.app.KtxScreen

class App() : KtxGame<KtxScreen>() {

    override fun create() {

        val gameSetup = GameSetup()

        // Set the initial screen to the menu screen
        val screen = Screen(gameSetup)
        addScreen(screen)
        setScreen<Screen>()


        // Set up input handling
        val inputMultiplexer = InputMultiplexer()
        inputMultiplexer.addProcessor(CameraController(gameSetup.gameConfiguration.camera))

        val backgroundController = MapActionsController(screen)
        inputMultiplexer.addProcessor(backgroundController)

        Gdx.input.inputProcessor = inputMultiplexer
    }
}