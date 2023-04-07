import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.parcelable.ParcelableContainer
import com.arkivanov.essenty.statekeeper.StateKeeperDispatcher
import com.joshrose.common.App
import com.joshrose.common.root.DefaultRootComponent
import com.roseFinancials.lenafx.ui.theme.PlotsForComposeTheme
import java.awt.Dimension
import java.io.File
import java.io.ObjectInputStream

fun main() = application {
    val lifecycle = LifecycleRegistry()
    val stateKeeper = StateKeeperDispatcher(tryRestoreStateFromFile())

    val root = runOnUiThread {
        DefaultRootComponent(
            componentContext = DefaultComponentContext(
                lifecycle = lifecycle,
                stateKeeper = stateKeeper
            )
        )
    }

    val windowParams = DpSize(1250.dp, 750.dp)
    val windowState = rememberWindowState(size = windowParams)

    Window(
        onCloseRequest = ::exitApplication,
        state = windowState,
        resizable = true,
        title = "PlotsForKotlin"
    ) {
        window.minimumSize = Dimension(
            windowParams.width.value.toInt(),
            windowParams.height.value.toInt()
        )

        PlotsForComposeTheme {
            App(root)
        }
    }
}

private const val SAVED_STATE_FILE_NAME = "saved_state.dat"

private fun tryRestoreStateFromFile(): ParcelableContainer? =
    File(SAVED_STATE_FILE_NAME).takeIf(File::exists)?.let { file ->
        try {
            ObjectInputStream(file.inputStream()).use(ObjectInputStream::readObject) as ParcelableContainer
        } catch (e: Exception) {
            null
        } finally {
            file.delete()
        }
    }
