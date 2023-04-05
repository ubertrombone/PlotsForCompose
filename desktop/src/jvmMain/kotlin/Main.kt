import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.joshrose.common.App
import com.roseFinancials.lenafx.ui.theme.PlotsForComposeTheme
import java.awt.Dimension

fun main() = application {
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
            App()
        }
    }
}
