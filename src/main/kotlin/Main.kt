import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration


fun main(args: Array<String>) {
    val app = App()
    Lwjgl3Application(app, Lwjgl3ApplicationConfiguration().apply
    {
        setTitle("Map generator!")
        setWindowedMode(640, 480)
    })
}